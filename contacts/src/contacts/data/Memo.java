package contacts.data;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Memo {

	private final ObjectProperty<Integer> id = new SimpleObjectProperty<>();
	private final StringProperty titre = new SimpleStringProperty();
	private final StringProperty description = new SimpleStringProperty();
	private final ObjectProperty<Boolean> flagUrgent = new SimpleObjectProperty<>();
	private final ObjectProperty<Categorie> categorie = new SimpleObjectProperty<>();

	// JavaFX Getters and Setters
	public final ObjectProperty<Integer> idProperty() {
		return this.id;
	}

	public final Integer getId() {
		return this.idProperty().get();
	}

	public final void setId(final Integer id) {
		this.idProperty().set(id);
	}

	public final StringProperty titreProperty() {
		return this.titre;
	}

	public final String getTitre() {
		return this.titreProperty().get();
	}

	public final void setTitre(final String titre) {
		this.titreProperty().set(titre);
	}

	public final StringProperty descriptionProperty() {
		return this.description;
	}

	public final String getDescription() {
		return this.descriptionProperty().get();
	}

	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}

	public final ObjectProperty<Boolean> flagUrgentProperty() {
		return this.flagUrgent;
	}

	public final Boolean getFlagUrgent() {
		return this.flagUrgentProperty().get();
	}

	public final void setFlagUrgent(final Boolean flagUrgent) {
		this.flagUrgentProperty().set(flagUrgent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id.get());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Memo other = (Memo) obj;
		return Objects.equals(id.get(), other.id.get());
	}

	public final ObjectProperty<Categorie> categorieProperty() {
		return this.categorie;
	}

	public final Categorie getCategorie() {
		return this.categorieProperty().get();
	}

	public final void setCategorie(final Categorie categorie) {
		this.categorieProperty().set(categorie);
	}

}
