package contacts.view.memo;

import contacts.view.ManagerGui;
import contacts.data.Categorie;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;

public class ViewMemoForm extends ControllerAbstract {

	// -------
	// Composants de la vue
	// -------

	@FXML
	private Label labId;
	@FXML
	private TextField txfTitre;
	@FXML
	private TextArea txaDescription;
	@FXML
	private CheckBox ckbUrgent;
	@FXML
	private Button btnValider;
	@FXML
	private Button btnSupprimer;
	@FXML
	private ComboBox<Categorie> cmbCategorie;

	// -------
	// Autres champs
	// -------

	@Inject
	private ManagerGui managerGui;
	@Inject
	private ModelMemo modelMemo;

	// -------
	// Initialisation du Controller
	// -------

	@FXML
	private void initialize() {

		var draft = modelMemo.getDraft();

		// Id
		bind(labId, draft.idProperty(), new ConverterInteger());
		
		//categorie
		cmbCategorie.setItems( modelMemo.getCategories() );
		bindBidirectional( cmbCategorie, draft.categorieProperty() );
		UtilFX.setCellFactory( cmbCategorie, "libelle" );


		// Libellé
		bindBidirectional(txfTitre, draft.titreProperty());
		validator.addRuleNotBlank(txfTitre);
		validator.addRuleMaxLength(txfTitre, 50);
		validator.addRuleMinLength(txfTitre, 4);

		bindBidirectional(txaDescription, draft.descriptionProperty());
		validator.addRuleMaxLength(txaDescription, 1000);

		bindBidirectional(ckbUrgent, draft.flagUrgentProperty());
		validator.addRuleMaxValue(ckbUrgent, 2);
		validator.addRuleMinValue(ckbUrgent, 2);

		// Bouton VAlider
		btnValider.disableProperty().bind(validator.invalidProperty());
		btnSupprimer.disableProperty().bind(validator.invalidProperty());
	}

	@Override
	public void refresh() {
		txfTitre.requestFocus();
	}

	// -------
	// Actions
	// -------

	@FXML
	private void doAnnuler() {
		managerGui.showView(ViewMemoList.class);
	}

	@FXML
	private void doValider() {
		modelMemo.saveDraft();
		managerGui.showView(ViewMemoList.class);
	}
	
	@FXML
	private void doCategorieSupprimer() {
	cmbCategorie.setValue( null );
	}


}
