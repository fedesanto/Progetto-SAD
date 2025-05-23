package it.unisa.diem.sad.progetto_sad;

import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.factories.ShapeCreator;
import it.unisa.diem.sad.progetto_sad.fileHandlers.FileManager;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
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
    @FXML
    private MenuItem saveButton;

    private Shape highlightedShape;
    private ShapeCreator chosenShape;
    private boolean isDrawingMode = false;

    private double dragStartX;
    private double dragStartY;

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
     * Evidenzia visivamente la forma selezionata applicando un effetto visivo.
     * Se un'altra forma era precedentemente evidenziata, rimuove l'effetto.
     *
     * @param shape forma da evidenziare
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
     Deseleziona la forma precedentemente selezionata
     */

    private void clearHighlightShape() {
        if (highlightedShape != null) {
            highlightedShape.setEffect(null);
            highlightedShape = null;
        }
    }

    /**
     * Abilita la possibilità di trascinare una forma all'interno dell'area di lavoro.
     * Quando l'utente preme il tasto sinistro del mouse sulla forma, memorizza
     * la posizione iniziale per calcolare lo spostamento.
     * Durante il trascinamento, aggiorna dinamicamente la posizione della forma.
     * All'inizio del drag, la forma viene anche evidenziata visivamente.
     *
     * @param node la forma (istanza di Node) sulla quale abilitare il trascinamento
     */
    private void enableDrag(Node node) {
        node.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragStartX = event.getSceneX() - node.getLayoutX();
                dragStartY = event.getSceneY() - node.getLayoutY();
                highlightShape((Shape) node);
            }
        });

        node.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double newX = event.getSceneX() - dragStartX;
                double newY = event.getSceneY() - dragStartY;
                node.setLayoutX(newX);
                node.setLayoutY(newY);
            }
        });

        node.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
            }
        });
    }


    /**
     * Seleziona una linea come forma corrente da disegnare.
     * Imposta il colore del bordo preso dal color picker.
     * Gestisce la selezione della linea dalla palette degli strumenti.
     * Imposta la linea come forma corrente da disegnare, attivando la modalità di disegno.
     * Applica l'effetto di evidenziazione sulla forma selezionata nella palette.
     * Se c'è una forma selezionata nello spazio di lavoro, viene deselezionata.
     *
     * @param event riferimento all'evento di click
     */
    @FXML
    protected void chosenLine(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            chosenShape = new Shape1DCreator(Shape1D.TYPE_1D.LINE, strokeColorPicker.getValue());
            isDrawingMode = true;
            clearHighlightShape(); // deseleziona forma nel workspace
            highlightShape((Shape) event.getTarget());
        }
    }

    /**
     * Seleziona un rettangolo come forma corrente da disegnare.
     * Imposta i colori del bordo e del riempimento presi dai color picker.
     * Gestisce la selezione del rettangolo dalla palette degli strumenti.
     * Imposta il rettangolo come forma corrente da disegnare, attivando la modalità di disegno.
     * Applica l'effetto di evidenziazione sulla forma selezionata nella palette.
     * Se c'è una forma selezionata nello spazio di lavoro, viene deselezionata.
     *
     * @param event riferimento all'evento di click
     */
    @FXML
    protected void chosenRectangle(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            chosenShape = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, strokeColorPicker.getValue(), fillColorPicker.getValue());
            isDrawingMode = true;
            clearHighlightShape(); // deseleziona forma nel workspace
            highlightShape((Shape) event.getTarget());
        }
    }

    /**
     * Seleziona un'ellisse come forma corrente da disegnare.
     * Imposta i colori del bordo e del riempimento presi dai color picker.
     * Gestisce la selezione dell'ellisse dalla palette degli strumenti.
     * Imposta l'ellisse come forma corrente da disegnare, attivando la modalità di disegno.
     * Applica l'effetto di evidenziazione sulla forma selezionata nella palette.
     * Se c'è una forma selezionata nello spazio di lavoro, viene deselezionata.
     *
     * @param event riferimento all'evento di click
     */
    @FXML
    protected void chosenEllipse(MouseEvent event) {
        if(event.getButton() == MouseButton.PRIMARY) {
            chosenShape = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, strokeColorPicker.getValue(), fillColorPicker.getValue());
            isDrawingMode = true;
            clearHighlightShape(); //deseleziona forma nel workspace
            highlightShape((Shape) event.getTarget());
        }
    }

    /**
     * Aggiunge una nuova forma nel punto cliccato dal mouse se è stato selezionato uno strumento.
     * Solo il tasto sinistro del mouse attiva questa azione.
     * Gestisce il click del mouse all'interno dello spazio di lavoro.
     * Se è attiva la modalità di disegno e una forma è stata selezionata dalla barra degli strumenti,
     * viene creata una nuova istanza della forma in corrispondenza del punto cliccato e aggiunta al workspace.
     * Se la modalità di disegno non è attiva, interpreta il click come un tentativo di selezione:
     * -se si clicca su una forma presente nel workspace, essa viene evidenziata.
     * -se si clicca in un punto vuoto, eventuali selezioni precedenti vengono rimosse.
     *
     * @param event evento di click del mouse
     */
    @FXML
    protected void addShape(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;

        Node clickedNode = event.getPickResult().getIntersectedNode();

        if (isDrawingMode && chosenShape != null) {
            ShapeInterface shape = chosenShape.createShape();
            shape.setShapeX(event.getX());
            shape.setShapeY(event.getY());

            Shape shapeNode = (Shape) shape;
            enableDrag(shapeNode);   // abilita il trascinamento della forma
            workspace.getChildren().add((Shape) shape);

            chosenShape = null;
            isDrawingMode = false;
            clearHighlightShape();  // deseleziona le forme selezionate
        } else {
            // Modalità SELEZIONE
            if (clickedNode instanceof Shape && workspace.getChildren().contains(clickedNode)) {
                highlightShape((Shape) clickedNode); // evidenzia la forma cliccata
            } else {
                clearHighlightShape(); // cliccando sullo spazio vuoto viene deselezionata la forma
            }
        }
    }

    /**
     * Aggiorna il colore del bordo della forma selezionata.
     */
    @FXML
    protected void pickedStrokeColor() {
        if(chosenShape != null)
            chosenShape.setStrokeColor(strokeColorPicker.getValue());
    }

    /**
     * Aggiorna il colore di riempimento della forma selezionata, se si tratta di una forma bidimensionale.
     */
    @FXML
    protected void pickedFillColor() {
        if(chosenShape != null){
            if(chosenShape instanceof Shape2DCreator) {
                ((Shape2DCreator) chosenShape).setFillColor(fillColorPicker.getValue());
            }
        }
    }

    /**
     * Abilita il pulsante di salvataggio solo se esistono forme nel workspace.
     * Viene invocato da un evento di click su "File".
     */
    @FXML
    protected void onClickFile() {
        saveButton.setDisable(workspace.getChildren().isEmpty());
    }

    /**
     * Salva le forme presenti nel workspace in un file scelto dall'utente.
     * Il formato del file è testuale, come definito nella classe FileManager.
     */
    @FXML
    protected void saveFileOperation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            List<ShapeInterface> shapeList = new ArrayList<>();

            // Conversione dei nodi in ShapeInterface
            for (Node node : workspace.getChildren())
                shapeList.add((ShapeInterface) node);

            if (!FileManager.saveFile(shapeList, file.getAbsolutePath())) {
                // Generazione alert in caso di errore nel salvataggio
                showAlert("Errore", "Impossibile salvare il file.");
            }
        }
    }

    /**
     * Carica forme da un file scelto dall'utente e le visualizza nel workspace.
     * Sovrascrive eventuali forme già presenti.
     */
    @FXML
    protected void loadFileOperation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                // Mostra errore se il file non ha estensione .txt
                showAlert("Errore", "Il file deve avere estensione .txt.");
            } else {
                List<ShapeInterface> shapeList = FileManager.loadFile(file.getAbsolutePath());

                if (shapeList != null) {
                    // Rimuove tutte le forme precedenti e aggiunge le nuove
                    workspace.getChildren().clear();
                    for (ShapeInterface shape : shapeList)
                        workspace.getChildren().add((Shape) shape);
                } else {
                    // Generazione alert in caso di errore nel caricamento
                    showAlert("Errore", "Impossibile caricare il file.");
                }
            }
        }
    }

    /**
     * Mostra una finestra di dialogo di errore con un titolo e un messaggio specificati.
     *
     * @param title   il titolo della finestra di dialogo.
     * @param message il messaggio di errore da visualizzare nel contenuto dell'alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}