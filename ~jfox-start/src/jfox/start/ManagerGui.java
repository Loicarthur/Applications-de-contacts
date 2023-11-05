package jfox.start;
import javafx.scene.Scene;
import jfox.javafx.view.ManagerGuiAbstract;
import jfox.javafx.view.View;


public class ManagerGui extends ManagerGuiAbstract {
	
	
	//-------
	// Actions
	//-------

	@Override
	public void configureStage()  {
		
		// Choisit la vue à afficher
//		showView( ViewHome.class );
		
		// Configure le stage
		stage.setTitle( "Titre de la fenêtre" );
		stage.sizeToScene();
		stage.setResizable( false );
//		stage.getIcons().add( new Image( this.getClass().getResource("images/icone.png").toExternalForm() ) );

		// Configuration par défaut pour les boîtes de dialogue
//		typeConfigDialogDefault = ConfigDialog.class;
	}


	@Override
	public Scene createScene( View view ) {
		var scene = new Scene( view.getRoot() );
//		scene.getStylesheets().add( this.getClass().getResource("application.css").toExternalForm() );
		return scene;
	}
	
}