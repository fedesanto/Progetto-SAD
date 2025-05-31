package it.unisa.diem.sad.progetto_sad;

import it.unisa.diem.sad.progetto_sad.commands.*;
import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.factories.ShapeCreator;
import it.unisa.diem.sad.progetto_sad.fileHandlers.FileManager;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
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
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Group group;

    @FXML
    private RadioButton Zoom50;
    @FXML
    private RadioButton Zoom100;
    @FXML
    private RadioButton Zoom150;
    @FXML
    private RadioButton Zoom200;

    private final double BUTTON_SHADOW_RADIUS    = 13;              // Dimensione effetto evidenziazione dei bottoni
    private final Color  BUTTON_SHADOW_COLOR     = Color.BLUE;      // Colore effetto evidenziazione dei bottoni
    private final double SELECTION_SHADOW_RADIUS = 20;              // Dimensione effetto evidenziazione delle forme selezionate
    private final Color  SELECTION_SHADOW_COLOR  = Color.BLUE;      // Colore effetto evidenziazione delle forme selezionate

    private Button highlightedButton;               // Bottone di scelta delle forme evidenziato
    private ShapeCreator chosenShape;               // Forma scelta da creare 
    private ContextMenu shapeContextMenu;           // Menu contestuale delle forme
    private ContextMenu workspaceContextMenu;       // Menu contestuale dello spazio di lavoro
    private double workspaceContextMenuX;           // Coordinata X del punto di apparizione del menu contestuale dello spazio di lavoro
    private double workspaceContextMenuY;           // Coordinata Y del punto di apparizione del menu contestuale dello spazio di lavoro
    private ShapeInterface copiedShape;             // Forma copiata

    private double dragOffsetX;                      // Coordinata X del mouse quando inizia a trascinare una forma
    private double dragOffsetY;                      // Coordinata Y del mouse quando inizia a trascinare una forma
    private ShapeInterface selectedShape;           // Riferimento alla forma selezionata

    // Coordinate del mouse al momento dell'ultimo evento mouse press (utili per il panning)
    private double lastMouseX;
    private double lastMouseY;

    // Indica se l'utente sta trascinando attivamente una forma (usato per distinguere tra panning e drag di forme)
    private boolean isDraggingShape = false;

    // Margine oltre il quale lo spazio di lavoro può essere espanso dinamicamente
    private static final double EXPANSION_MARGIN = 50;

    private static final double EXPANSION_STEP = 300; // Quantità di pixel da aggiungere al workspace durante l'espansione
    private static final double EXPANSION_THRESHOLD = 0.97; // Soglia oltre la quale si attiva l'espansione (vicinanza al bordo)

    // Indica se il panning è attualmente attivo
    private boolean isPanning = false;

    // Coordinate iniziali del mouse al momento del click per il panning
    private double initialMouseX;
    private double initialMouseY;

    // Valori iniziali di scroll della ScrollPane (orizzontale e verticale)
    private double initialHValue;
    private double initialVValue;

    private final CommandHistory history = new CommandHistory();    //History dei comandi

    /**
     * Inizializza il controller dopo il caricamento del file FXML.
     * Crea il menu contestuale che sarà utilizzato per le forme.
     * Crea il menu contestuale che sarà utilizzato per lo spazio di lavoro.
     *
     * @param url            URL utilizzato per inizializzare l'oggetto.
     * @param resourceBundle Risorse per l'internazionalizzazione, se presenti.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shapeContextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Elimina");
        MenuItem resizeItem = new MenuItem("Ridimensiona");
        MenuItem copyItem = new MenuItem("Copia");
        MenuItem cutItem = new MenuItem("Taglia");
        MenuItem toFrontItem = new MenuItem("Porta avanti");
        MenuItem toBackItem = new MenuItem("Porta dietro");

        deleteItem.setOnAction(e -> {
            Command delete = new DeleteCommand(workspace, (ShapeInterface) shapeContextMenu.getOwnerNode()); //Creo ed eseguo il comando per l'eliminazione
            executeCommand(delete);
        });

        resizeItem.setOnAction(e -> {
            Command resize = new ResizeCommand((ShapeInterface) shapeContextMenu.getOwnerNode()); //Creo ed eseguo il comando per il ridimensionamento
            executeCommand(resize);
        });

        copyItem.setOnAction(e -> {
            copiedShape = ((ShapeInterface) shapeContextMenu.getOwnerNode()).clone();
        });

        cutItem.setOnAction(e -> {
            ShapeInterface toCut  = (ShapeInterface) shapeContextMenu.getOwnerNode();
            copiedShape = toCut.clone();
            Command delete = new DeleteCommand(workspace, toCut);  //Creo ed eseguo il comando per l'eliminazione
            executeCommand(delete);
        });

        toFrontItem.setOnAction(e -> {
            Command toFront = new BringFrontCommand(workspace, (ShapeInterface) shapeContextMenu.getOwnerNode());    // Creo ed eseguo il comando per portare avanti la forma cliccata
            executeCommand(toFront);
        });

        toBackItem.setOnAction(e -> {
            Command toBack = new BringBackCommand(workspace, (ShapeInterface) shapeContextMenu.getOwnerNode());    // Creo ed eseguo il comando per portare dietro la forma cliccata
            executeCommand(toBack);
        });

        shapeContextMenu.getItems().addAll(deleteItem, resizeItem, copyItem, cutItem, toFrontItem, toBackItem);


        workspaceContextMenu = new ContextMenu();
        MenuItem pasteItem = new MenuItem("Incolla");
        pasteItem.setOnAction(e -> {
            ShapeInterface newShape = copiedShape.clone();              // Clona la shape copiata
            newShape.setShapeX(workspaceContextMenuX);                  // la posiziona nelle coordinate del menu contestuale
            newShape.setShapeY(workspaceContextMenuY);
            addShapeEvents(newShape);                                   // aggiunta degli eventi alla nuova forma clonata
            Command insert = new InsertCommand(workspace, newShape);    //Creo ed eseguo il comando per l'inserimento
            executeCommand(insert);
        });

        workspaceContextMenu.setOnShown(e -> {
            pasteItem.setDisable(copiedShape == null);          // disabilitazione della voce 'Incolla' nel caso in cui non fosse stata copiata nessuna forma
        });
        workspaceContextMenu.getItems().add(pasteItem);

        setupPanning(); // Inizializza la logica per il panning del workspace con trascinamento del mouse su aree vuote
    }

    /**
     * Imposta il comportamento di panning dello spazio di lavoro.
     * Quando l'utente clicca e trascina su un'area vuota del workspace con il tasto sinistro del mouse,
     * la visualizzazione viene spostata (scrollata) nella direzione del trascinamento.
     * Durante il trascinamento, se il bordo visibile viene raggiunto, il workspace si espande automaticamente.
     */
    private void setupPanning() {
        workspace.setOnMousePressed(event -> {
            // Solo se si clicca in un'area vuota (non una forma)
            if (event.getTarget() == workspace) {
                isPanning = true;
                initialMouseX = event.getSceneX();
                initialMouseY = event.getSceneY();
                initialHValue = scrollPane.getHvalue();
                initialVValue = scrollPane.getVvalue();
            }
        });

        workspace.setOnMouseDragged(event -> {
            if (!isPanning) return;

            double deltaX = initialMouseX - event.getSceneX();
            double deltaY = initialMouseY - event.getSceneY();

            double hMax = scrollPane.getHmax();
            double vMax = scrollPane.getVmax();

            // Aggiorna i valori di scorrimento in base al movimento
            scrollPane.setHvalue(clamp(initialHValue + deltaX / workspace.getWidth(), 0, hMax));
            scrollPane.setVvalue(clamp(initialVValue + deltaY / workspace.getHeight(), 0, vMax));

            // Espansione solo se si è vicini ai bordi (ma con limite)
            boolean expanded = false;

            if (scrollPane.getHvalue() > EXPANSION_THRESHOLD) {
                workspace.setPrefWidth(workspace.getPrefWidth() + EXPANSION_STEP);
                expanded = true;
            } else if (scrollPane.getHvalue() < (1 - EXPANSION_THRESHOLD) && scrollPane.getHvalue() == 0) {
                workspace.setPrefWidth(workspace.getPrefWidth() + EXPANSION_STEP);
                scrollPane.setHvalue(EXPANSION_STEP / workspace.getPrefWidth());
                expanded = true;
            }

            if (scrollPane.getVvalue() > EXPANSION_THRESHOLD) {
                workspace.setPrefHeight(workspace.getPrefHeight() + EXPANSION_STEP);
                expanded = true;
            } else if (scrollPane.getVvalue() < (1 - EXPANSION_THRESHOLD) && scrollPane.getVvalue() == 0) {
                workspace.setPrefHeight(workspace.getPrefHeight() + EXPANSION_STEP);
                scrollPane.setVvalue(EXPANSION_STEP / workspace.getPrefHeight());
                expanded = true;
            }

            // Se espanso, aggiorna i valori iniziali per evitare ricalcoli eccessivi
            if (expanded) {
                initialHValue = scrollPane.getHvalue();
                initialVValue = scrollPane.getVvalue();
                initialMouseX = event.getSceneX();
                initialMouseY = event.getSceneY();
            }
        });

        workspace.setOnMouseReleased(event -> {
            isPanning = false;
        });
    }


    /**
     * Limita un valore all'interno di un intervallo definito.
     *
     * @param value valore da limitare
     * @param min   valore minimo consentito
     * @param max   valore massimo consentito
     * @return valore limitato all'intervallo [min, max]
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));  // Restituisce il valore limitato
    }

    /**
     * Espande dinamicamente le dimensioni del workspace se il bordo visibile viene raggiunto.
     * Aggiunge margine extra a destra o in basso per consentire all'utente di continuare
     * a spostarsi nello spazio di lavoro senza restrizioni visive.
     */
    private void expandWorkspace() {
        Bounds viewportBounds = scrollPane.getViewportBounds();
        double viewportWidth = viewportBounds.getWidth();
        double viewportHeight = viewportBounds.getHeight();

        double hValue = scrollPane.getHvalue();
        double vValue = scrollPane.getVvalue();

        double contentWidth = workspace.getWidth();
        double contentHeight = workspace.getHeight();

        double viewLeft = hValue * (contentWidth - viewportWidth);
        double viewRight = viewLeft + viewportWidth;

        double viewTop = vValue * (contentHeight - viewportHeight);
        double viewBottom = viewTop + viewportHeight;

        boolean expanded = false;

        // Espansione verso destra
        if (viewRight >= contentWidth - EXPANSION_MARGIN) {
            workspace.setPrefWidth(workspace.getPrefWidth() + EXPANSION_MARGIN);
            expanded = true;
        }

        // Espansione verso il basso
        if (viewBottom >= contentHeight - EXPANSION_MARGIN) {
            workspace.setPrefHeight(workspace.getPrefHeight() + EXPANSION_MARGIN);
            expanded = true;
        }

        // Espansione verso sinistra
        if (viewLeft <= EXPANSION_MARGIN) {
            workspace.setPrefWidth(workspace.getPrefWidth() + EXPANSION_MARGIN);
            shiftContent(EXPANSION_MARGIN, 0); // Sposta i contenuti a destra
            scrollPane.setHvalue((viewLeft + EXPANSION_MARGIN) / (workspace.getWidth() - viewportWidth));
            expanded = true;
        }

        // Espansione verso l’alto
        if (viewTop <= EXPANSION_MARGIN) {
            workspace.setPrefHeight(workspace.getPrefHeight() + EXPANSION_MARGIN);
            shiftContent(0, EXPANSION_MARGIN); // Sposta i contenuti in basso
            scrollPane.setVvalue((viewTop + EXPANSION_MARGIN) / (workspace.getHeight() - viewportHeight));
            expanded = true;
        }
    }

    private void shiftContent(double deltaX, double deltaY) {
        for (Node node : group.getChildren()) {
            node.setLayoutX(node.getLayoutX() + deltaX);
            node.setLayoutY(node.getLayoutY() + deltaY);
        }
    }


    /**
     * Evidenzia visivamente il bottone selezionata applicando un effetto visivo.
     * Se un altro bottone era precedentemente evidenziato, rimuove l'effetto a quello precedente.
     * Se il metodo viene chiamato sul bottone attualmente evidenziato, gli rimuove l'effetto
     *
     * @param button bottone da evidenziare
     * @return restituisce true se è stato cliccato un nuovo bottone, altrimenti false
     */
    private boolean highlightButton(Button button) {
        DropShadow highlight = new DropShadow(BUTTON_SHADOW_RADIUS, BUTTON_SHADOW_COLOR);  // effetto di evidenziazione

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
     * @param shape la forma da selezionare ed evidenziare
     */
    private void selectShape(ShapeInterface shape) {
        DropShadow highlight = new DropShadow(SELECTION_SHADOW_RADIUS, SELECTION_SHADOW_COLOR);

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
            if (chosenShape != null) {      // Se sono in modalità di disegno, creo una nuova forma e la inserisco nello spazio di lavoro
                ShapeInterface newShape = chosenShape.createShape();
                newShape.setShapeX(event.getX());
                newShape.setShapeY(event.getY());

                addShapeEvents(newShape);  //Aggiunge tutti gli eventi di interesse per la forma appena creata

                Command insert = new InsertCommand(workspace, newShape);    //Creo ed eseguo il comando per l'inserimento
                executeCommand(insert);
            }
        }else if (event.getButton() == MouseButton.SECONDARY){      // Se viene effettuato un clic destro sullo spazio di lavoro, viene fatto apparire il suo menu contestuale
            workspaceContextMenuX = event.getX();
            workspaceContextMenuY = event.getY();
            workspaceContextMenu.show(workspace.getScene().getWindow(), event.getScreenX(), event.getScreenY());
        }

        deselectShape();        // Deselezionamento della forma selezionata ogni volta che si clicca nello spazio di lavoro
    }


    /**
     * Registra tutti gli eventi interattivi necessari per una forma geometrica.
     * Gli eventi gestiscono:
     *     La selezione della forma tramite click sinistro o destro.
     *     La visualizzazione del menu contestuale al click destro.
     *     Il trascinamento della forma con il mouse (drag and drop).
     *
     * @param shape la forma a cui associare gli eventi di interazione.
     */
    private void addShapeEvents(ShapeInterface shape){
        Shape shapeEvent = (Shape) shape;

        shapeEvent.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY){     //Evento alla pressione del tasto destro
                shapeContextMenu.show(shapeEvent, event.getScreenX(), event.getScreenY());    //Mostra menu contestuale
                selectShape(shape);
                event.consume();
            }else if(event.getButton() == MouseButton.PRIMARY && chosenShape == null){
                selectShape(shape); // seleziona la forma solo se nessun'altra è già selezionata
                event.consume();

            }
        });

        // Evento di pressione del mouse per iniziare il trascinamento
        shapeEvent.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY){

                if (chosenShape == null){
                    // Calcola l'offset iniziale tra il punto cliccato e la posizione della forma
                    dragOffsetX = ((ShapeInterface) event.getTarget()).getShapeX() - event.getX();
                    dragOffsetY = ((ShapeInterface) event.getTarget()).getShapeY() - event.getY();

                    selectShape(shape); // Seleziona la forma
                }
                isDraggingShape = true;  // Segnala che una forma è in fase di trascinamento
            }
        });

        shapeEvent.setOnDragDetected(event -> {
            Command drag = new DragCommand(shape);
            executeCommand(drag);
        });

        shapeEvent.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY && chosenShape == null) {
                shape.setShapeX(event.getX() + dragOffsetX); // aggiorna posizione X in base al cursore
                shape.setShapeY(event.getY() + dragOffsetY); // aggiorna posizione Y in base al cursore

                double margin = 30;         // Margine dai bordi per attivare lo scroll
                double scrollSpeed = 0.01;  // Velocità dello scroll automatico

                // Ottiene coordinate del mouse all'interno dello scrollPane
                Bounds viewportBounds = scrollPane.getViewportBounds();
                Point2D mouseInScrollPane = scrollPane.screenToLocal(event.getScreenX(), event.getScreenY());

                double mouseX = mouseInScrollPane.getX();
                double mouseY = mouseInScrollPane.getY();

                double oldHValue = scrollPane.getHvalue();
                double oldVValue = scrollPane.getVvalue();

                // Scroll orizzontale
                if (mouseX > viewportBounds.getWidth() - margin) {
                    scrollPane.setHvalue(Math.min(1.0, oldHValue + scrollSpeed));
                } else if (mouseX < margin) {
                    scrollPane.setHvalue(Math.max(0.0, oldHValue - scrollSpeed));
                }

                // Scroll verticale
                if (mouseY > viewportBounds.getHeight() - margin) {
                    scrollPane.setVvalue(Math.min(1.0, oldVValue + scrollSpeed));
                } else if (mouseY < margin) {
                    scrollPane.setVvalue(Math.max(0.0, oldVValue - scrollSpeed));
                }

                // Calcola lo spostamento effettivo dello scroll e applicalo alla forma
                double deltaH = scrollPane.getHvalue() - oldHValue;
                double deltaV = scrollPane.getVvalue() - oldVValue;

                double scrollDX = deltaH * (workspace.getWidth() - viewportBounds.getWidth());
                double scrollDY = deltaV * (workspace.getHeight() - viewportBounds.getHeight());

                shape.setShapeX(shape.getShapeX() + scrollDX);
                shape.setShapeY(shape.getShapeY() + scrollDY);
            }

            shapeContextMenu.hide();
        });

        shapeEvent.setOnMouseReleased(event -> {
            isDraggingShape = false;
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

        if(selectedShape != null){
            Command changeStrokeColor = new ChangeStrokeColorCommand(selectedShape, selectedColor); //Creo ed eseguo il comando per il cambio colore bordi
            executeCommand(changeStrokeColor);
        }
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

        if (selectedShape instanceof Shape2D){
            Command changeFillColor = new ChangeFillColorCommand((Shape2D) selectedShape, selectedColor); //Creo ed eseguo il comando per il cambio colore riempimento
            executeCommand(changeFillColor);
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
                    for (ShapeInterface shape : shapeList) {
                        addShapeEvents(shape);
                        workspace.getChildren().add((Shape) shape);
                    }
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
    /**
     * Gestisce la modifica del livello di zoom dello spazio di lavoro in base al RadioButton selezionato.
     * Il metodo controlla quale RadioButton è attualmente selezionato tra i quattro disponibili (50%, 100%, 150%, 200%)
     * e imposta la scala (zoom) del workspace di conseguenza.
     * Viene applicato sia all'asse X che all'asse Y del workspace, per mantenere le proporzioni.
     */

    @FXML
    protected void handleZoomChange() {
        double scale = 1.0;

        if (Zoom50.isSelected()) {
            scale = 0.5;
        } else if (Zoom100.isSelected()) {
            scale = 1.0;
        } else if (Zoom150.isSelected()) {
            scale = 1.5;
        } else if (Zoom200.isSelected()) {
            scale = 2.0;
        }

        workspace.setScaleX(scale);
        workspace.setScaleY(scale);
    }

    /**
     * Esegue il comando specificato invocandone il metodo
     * e lo aggiunge allo storico dei comandi
     *
     * @param command il comando da eseguire.
     */
    private void executeCommand(Command command){
        command.execute();
        history.push(command);
    }


    /**
     * Evento associato alla pressione del bottone di annullamento.
     * Deve annullare l'ultimo command eseguito
     *
     */
    @FXML
    void undo() {
        if (!history.isEmpty()){
            Command command = history.pop();
            command.undo();
        }
    }
}