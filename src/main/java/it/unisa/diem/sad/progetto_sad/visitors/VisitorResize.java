package it.unisa.diem.sad.progetto_sad.visitors;

import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Visitor concreto per il ridimensionamento delle forme geometriche.
 *
 * Implementa l'interfaccia VisitorShape e fornisce un'interfaccia
 * grafica per modificare le dimensioni delle forme Shape1D e Shape2D.
 */
public class VisitorResize implements VisitorShape {

    // Espressione regolare per numeri decimali positivi
    private final String DECIMAL_REGEX = "\\d*(\\.\\d+)?";

    /**
     * Visualizza una finestra di dialogo per ridimensionare una forma 1D.
     *
     * La finestra consente all'utente di inserire una nuova lunghezza. Il valore inserito viene validato
     * per assicurarsi che sia un numero decimale positivo. Se la validazione ha successo, la lunghezza
     * della forma viene aggiornata di conseguenza.
     *
     * @param shape1D la forma 1D da ridimensionare
     */
    public void visit(Shape1D shape1D) {
        Dialog<Void> dialog = createDialog("Ridimensiona forma 1D", "Inserisci lunghezza");

        // Campo input con la lunghezza corrente della forma
        TextField lengthField = new TextField(String.valueOf(shape1D.getShapeLength()));

        // Layout con una sola riga: [ "Lunghezza:" | campo di testo ]
        GridPane grid = createGridPane(new String[]{"Lunghezza:"}, new TextField[]{lengthField});
        dialog.getDialogPane().setContent(grid);

        // Bottoni
        Node confirmButton = createButtons(dialog);

        // Abilitazione/disabilitazione del pulsante "Conferma" in base alla presenza di input nel campo
        lengthField.textProperty().addListener((obs, oldVal, newVal) -> {
            confirmButton.setDisable(newVal.trim().isEmpty());
        });

        // Aggiunta filtro al bottone per validare l'input prima che venga chiusa la finestra
        (confirmButton).addEventFilter(ActionEvent.ACTION, event -> {
            String input = lengthField.getText().trim();

            // Controlla che l'input rispetti il formato decimale
            if (!input.matches(DECIMAL_REGEX)) {
                showError(event);    //Mostra un messaggio d'errore e blocca la chiusura della finestra
            } else {
                // Se l'input è valido, aggiorna la lunghezza della forma
                double newL = Double.parseDouble(input);
                shape1D.setShapeLength(newL);
            }
        });

        dialog.showAndWait();
    }

    /**
     * Visualizza una finestra di dialogo per ridimensionare una forma 2D.
     *
     * La finestra consente all'utente di inserire nuove dimensioni di larghezza e altezza. I valori inseriti
     * vengono validati per assicurarsi che siano numeri decimali positivi. Se la validazione ha successo,
     * le dimensioni della forma vengono aggiornate di conseguenza.
     *
     * @param shape2D la forma 2D da ridimensionare
     */
    public void visit(Shape2D shape2D) {
        Dialog<Void> dialog = createDialog("Ridimensiona forma 2D", "Inserisci larghezza e altezza");

        // Campi input con larghezza e altezza correnti.
        TextField widthField = new TextField(String.valueOf(shape2D.getShapeWidth()));
        TextField heightField = new TextField(String.valueOf(shape2D.getShapeHeight()));

        // Layout con due righe: [ "Larghezza:" | campo ] e [ "Altezza:" | campo ]
        GridPane grid = createGridPane(new String[]{"Larghezza:", "Altezza:"}, new TextField[]{widthField, heightField});
        dialog.getDialogPane().setContent(grid);

        // Bottoni
        Node confirmButton = createButtons(dialog);

        // Listener condiviso per entrambi i campi: abilitazione del bottone solo se entrambi hanno contenuto
        ChangeListener<String> fieldListener = (obs, oldVal, newVal) -> {
            boolean valid = !widthField.getText().trim().isEmpty() &&
                    !heightField.getText().trim().isEmpty();
            confirmButton.setDisable(!valid);
        };
        widthField.textProperty().addListener(fieldListener);
        heightField.textProperty().addListener(fieldListener);

        // Filtro per la validazione e l'aggiornamento delle dimensioni
        (confirmButton).addEventFilter(ActionEvent.ACTION, event -> {
            String w = widthField.getText().trim();
            String h = heightField.getText().trim();

            if (!w.matches(DECIMAL_REGEX) || !h.matches(DECIMAL_REGEX)) {
                showError(event);
            } else {
                double newW = Double.parseDouble(w);
                double newH = Double.parseDouble(h);
                shape2D.setShapeWidth(newW);
                shape2D.setShapeHeight(newH);
            }
        });

        dialog.showAndWait();
    }

    /**
     * Crea e configura una finestra di dialogo JavaFX con un titolo e un'intestazione specificati.
     *
     * @param title  il titolo della finestra di dialogo
     * @param header il testo dell'intestazione della finestra di dialogo
     * @return un oggetto Dialog configurato
     */
    private Dialog<Void> createDialog(String title, String header) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        return dialog;
    }

    /**
     * Crea un layout GridPane con etichette e campi di testo associati.
     * Ogni etichetta è posizionata accanto al campo di testo corrispondente in una riga del grid pane.
     *
     * @param labels le etichette da visualizzare
     * @param fields i campi di testo associati alle etichette
     * @return un oggetto GridPane contenente le etichette e i campi di testo
     */
    private GridPane createGridPane(String[] labels, TextField[] fields) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        int minLength = Math.min(labels.length, fields.length);

        for (int i = 0; i < minLength; i++) {
            Label label = new Label(labels[i]);
            grid.addRow(i, label, fields[i]);
        }

        return grid;
    }

    /**
     * Aggiunge i pulsanti "Conferma" e "Annulla" alla finestra di dialogo specificata.
     * Restituisce il pulsante "Conferma" per consentire l'aggiunta di listener o filtri eventi.
     *
     * @param dialog la finestra di dialogo a cui aggiungere i pulsanti
     * @return il nodo corrispondente al pulsante "Conferma"
     */
    private Node createButtons(Dialog<Void> dialog) {
        ButtonType okButtonType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        return dialog.getDialogPane().lookupButton(okButtonType);
    }

    /**
     * Mostra un messaggio di errore in una finestra di avviso quando l'input dell'utente non è valido.
     * Il messaggio informa l'utente che deve inserire un numero decimale positivo valido.
     * L'evento associato viene consumato per impedire la chiusura della finestra di dialogo.
     *
     * @param event l'evento da consumare per impedire la chiusura della finestra di dialogo
     */
    private void showError(Event event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText("Inserisci un numero decimale positivo valido! (es. 123.45)");
        alert.showAndWait();
        event.consume(); // Non chiude la finestra di dialogo
    }

}