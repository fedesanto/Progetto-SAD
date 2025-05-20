package it.unisa.diem.sad.progetto_sad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Applicazione JavaFX per il disegno, che carica una vista da FXML
 * e ne imposta titolo, dimensioni e comportamento di resizing
 */
public class DrawingApplication extends Application {
    //Attributi privati di inizializzazione
    private final String FXML_FILE = "view.fxml";
    private final int SCENE_WIDTH  = 800;
    private final int SCENE_HEIGHT  = 600;

    /**
     * Punto di ingresso dell'applicazione JavaFX
     *
     * Viene caricato il layout definito in 'view.fxml', viene creato
     * un Scene con dimensioni prefissate, e quindi viene configurato
     * lo Stage principale con titolo e proprietà di non ridimensionabilità.
     *
     * @param stage lo stage primario su cui montare la scena
     * @throws IOException se il file FXML non viene trovato o non è valido
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DrawingApplication.class.getResource(FXML_FILE));
        Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);

        stage.setTitle("Drawing application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Metodo principale di lancio dell'applicazione
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        launch();
    }
}