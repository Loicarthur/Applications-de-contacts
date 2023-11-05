package contacts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.data.Memo;
import jakarta.inject.Inject;
import jfox.jdbc.UtilJdbc;

public class DaoMemo {

	// -------
	// Champs
	// -------

	@Inject
	private DataSource dataSource;

	@Inject
	private DaoCategorie daoCategorie;

	// -------
	// Actions
	// -------

	public void inserer(Memo memo) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO memo ( titre,description,flagurgent,idcategorie ) VALUES( ?, ?,? ,?) ";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setObject(1, memo.getTitre());
			stmt.setObject(2, memo.getDescription());
			stmt.setObject(3, memo.getFlagUrgent());
			stmt.setObject(4, memo.getCategorie() == null ? null : memo.getCategorie().getId());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			memo.setId(rs.getObject(1, Integer.class));

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public void modifier(Memo memo) {

		Connection cn = null;
		PreparedStatement stmt = null;
		String sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE memo SET titre=?,description=?,flagurgent=?,idcategorie=? WHERE idmemo =  ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, memo.getTitre());
			stmt.setObject(2, memo.getDescription());
			stmt.setObject(3, memo.getFlagUrgent());
			stmt.setObject(4, memo.getCategorie() == null ? null : memo.getCategorie().getId());
			stmt.setObject(5, memo.getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}
	}

	public void supprimer(int idMemo) {

		Connection cn = null;
		PreparedStatement stmt = null;
		String sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM memo WHERE idmemo = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, idMemo);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}
	}

	public Memo retrouver(int idMemo) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM memo WHERE idmemo = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, idMemo);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return construireMemo(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public List<Memo> listerTout() {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM memo ORDER BY titre";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Memo> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add(construireMemo(rs));
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public int compterPourCategorie(int idCategorie) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			cn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM memo WHERE idcategorie = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, idCategorie);
			rs = stmt.executeQuery();

			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	// -------
	// Méthodes auxiliaires
	// -------

	protected Memo construireMemo(ResultSet rs) throws SQLException {
		Memo memo = new Memo();
		memo.setId(rs.getObject("idmemo", Integer.class));
		memo.setTitre(rs.getObject("titre", String.class));
		memo.setDescription(rs.getObject("description", String.class));
		memo.setFlagUrgent(rs.getObject("FlagUrgent", Boolean.class));
		var idCategorie = rs.getObject("idcategorie", Integer.class);
		if (idCategorie != null) {
			memo.setCategorie(daoCategorie.retrouver(idCategorie));
		}
		return memo;
	}

}
