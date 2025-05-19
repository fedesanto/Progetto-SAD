package it.unisa.diem.sad.progetto_sad;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private Pane selectableShapes;
    private Shape highlightedShape;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void onHighlighting() {
        DropShadow highlight = new DropShadow(20, Color.BLUE);

        for (Node shape : selectableShapes.getChildren()) {
            if (highlightedShape != null) {
                highlightedShape.setEffect(null);
            }
            highlightedShape = (Shape) shape;
            highlightedShape.setEffect(highlight);
        }
    }


    @FXML
    protected void onHelloButtonClick() {

    }
}