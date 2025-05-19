package it.unisa.diem.sad.progetto_sad;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller della vista principale dell'applicazione JavaFX.
 * Gestisce l'interazione con l'interfaccia utente per la creazione e la personalizzazione di forme geometriche.
 */
public class ViewController implements Initializable {

    @FXML
    private Pane selectableShapes;
    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private Pane workspace;

    private Shape highlightedShape;
    private ShapeCreator selectedShape;

    /**
     * Inizializza il controller dopo il caricamento del file FXML.
     *
     * @param url URL utilizzato per inizializzare l'oggetto.
     * @param resourceBundle Risorse per l'internazionalizzazione, se presenti.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void onHighlighting() {
        DropShadow highlight = new DropShadow(20, Color.BLUE);  // effetto evidenziato

        for (Node shape : selectableShapes.getChildren()) { //itero su tutte le forme del pannello "Forme"
            if (highlightedShape != null) {         //evidenzio la forma attualmente evidenziata
                highlightedShape.setEffect(null);
            }
            highlightedShape = (Shape) shape;
            highlightedShape.setEffect(highlight);  //evidenzio la forma cliccata
        }
    }

    /**
     * Seleziona una linea come forma corrente da disegnare.
     * Imposta il colore del bordo preso dal color picker.
     */
    @FXML
    protected void chosenLine() {
        selectedShape = new Shape1DCreator(Shape1D.TYPE_1D.LINE, strokeColorPicker.getValue());
    }

    /**
     * Seleziona un rettangolo come forma corrente da disegnare.
     * Imposta i colori del bordo e del riempimento presi dai color picker.
     */
    @FXML
    protected void chosenRectangle() {
        selectedShape = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, strokeColorPicker.getValue(), fillColorPicker.getValue());
    }

    /**
     * Seleziona un'ellisse come forma corrente da disegnare.
     * Imposta i colori del bordo e del riempimento presi dai color picker.
     */
    @FXML
    protected void chosenEllipse() {
        selectedShape = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, strokeColorPicker.getValue(), fillColorPicker.getValue());
    }

    /**
     * Crea e aggiunge al workspace la forma selezionata, usando le proprietà correnti.
     */
    @FXML
    protected void addShape() {
        ShapeInterface shape = selectedShape.createShape();
        workspace.getChildren().add(shape.getShape());
    }

    /**
     * Aggiorna il colore del bordo della forma selezionata.
     */
    @FXML
    protected void pickedStrokeColor() {
        selectedShape.setStrokeColor(strokeColorPicker.getValue());
    }

    /**
     * Aggiorna il colore di riempimento della forma selezionata, se si tratta di una forma bidimensionale.
     */
    @FXML
    protected void pickedFillColor() {
        if(selectedShape instanceof Shape2DCreator) {
            ((Shape2DCreator) selectedShape).setFillColor(fillColorPicker.getValue());
        }
    }
}