package labyrinthe;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import jfox.javafx.view.ControllerAbstract;

public class ViewHome extends ControllerAbstract {
// Actions
	@Inject
	private ManagerGui managerGui;

	@FXML
	private Button doProbleme;

	@FXML
	private Button doQuitter;

	@FXML
	private ComboBox<Integer> cmbNiveau;

	@Inject
	private ModelLabyrinthe modelLabyrinthe;

	@FXML
	private void initialize() {
		cmbNiveau.setItems(modelLabyrinthe.getListeNiveaux());
		bindBidirectional(cmbNiveau, modelLabyrinthe.niveauProperty());
	}

	@FXML
	private void doQuitter() {
		managerGui.exit();
	}

	@FXML
	private void doProbleme() {
		managerGui.showView(ViewProbleme.class);

	}
}
