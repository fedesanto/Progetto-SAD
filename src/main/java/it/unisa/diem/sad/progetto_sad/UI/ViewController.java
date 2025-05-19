package it.unisa.diem.sad.progetto_sad.UI;

import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.factories.ShapeCreator;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
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

    /**
     * Evidenzia la forma selezionata
     *
     * @param selectedShape riferimento alla shape cliccata
     */
    private void highlightShape(Shape selectedShape) {
        DropShadow highlight = new DropShadow(20, Color.BLUE);  // effetto di evidenziazione

        if (highlightedShape != null) {         // disattivo l'effetto alla forma attualmente evidenziata
            highlightedShape.setEffect(null);
        }
        highlightedShape = selectedShape;       // evidenzio la forma cliccata
        highlightedShape.setEffect(highlight);
    }

    /**
     * Seleziona una linea come forma corrente da disegnare.
     * Imposta il colore del bordo preso dal color picker.
     *
     * @param e riferimento all'evento di click
     */
    @FXML
    protected void chosenLine(MouseEvent e) {
        selectedShape = new Shape1DCreator(Shape1D.TYPE_1D.LINE, strokeColorPicker.getValue());

        highlightShape((Shape) e.getTarget());
    }

    /**
     * Seleziona un rettangolo come forma corrente da disegnare.
     * Imposta i colori del bordo e del riempimento presi dai color picker.
     *
     * @param e riferimento all'evento di click
     */
    @FXML
    protected void chosenRectangle(MouseEvent e) {
        selectedShape = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, strokeColorPicker.getValue(), fillColorPicker.getValue());

        highlightShape((Shape) e.getTarget());
    }

    /**
     * Seleziona un'ellisse come forma corrente da disegnare.
     * Imposta i colori del bordo e del riempimento presi dai color picker.
     *
     * @param e riferimento all'evento di click
     */
    @FXML
    protected void chosenEllipse(MouseEvent e) {
        selectedShape = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, strokeColorPicker.getValue(), fillColorPicker.getValue());

        highlightShape((Shape) e.getTarget());
    }

    /**
     * Crea e aggiunge al workspace la forma selezionata, usando le proprietà correnti.
     */
    @FXML
    protected void addShape(MouseEvent event) {
        ShapeInterface shape = selectedShape.createShape();
        shape.setCenterX(event.getX());
        shape.setCenterY(event.getY());
        workspace.getChildren().add((Shape) shape);
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