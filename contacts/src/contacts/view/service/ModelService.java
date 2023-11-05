package contacts.view.service;

import java.time.LocalDate;

import contacts.commun.IMapper;
import contacts.dao.DaoService;
import contacts.data.Personne;
import contacts.data.Service;
import contacts.view.personne.ModelPersonne;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Mode;

public class ModelService {

	// -------
	// Données observables
	// -------

	private final ObservableList<Service> list = FXCollections.observableArrayList();

	private final BooleanProperty flagRefreshingList = new SimpleBooleanProperty();

	private final Service draft = new Service();

	private final ObjectProperty<Service> current = new SimpleObjectProperty<>();

	// -------
	// Autres champs
	@Inject
	private ModelPersonne modelPersonne;
	// -------

	private Mode mode = Mode.NEW;

	@Inject
	private IMapper mapper;
	@Inject
	private DaoService daoService;

	// -------
	// Getters & Setters
	// -------

	public ObservableList<Personne> getPersonnes() {
		return modelPersonne.getList();
	}

	public ObservableList<Service> getList() {
		return list;
	}

	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}

	public Service getDraft() {
		return draft;
	}

	public ObjectProperty<Service> currentProperty() {
		return current;
	}

	public Service getCurrent() {
		return current.get();
	}

	public void setCurrent(Service item) {
		current.set(item);
	}

	public Mode getMode() {
		return mode;
	}

	// -------
	// Actions
	// -------

	public void refreshList() {
		// flagRefreshingList vaut true pendant la durée
		// du traitement de mise à jour de la liste
		flagRefreshingList.set(true);
		list.setAll(daoService.listerTout());
		flagRefreshingList.set(false);
	}

	public void initDraft(Mode mode) {
		modelPersonne.refreshList();
		this.mode = mode;
		if (mode == Mode.NEW) {
			mapper.update(draft, new Service());
			draft.setFlagSiege(false);
			draft.setAnneeCreation(LocalDate.now().getYear());
		} else {
			setCurrent(daoService.retrouver(getCurrent().getIdService()));
			mapper.update(draft, getCurrent());
		}
	}

	public void saveDraft() {

		// Enregistre les données dans la base

		if (mode == Mode.NEW) {
			// Insertion
			daoService.inserer(draft);
			// Actualise le courant
			setCurrent(mapper.update(new Service(), draft));
		} else {
			// modficiation
			daoService.modifier(draft);
			// Actualise le courant
			mapper.update(getCurrent(), draft);
		}
	}

	public void deleteCurrent() {

		// Effectue la suppression
		daoService.supprimer(getCurrent().getIdService());
		// Détermine le nouveau courant
		setCurrent(UtilFX.findNext(list, getCurrent()));
	}

}
