package it.unisa.diem.sad.progetto_sad;

import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.factories.ShapeCreator;
import it.unisa.diem.sad.progetto_sad.fileHandlers.FileManager;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller della vista principale dell'applicazione JavaFX.
 * Gestisce l'interazione con l'interfaccia utente per la creazione e la personalizzazione di forme geometriche.
 */
public class ViewController implements Initializable {

    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private Pane workspace;

    private Shape highlightedShape;
    private ShapeCreator chosenShape;

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
     * @param shape riferimento alla shape cliccata
     */
    private void highlightShape(Shape shape) {
        DropShadow highlight = new DropShadow(20, Color.BLUE);  // effetto di evidenziazione

        if (highlightedShape != null) {         // disattivo l'effetto alla forma attualmente evidenziata
            highlightedShape.setEffect(null);
        }
        highlightedShape = shape;       // evidenzio la forma cliccata
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
        chosenShape = new Shape1DCreator(Shape1D.TYPE_1D.LINE, strokeColorPicker.getValue());

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
        chosenShape = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, strokeColorPicker.getValue(), fillColorPicker.getValue());

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
        chosenShape = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, strokeColorPicker.getValue(), fillColorPicker.getValue());

        highlightShape((Shape) e.getTarget());
    }

    /**
     * Crea e aggiunge al workspace la forma selezionata, usando le proprietà correnti.
     */
    @FXML
    protected void addShape(MouseEvent event) {
        if(chosenShape != null) {
            if (event.getButton() == MouseButton.PRIMARY) {
                ShapeInterface shape = chosenShape.createShape();
                shape.setShapeX(event.getX());
                shape.setShapeY(event.getY());
                workspace.getChildren().add((Shape) shape);
            }
        }
    }

    /**
     * Aggiorna il colore del bordo della forma selezionata.
     */
    @FXML
    protected void pickedStrokeColor() {
        chosenShape.setStrokeColor(strokeColorPicker.getValue());
    }

    /**
     * Aggiorna il colore di riempimento della forma selezionata, se si tratta di una forma bidimensionale.
     */
    @FXML
    protected void pickedFillColor() {
        if(chosenShape instanceof Shape2DCreator) {
            ((Shape2DCreator) chosenShape).setFillColor(fillColorPicker.getValue());
        }
    }

    @FXML
    protected void saveFileOperation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (.txt)", ".txt"));
        File file = fileChooser.showSaveDialog(null);

        if(file.getName().endsWith(".txt")){
            List<ShapeInterface> shapeList = new ArrayList<>();

            for (Node node : workspace.getChildren())
                shapeList.add((ShapeInterface) node);

            FileManager.saveFile(shapeList, file.getAbsolutePath());
        }
        else {
            System.out.println("File non valido");
        }
    }

    @FXML
    protected void loadFileOperation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (.txt)", ".txt"));
        File file = fileChooser.showOpenDialog(null);

        if(file.getName().endsWith(".txt")){
            List<ShapeInterface> shapeList = FileManager.loadFile(file.getAbsolutePath());

            workspace.getChildren().clear();
            for (ShapeInterface shape : shapeList)
                workspace.getChildren().add((Shape) shape);
        }
        else {
            System.out.println("File non valido");
        }
    }
}