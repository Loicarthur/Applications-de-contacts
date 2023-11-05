package labyrinthe;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.scene.control.Menu;
import jfox.context.Dependent;
import jfox.javafx.control.MenuBarAbstract;


@Dependent
public class MenuBarAppli extends MenuBarAbstract {

	
	//-------
	// Champs
	//-------
	
	@Inject
	private ManagerGui	 	managerGui;
	
	
	//-------
	// Initialisation
	//-------
	
	@PostConstruct
	public void init() {

		
		// Variables de travail
		Menu 		menu;
		
		
		// Menu n°1
		
		menu = addMenu( "Application" );

		addMenuItem( "Acceuil", menu, 
				e -> managerGui.showView( ViewHome.class ) );

		addMenuItem( "Quitter", menu, 
				e -> managerGui.exit() );

		
		// Menu n°2
		
		menu = addMenu( "Labyrinthe" );

		addMenuItem( "Problème", menu, 
				e -> managerGui.showView( ViewProbleme.class ) );

		addMenuItem( "Solution", menu, 
				e -> managerGui.showView( ViewSolution.class ) );
		
	}
	
}
