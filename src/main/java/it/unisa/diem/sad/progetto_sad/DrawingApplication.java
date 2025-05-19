package it.unisa.diem.sad.progetto_sad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DrawingApplication extends Application {
    private final String FXML_FILE = "view.fxml";
    private final int SCENE_WIDTH  = 800;
    private final int SCENE_HEIGHT  = 600;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DrawingApplication.class.getResource(FXML_FILE));
        Scene scene = new Scene(fxmlLoader.load(), SCENE_WIDTH, SCENE_HEIGHT);

        stage.setTitle("Drawing application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}