package labyrinthe;

import jakarta.annotation.PostConstruct;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import jfox.exception.ExceptionValidation;

public class ModelLabyrinthe {

	private final ObservableList<Integer> listeNiveaux = FXCollections.observableArrayList();
	private final ObjectProperty<Integer> niveau = new SimpleObjectProperty<>();
	private final ObjectProperty<Image> imageProbleme = new SimpleObjectProperty<>();
	private final ObjectProperty<Image> imageSolution = new SimpleObjectProperty<>();

	public ObservableList<Integer> getListeNiveaux() {
		return listeNiveaux;
	}

	public ObjectProperty<Integer> niveauProperty() {
		return niveau;
	}

	public ObjectProperty<Image> imageProblemeProperty() {
		return imageProbleme;
	}

	public ObjectProperty<Image> imageSolutionProperty() {
		return imageSolution;
	}

	@PostConstruct
	public void init() {
		listeNiveaux.addAll(1, 2, 3, 4, 5);
		niveau.addListener(obs -> chargerImages());
		niveau.set(1);

	}

	public void chargerImages() {
		if (niveau.getValue() < 1 || niveau.getValue() > 4) {
			imageProbleme.set( null );
			imageSolution.set( null );
			throw new ExceptionValidation("Niveau incorrect");
		}
		String chemin1, chemin2;
		chemin1 = "images/probleme" + niveau.getValue() + ".png";
		chemin2 = "images/solution" + niveau.getValue() + ".png";
		imageProbleme.set(new Image(getClass().getResource(chemin1).toExternalForm()));
		imageSolution.set(new Image(getClass().getResource(chemin2).toExternalForm()));
	}

}
