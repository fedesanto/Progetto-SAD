package it.unisa.diem.sad.progetto_sad;

import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.factories.ShapeCreator;
import it.unisa.diem.sad.progetto_sad.fileHandlers.FileManager;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import it.unisa.diem.sad.progetto_sad.visitors.VisitorResize;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ContextMenu workspaceContextMenu;
    private double workspaceX;
    private double workspaceY;
    private ShapeInterface copiedShape;

    private double startDragX;
    private double startDragY;
    private ShapeInterface selectedShape;

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
        MenuItem copyItem = new MenuItem("Copia");
        MenuItem cutItem = new MenuItem("Taglia");

        deleteItem.setOnAction(e -> {
            workspace.getChildren().remove(contextMenu.getOwnerNode());
        });

        resizeItem.setOnAction(e -> {
            VisitorResize resizeVisitor = new VisitorResize();
            ((ShapeInterface) contextMenu.getOwnerNode()).accept(resizeVisitor);  // chiama il visit appropriato
        });

        copyItem.setOnAction(e -> {
            copiedShape = ((ShapeInterface) contextMenu.getOwnerNode()).clone();
        });

        cutItem.setOnAction(e -> {
            ShapeInterface toCut  = (ShapeInterface) contextMenu.getOwnerNode();
            copiedShape = toCut.clone();
            workspace.getChildren().remove((Shape) toCut);
        });

        contextMenu.getItems().addAll(deleteItem, resizeItem, copyItem, cutItem);


        workspaceContextMenu = new ContextMenu();
        MenuItem pasteItem = new MenuItem("Incolla");
        pasteItem.setOnAction(e -> {
            ShapeInterface newShape = copiedShape.clone();
            newShape.setShapeX(workspaceX);
            newShape.setShapeY(workspaceY);
            addShapeEvents(newShape);
            workspace.getChildren().add((Shape) newShape);
        });

        workspaceContextMenu.setOnShown(e -> {
            pasteItem.setDisable(copiedShape == null);
        });
        workspaceContextMenu.getItems().add(pasteItem);
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
     * Seleziona la forma specificata come attualmente selezionata nello spazio di lavoro,
     * deselezionando eventuali forme precedentemente selezionate.
     *
     * @param shape la forma da selezionare e evidenziare
     */
    private void selectShape(ShapeInterface shape) {
        DropShadow highlight = new DropShadow(20, Color.BLUE);

        if (selectedShape != null) {
            ((Shape) selectedShape).setEffect(null); //Rimuove l'effetto visivo dalla forma precedentemente selezionata
        }

        selectedShape = shape;
        ((Shape) selectedShape).setEffect(highlight);
    }

    /**
     * Deseleziona la forma attualmente selezionata nello spazio di lavoro, rimuovendo l'effetto visivo di evidenziazione.
     * Se non c'è nessuna forma selezionata, non esegue alcuna operazione.
     */
    private void deselectShape() {
        if (selectedShape != null) {
            ((Shape) selectedShape).setEffect(null); // Rimuove l'effetto visivo dalla forma
            selectedShape = null;
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
            deselectShape();
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
            deselectShape();
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
            deselectShape();
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
        if (event.getButton() == MouseButton.PRIMARY) {
            if (chosenShape != null) {
                ShapeInterface shape = chosenShape.createShape();
                shape.setShapeX(event.getX());
                shape.setShapeY(event.getY());

                addShapeEvents(shape);  //Aggiunge tutti gli eventi di interesse per la forma appena creata

                workspace.getChildren().add((Shape) shape);
            }
        }else if (event.getButton() == MouseButton.SECONDARY){
            workspaceX = event.getX();
            workspaceY = event.getY();
            workspaceContextMenu.show(workspace.getScene().getWindow(), event.getScreenX(), event.getScreenY());
        }
        deselectShape();
    }


    /**
     * Registra tutti gli eventi interattivi necessari per una forma geometrica.
     *
     * Gli eventi gestiscono:
     *      La selezione della forma tramite click sinistro o destro.
     *     La visualizzazione del menu contestuale al click destro.
     *     Il trascinamento della forma con il mouse (drag and drop).
     *
     * @param shape la forma a cui associare gli eventi di interazione.
     */
    private void addShapeEvents(ShapeInterface shape){
        Shape shapeEvent = (Shape) shape;

        shapeEvent.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY){     //Evento alla pressione del tasto destro
                contextMenu.show(shapeEvent, event.getScreenX(), event.getScreenY());    //Mostra menu contestuale
                selectShape(shape);
                event.consume();
            }else if(event.getButton() == MouseButton.PRIMARY && chosenShape == null){
                selectShape(shape);
                event.consume();

            }
        });

        shapeEvent.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY){

                if (chosenShape == null){
                    startDragX = ((ShapeInterface) event.getTarget()).getShapeX() - event.getX();
                    startDragY = ((ShapeInterface) event.getTarget()).getShapeY() - event.getY();

                    selectShape(shape);
                }
            }
        });

        shapeEvent.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY && chosenShape == null) {
                shape.setShapeX(event.getX() + startDragX); // aggiorna posizione X in base al cursore
                shape.setShapeY(event.getY() + startDragY); // aggiorna posizione Y in base al cursore
            }
            contextMenu.hide();
        });

    }

    /**
     * Gestisce l’evento di selezione di un nuovo colore per il bordo della forma.
     * Se una forma è attualmente selezionata nello spazio di lavoro, il metodo aggiorna
     * il colore del suo bordo con il colore scelto dall’utente tramite il color picker
     * dedicato ai bordi.
     * Sono gestiti due casi di selezione:
     *     chosenShape: la forma attivamente selezionata e controllata.
     *     selectedShape: eventuale altra forma evidenziata per operazioni precedenti.
     */
    @FXML
    protected void pickedStrokeColor() {
        Color selectedColor = strokeColorPicker.getValue();

        if (chosenShape != null)
            chosenShape.setStrokeColor(selectedColor);

        if(selectedShape != null)
            selectedShape.setStrokeColor(selectedColor);
    }


    /**
     * Cambia il colore di riempimento della forma selezionata:
     * - Se è attiva la modalità di disegno,
     *   imposta il colore di riempimento per la prossima forma da disegnare.
     * - Se una forma 2D è attualmente selezionata nello spazio di lavoro,
     *   aggiorna visivamente il suo colore di riempimento con quello scelto dall'utente.
     */
    @FXML
    protected void pickedFillColor() {
        Color selectedColor = fillColorPicker.getValue();

        if (chosenShape instanceof Shape2DCreator)
            ((Shape2DCreator) chosenShape).setFillColor(selectedColor);

        if (selectedShape instanceof Shape2D)
            ((Shape2D) selectedShape).setFillColor(selectedColor);
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