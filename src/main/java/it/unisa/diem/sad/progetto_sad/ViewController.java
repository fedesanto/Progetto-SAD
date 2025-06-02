package it.unisa.diem.sad.progetto_sad;

import it.unisa.diem.sad.progetto_sad.commands.*;
import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.factories.ShapeCreator;
import it.unisa.diem.sad.progetto_sad.fileHandlers.FileManager;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.application.Platform;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private Button undoButton;

    @FXML
    private RadioButton Zoom50;
    @FXML
    private RadioButton Zoom100;
    @FXML
    private RadioButton Zoom150;
    @FXML
    private RadioButton Zoom200;
    @FXML
    private ToggleButton gridButton;
    @FXML
    private TextField gridSizeField;

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
    private double startDragX;                      // Coordinata X del mouse quando inizia a trascinare una forma
    private double startDragY;
    private ShapeInterface selectedShape;            // Riferimento alla forma selezionata

    private final CommandHistory history = new CommandHistory();    //History dei comandi

    // Coordinate iniziali del panning (quando l'utente inizia a trascinare lo sfondo)
    private double panStartX;
    private double panStartY;

    // Valore costante che definisce di quanto si espande il workspace ogni volta che serve più spazio
    private static final double EXPANSION_STEP = 100; // massimo incremento per volta

    // Margine, in pixel, entro il quale se il mouse si avvicina al bordo durante il panning, si attiva l'espansione automatica
    private static final double MARGIN = 50; // margine vicino ai bordi per triggerare l'espansione

    // Flag che indica se l'utente sta trascinando una forma (serve per distinguere il panning dal drag delle forme)
    private boolean isDraggingShape = false;

    private Group gridGroup = new Group();
    private int gridSpacing = 25; // Spaziatura tra le linee della griglia in pixel

    /**
     * Inizializza il controller dopo il caricamento del file FXML.
     * Crea il menu contestuale che sarà utilizzato per le forme.
     * Crea il menu contestuale che sarà utilizzato per lo spazio di lavoro.
     * Inizializza i color picker.
     * Effettua il setup necessario per abilitare il panning dello spazio di lavoro.
     * Effettuo il setup necessario per consentire la visualizzazione della griglia.
     * Associa la proprietà di disattivazione dell'undo button alla proprietà 'empty' della history.
     * Imposta il valore iniziale al text field della griglia.
     *
     * @param url            URL utilizzato per inizializzare l'oggetto.
     * @param resourceBundle Risorse per l'internazionalizzazione, se presenti.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Inizializza il menu contestuale associato alle forme
        initShapeContextMenu();

        // Inizializza il menu contestuale associato al workspace
        initWorkspaceContextMenu();

        // Inizializza i color picker
        initColorPicker(strokeColorPicker);
        initColorPicker(fillColorPicker);

        // Effettuo il setup necessario per permettere il panning dello spazio di lavoro
        setUpPanning();

        // Effettuo il setup necessario per consentire la visualizzazione della griglia
        setUpGrid();

        //Associa la proprietà osservabile 'empty' della history alla proprietà di disattivazione dell'undo button
        undoButton.disableProperty().bind(history.emptyProperty());

        // Imposta nel campo di testo il valore corrente della spaziatura della griglia
        gridSizeField.setText(String.valueOf(gridSpacing));
    }

    /**
     * Metodo di comodo per l'inizializzazione del menu contestuale associato alle forme
     */
    private void initShapeContextMenu(){
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
            sendGridToBack(); // assicura che la griglia resti sempre dietro le forme
        });

        shapeContextMenu.getItems().addAll(deleteItem, resizeItem, copyItem, cutItem, toFrontItem, toBackItem);
    }

    /**
     * Metodo di comodo per l'inizializzazione del menu contestuale associato allo spazio di lavoro
     */
    private void initWorkspaceContextMenu(){
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
    }

    /**
     * Metodo di comodo per l'inizializzazione di un color picker
     */
    private void initColorPicker(ColorPicker colorPicker){
        // Implementazione per risolvere il bug per cui l'applicazione si chiude quando si sceglie un colore personalizzato
        colorPicker.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing && !isNowShowing) {
                // Il ColorPicker è appena stato chiuso (anche se il colore non è cambiato)
                Platform.runLater(() -> {
                    Stage stage = (Stage) colorPicker.getScene().getWindow();
                    stage.toFront();        // Riapre la finesta se è stata chiusa
                });
            }
        });
    }

    /**
     * Metodo di comodo per il setup delle operazioni necessaria alla visualizzazione della griglia
     */
    private void setUpPanning(){
        // Gestore per il mouse press sul workspace: inizia il panning solo se l'utente clicca su uno spazio vuoto (no forma selezionata)
        workspace.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY && chosenShape == null) {
                // Salva la posizione iniziale del mouse in coordinate di scena (serve per calcolare lo spostamento)
                panStartX = event.getSceneX();
                panStartY = event.getSceneY();
            }
        });

        // Gestore per il trascinamento del mouse sul workspace (panning)
        workspace.setOnMouseDragged(event -> {
            // Solo se non si sta trascinando una forma e nessuna forma è selezionata
            if (!isDraggingShape && chosenShape == null && event.getButton() == MouseButton.PRIMARY) {
                // Calcola lo spostamento del mouse rispetto alla posizione iniziale
                double deltaX = event.getSceneX() - panStartX;
                double deltaY = event.getSceneY() - panStartY;

                // Ottiene i valori massimi di scroll orizzontale e verticale
                double hMax = scrollPane.getHmax();
                double vMax = scrollPane.getVmax();

                // Calcola i nuovi valori di scroll (H e V) in base al movimento del mouse
                double newH = scrollPane.getHvalue() - deltaX / workspace.getWidth();
                double newV = scrollPane.getVvalue() - deltaY / workspace.getHeight();

                // Applica i nuovi valori di scroll, limitandoli tra 0 e il massimo consentito
                scrollPane.setHvalue(clamp(newH, 0, hMax));
                scrollPane.setVvalue(clamp(newV, 0, vMax));

                // Aggiorna la posizione iniziale del mouse per il prossimo movimento
                panStartX = event.getSceneX();
                panStartY = event.getSceneY();

                // Controlla se bisogna espandere dinamicamente il workspace (in base alla posizione corrente del mouse)
                expandWorkspace();
            }
        });
    }

    /**
     * Metodo di comodo per il setup della griglia
     */
    private void setUpGrid(){
        // Aggiunge il gruppo della griglia al workspace e lo nasconde inizialmente
        workspace.getChildren().add(gridGroup);
        gridGroup.setVisible(false); // Nasconde la griglia all'avvio

        // Gestisce il click sul pulsante della griglia
        gridButton.setOnAction(e -> {
            if (gridButton.isSelected()) {
                gridGroup.setVisible(true);  // Mostra la griglia
                drawGrid();                  // Disegna la griglia
            } else {
                gridGroup.setVisible(false); // Nasconde la griglia
            }
        });

        // Ridisegna la griglia quando la larghezza del workspace cambia (se visibile)
        workspace.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (gridGroup.isVisible()) {
                drawGrid();
            }
        });

        // Ridisegna la griglia quando l'altezza del workspace cambia (se visibile)
        workspace.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (gridGroup.isVisible()) {
                drawGrid();
            }
        });
    }

    /**
     * Disegna una griglia sull'area di lavoro, con linee verticali e orizzontali
     * distanziate secondo la costante GRID_SPACING. La griglia si estende per tutta
     * l'area visibile del workspace.
     * Le linee sono aggiunte al gruppo gridGroup.
     * Viene prima cancellata l'eventuale griglia esistente.
     */
    private void drawGrid() {
        clearGrid(); // Pulisce griglia esistente

        double scale = workspace.getScaleX();

        // Dimensioni reali visibili nello ScrollPane in coordinate NON scalate
        double visibleWidth = scrollPane.getViewportBounds().getWidth() / scale;
        double visibleHeight = scrollPane.getViewportBounds().getHeight() / scale;

        // Considera anche la dimensione effettiva del workspace
        double width = Math.max(workspace.getPrefWidth(), visibleWidth);
        double height = Math.max(workspace.getPrefHeight(), visibleHeight);

        for (double x = 0; x < width; x += gridSpacing) {
            Line line = new Line(x, 0, x, height);
            line.setStroke(Color.LIGHTGRAY);
            gridGroup.getChildren().add(line);
        }

        for (double y = 0; y < height; y += gridSpacing) {
            Line line = new Line(0, y, width, y);
            line.setStroke(Color.LIGHTGRAY);
            gridGroup.getChildren().add(line);
        }
    }

    /**
     * Rimuove tutte le linee presenti nel gruppo gridGroup,
     * eliminando la griglia visiva dall'area di lavoro.
     */
    private void clearGrid() {
        gridGroup.getChildren().clear();
    }

    /**
     * Invia il gruppo della griglia in fondo allo stack dei nodi del workspace,
     * assicurando che la griglia non copra le forme visibili.
     * Se il gruppo gridGroup è presente nel workspace, viene rimosso e riaggiunto
     * come primo figlio.
     */
    private void sendGridToBack() {
        if (workspace.getChildren().contains(gridGroup)) {
            workspace.getChildren().remove(gridGroup);
            workspace.getChildren().add(0, gridGroup); // mette gridGroup come primo figlio → dietro visivamente
        }
    }

    /**
     * Limita un valore entro un intervallo specificato.
     * Se il valore è minore del minimo, restituisce il minimo.
     * Se è maggiore del massimo, restituisce il massimo.
     * Altrimenti restituisce il valore stesso.
     *
     * @param value Valore da limitare.
     * @param min   Valore minimo consentito.
     * @param max   Valore massimo consentito.
     * @return Il valore limitato entro [min, max].
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    /**
     * Espande dinamicamente le dimensioni del workspace se il bordo visibile viene raggiunto.
     * Aggiunge margine extra a destra o in basso per consentire all'utente di continuare
     * a spostarsi nello spazio di lavoro senza restrizioni visive.
     */
    private void expandWorkspace() {
        Bounds contentBounds = workspace.getLayoutBounds();

        double offsetX = workspace.getTranslateX();
        double offsetY = workspace.getTranslateY();


        double scrollH = scrollPane.getHvalue();
        double scrollV = scrollPane.getVvalue();

        double viewportWidth = scrollPane.getViewportBounds().getWidth();
        double viewportHeight = scrollPane.getViewportBounds().getHeight();

        double visibleX = scrollH * (contentBounds.getWidth() - viewportWidth);
        double visibleY = scrollV * (contentBounds.getHeight() - viewportHeight);

        double buffer = 200;

        boolean expandRight = visibleX + viewportWidth > contentBounds.getWidth() - buffer;
        boolean expandBottom = visibleY + viewportHeight > contentBounds.getHeight() - buffer;
        boolean expandLeft = visibleX < buffer;
        boolean expandTop = visibleY < buffer;

        double newWidth = contentBounds.getWidth();
        double newHeight = contentBounds.getHeight();

        double deltaX = 0, deltaY = 0;

        if (expandRight) newWidth += 400;
        if (expandBottom) newHeight += 400;
        if (expandLeft) {
            newWidth += 400;
            deltaX = 200;
        }
        if (expandTop) {
            newHeight += 400;
            deltaY = 200;
        }

        if (newWidth != contentBounds.getWidth() || newHeight != contentBounds.getHeight()) {
            workspace.setPrefSize(newWidth, newHeight);
            for (Node node : workspace.getChildren()) {
                if (node instanceof Shape) {
                    node.setLayoutX(node.getLayoutX() + deltaX);
                    node.setLayoutY(node.getLayoutY() + deltaY);
                }
            }
            if (gridGroup.isVisible()) drawGrid();
            scrollPane.setHvalue((visibleX + deltaX) / (newWidth - viewportWidth));
            scrollPane.setVvalue((visibleY + deltaY) / (newHeight - viewportHeight));
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
            selectedShape.toJavaFXShape().setEffect(null); //Rimuove l'effetto visivo dalla forma precedentemente selezionata
        }

        selectedShape = shape;
        selectedShape.toJavaFXShape().setEffect(highlight);
    }

    /**
     * Deseleziona la forma attualmente selezionata nello spazio di lavoro, rimuovendo l'effetto visivo di evidenziazione.
     * Se non c'è nessuna forma selezionata, non esegue alcuna operazione.
     */
    private void deselectShape() {
        if (selectedShape != null) {
            selectedShape.toJavaFXShape().setEffect(null); // Rimuove l'effetto visivo dalla forma
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
        Shape shapeEvent = shape.toJavaFXShape();


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
                    // Leggo la posizione attuale della forma
                    startDragX = shape.getShapeX();
                    startDragY = shape.getShapeY();
                    
                    // Calcola l'offset iniziale tra il punto cliccato e la posizione della forma
                    dragOffsetX = startDragX - event.getX();
                    dragOffsetY = startDragY - event.getY();

                    selectShape(shape); // Seleziona la forma
                }

                isDraggingShape = true;  // Segnala che una forma è in fase di trascinamento
            }
        });

        
        shapeEvent.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY && chosenShape == null) {
                shape.setShapeX(event.getX() + dragOffsetX); // aggiorna posizione X in base al cursore
                shape.setShapeY(event.getY() + dragOffsetY); // aggiorna posizione Y in base al cursore

                expandWorkspace(); // chiamata per verificare se espandere il workspace mentre trascino la forma

                double margin = 30;         // Margine dai bordi per attivare lo scroll
                double scrollSpeed = 0.01;  // Velocità dello scroll automatico

                // Ottiene coordinate del mouse all'interno dello scrollPane
                Bounds viewportBounds = scrollPane.getViewportBounds();
                Point2D mouseInScrollPane = scrollPane.screenToLocal(event.getScreenX(), event.getScreenY());

                double mouseX = mouseInScrollPane.getX();
                double mouseY = mouseInScrollPane.getY();

                double oldHValue = scrollPane.getHvalue();
                double oldVValue = scrollPane.getVvalue();

                // Scroll orizzontale automatico se il mouse si avvicina ai bordi laterali
                if (mouseX > viewportBounds.getWidth() - margin) {
                    scrollPane.setHvalue(Math.min(1.0, oldHValue + scrollSpeed));
                } else if (mouseX < margin) {
                    scrollPane.setHvalue(Math.max(0.0, oldHValue - scrollSpeed));
                }

                // Scroll verticale automatico se il mouse si avvicina ai bordi superiori/inferiori
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

            // Se è stato rilasciato il tasto sinistro, non mi trovo in modalità di disegno ed è avvenuto un drag, creo ed eseguo il comando di drag
            if (event.getButton() == MouseButton.PRIMARY && chosenShape == null && (shape.getShapeX() != startDragX || shape.getShapeY() != startDragY)){
                Command drag = new DragCommand(shape, startDragX, startDragY);
                executeCommand(drag);
            }
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
                if (node instanceof ShapeInterface)
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
                    workspace.getChildren().add(gridGroup);
                    for (ShapeInterface shape : shapeList) {
                        addShapeEvents(shape);
                        workspace.getChildren().add(shape.toJavaFXShape());
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


        if (gridGroup.isVisible()) // Controlla se la griglia è visibile prima di ridisegnarla
            drawGrid(); // Ridisegna la griglia sull’intera area visibile
    }


    /**
     Gestisce il cambiamento della dimensione della griglia quando l'utente inserisce un nuovo valore,
     nel campo di testo. Valida l'input, aggiorna la spaziatura della griglia e la ridisegna se visibile.,
     Se l'input non è valido (non numerico o ≤ 0), mostra un messaggio di errore e ripristina il valore precedente.
     */
    @FXML
    private void onGridSizeChanged() {
        String input = gridSizeField.getText(); // Legge il valore inserito nel campo di testo
        int oldValue = gridSpacing; // Salva il valore corrente per poterlo ripristinare in caso di errore

        // Controlla che l'input sia composto solo da cifre e non vuoto
        if (input.matches("\\d+")) {
            int value = Integer.parseInt(input); // Converti in intero

            if (value > 0) {
                gridSpacing = value; // Aggiorna la spaziatura della griglia

                if (gridGroup.isVisible()) {
                     drawGrid(); // Ridisegna la griglia se visibile
                }
                return;
            }
        }

        // Input non valido: ripristina il valore precedente e mostra un errore
        gridSizeField.setText(String.valueOf(oldValue));
        showAlert("Valore non valido", "Inserisci un numero intero positivo per la dimensione della griglia.");
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
     */
    @FXML
    protected void undo() {
        if (!history.isEmpty()){
            Command command = history.pop();
            command.undo();
        }
    }
}