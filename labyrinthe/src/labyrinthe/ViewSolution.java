package labyrinthe;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import jfox.javafx.view.ControllerAbstract;

public class ViewSolution extends ControllerAbstract {
// Actions
	
	@Inject
	private ManagerGui managerGui;
	
	@FXML
    private Button doProbleme;

    @FXML
    private Button doQuitter;
    
    @FXML
    private ImageView imvSolution;
    
    @Inject
	private ModelLabyrinthe modelLabyrinthe;

	@FXML
	private void initialize() {
		bindBidirectional(imvSolution, modelLabyrinthe.imageSolutionProperty());
	}
	
	@FXML
	private void doProbleme() {
		managerGui.showView(ViewProbleme.class);
	}

	@FXML
	private void doQuitter() {
		managerGui.exit();
	}
}
