package it.unisa.diem.sad.progetto_sad;

import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.factories.ShapeCreator;
import it.unisa.diem.sad.progetto_sad.fileHandlers.FileManager;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import it.unisa.diem.sad.progetto_sad.visitors.VisitorResize;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
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

    private Button highlightedButton;
    private ShapeCreator chosenShape;
    private ContextMenu contextMenu;

    /**
     * Inizializza il controller dopo il caricamento del file FXML.
     * Crea il contex menu che sarà utilizzato dalle forme
     *
     * @param url            URL utilizzato per inizializzare l'oggetto.
     * @param resourceBundle Risorse per l'internazionalizzazione, se presenti.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Elimina");
        MenuItem resizeItem = new MenuItem("Ridimensiona");

        deleteItem.setOnAction(e -> {
            workspace.getChildren().remove((Shape) selectedShape);
        });

        resizeItem.setOnAction(e -> {
            VisitorResize resizeVisitor = new VisitorResize();
            selectedShape.accept(resizeVisitor);  // chiama il visit appropriato
        });

        contextMenu.getItems().addAll(deleteItem, resizeItem);
    }

    /**
     * Evidenzia visivamente il bottone selezionata applicando un effetto visivo.
     * Se un altro bottone era precedentemente evidenziato, rimuove l'effetto a quello precedente.
     * Se il metodo viene chiamato sul bottne attualmente evidenziato, gli rimuove l'effetto
     *
     * @param button bottone da evidenziare
     * @return restistuisce true se è stato cliccato un nuovo bottone, altrimenti false
     */
    private boolean highlightButton(Button button) {
        DropShadow highlight = new DropShadow(13, Color.BLUE);  // effetto di evidenziazione

        if (highlightedButton != null) {         // disattivo l'effetto alla forma attualmente evidenziata
            highlightedButton.setEffect(null);
        }

        if(button != highlightedButton){
            highlightedButton = button;       // evidenzio la forma cliccata
            highlightedButton.setEffect(highlight);
            return true;
        }else{
            highlightedButton = null;
            return false;
        }
    }

    /**
     * Seleziona una linea come forma corrente da disegnare.
     * Imposta il colore del bordo preso dal color picker.
     *
     * @param event riferimento all'evento di click
     */
    @FXML
    protected void chosenLine(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            boolean isNewRequest = highlightButton((Button) event.getTarget()); //Evidenzia o disevidenzia il bottone cliccato
            if(isNewRequest)
                chosenShape = new Shape1DCreator(Shape1D.TYPE_1D.LINE, strokeColorPicker.getValue());
            else
                chosenShape = null;
        }
    }

    /**
     * Seleziona un rettangolo come forma corrente da disegnare.
     * Imposta i colori del bordo e del riempimento presi dai color picker.
     *
     * @param event riferimento all'evento di click
     */
    @FXML
    protected void chosenRectangle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            boolean isNewRequest = highlightButton((Button) event.getTarget());     //Evidenzia o disevidenzia il bottone cliccato
            if(isNewRequest)
                chosenShape = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, strokeColorPicker.getValue(), fillColorPicker.getValue());
            else
                chosenShape = null;
        }
    }

    /**
     * Seleziona un'ellisse come forma corrente da disegnare.
     * Imposta i colori del bordo e del riempimento presi dai color picker.
     *
     * @param event riferimento all'evento di click
     */
    @FXML
    protected void chosenEllipse(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            boolean isNewRequest = highlightButton((Button) event.getTarget());     //Evidenzia o disevidenzia il bottone cliccato
            if(isNewRequest)
                chosenShape = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, strokeColorPicker.getValue(), fillColorPicker.getValue());
            else
                chosenShape = null;
        }
    }

    /**
     * Aggiunge una nuova forma nel punto cliccato dal mouse se è stato selezionato uno strumento.
     * Solo il tasto sinistro del mouse attiva questa azione.
     *
     * @param event evento di click del mouse
     */
    @FXML
    protected void clickOnWorkspace(MouseEvent event) {
        if (chosenShape != null) {
            if (event.getButton() == MouseButton.PRIMARY) {
                ShapeInterface shape = chosenShape.createShape();
                shape.setShapeX(event.getX());
                shape.setShapeY(event.getY());

                addShapeEvents(shape);  //Aggiunge tutti gli eventi di interesse per la forma appena creata

                workspace.getChildren().add((Shape) shape);
            }
        }
    }


    /**
     * Metodo di comodo che aggiunge tutti gli eventi necessari ad una forma creata
     *
     * @param shape forma a cui aggiungere gli eventi
     */
    private void addShapeEvents(ShapeInterface shape){
        Shape shapeEvent = (Shape) shape;

        shapeEvent.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY){     //Evento alla pressione del tasto destro
                contextMenu.show(shapeEvent, event.getScreenX(), event.getScreenY());    //Mostra menu contestuale
                selectShape(shape);
                event.consume();
            }
        });
    }

    /**
     * Aggiorna il colore del bordo della forma selezionata.
     */
    @FXML
    protected void pickedStrokeColor() {
        if (chosenShape != null)
            chosenShape.setStrokeColor(strokeColorPicker.getValue());
    }

    /**
     * Aggiorna il colore di riempimento della forma selezionata, se si tratta di una forma bidimensionale.
     */
    @FXML
    protected void pickedFillColor() {
        if (chosenShape != null) {
            if (chosenShape instanceof Shape2DCreator) {
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