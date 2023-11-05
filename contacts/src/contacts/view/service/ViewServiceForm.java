package contacts.view.service;

import contacts.view.ManagerGui;
import contacts.data.Personne;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;

public class ViewServiceForm extends ControllerAbstract {

	// -------
	// Composants de la vue
	// -------

	@FXML
	private Label labId;
	@FXML
	private TextField txfNom;
	@FXML
	private TextField txfAnnee;
	@FXML
	private CheckBox ckbSiege;
	@FXML
	private Button btnValider;
	@FXML
	private Button btnSupprimer;
	@FXML
	private ComboBox<Personne> cmbService;

	// -------
	// Autres champs
	// -------

	@Inject
	private ManagerGui managerGui;
	@Inject
	private ModelService modelService;

	// -------
	// Initialisation du Controller
	// -------

	@FXML
	private void initialize() {

		var draft = modelService.getDraft();

		// Id
		bind(labId, draft.idServiceProperty(), new ConverterInteger());
		
		//categorie
		cmbService.setItems( modelService.getPersonnes());
		bindBidirectional( cmbService, draft.personneProperty());
		UtilFX.setCellFactory( cmbService, "nom");


		// Libellé
		bindBidirectional(txfNom, draft.nomProperty());
		validator.addRuleNotBlank(txfNom);
		validator.addRuleMaxLength(txfNom, 50);
		validator.addRuleMinLength(txfNom, 4);
		
		bindBidirectional(txfAnnee, draft.anneeCreationProperty(), new ConverterInteger());
		validator.addRuleNotBlank(txfNom);
		validator.addRuleMaxLength(txfNom, 50);
		validator.addRuleMinLength(txfNom, 4);

		bindBidirectional(ckbSiege, draft.flagSiegeProperty());
		validator.addRuleMaxValue(ckbSiege, 2);
		validator.addRuleMinValue(ckbSiege, 2);

		// Bouton VAlider
		btnValider.disableProperty().bind(validator.invalidProperty());
		btnSupprimer.disableProperty().bind(validator.invalidProperty());
	}

	@Override
	public void refresh() {
		txfNom.requestFocus();
	}

	// -------
	// Actions
	// -------

	@FXML
	private void doAnnuler() {
		managerGui.showView(ViewServiceList.class);
	}

	@FXML
	private void doValider() {
		modelService.saveDraft();
		managerGui.showView(ViewServiceList.class);
	}
	
	@FXML
	private void doPersonneSupprimer() {
	cmbService.setValue( null );
	}


}
