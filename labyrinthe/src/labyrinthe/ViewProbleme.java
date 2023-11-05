package labyrinthe;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import jfox.javafx.view.ControllerAbstract;

public class ViewProbleme extends ControllerAbstract {
// Actions
	@Inject
	private ManagerGui managerGui;

	@FXML
    private ImageView imvProbleme;

	@Inject
	private ModelLabyrinthe modelLabyrinthe;

	@FXML
	private Button doAcceuil;

	@FXML
	private Button doSolution;

	@FXML
	private void initialize() {
		bindBidirectional(imvProbleme, modelLabyrinthe.imageProblemeProperty());
	}

	@FXML
	private void doAcceuil() {
		managerGui.showView(ViewHome.class);
	}

	@FXML
	private void doSolution() {
		managerGui.showView(ViewSolution.class);
	}
}
