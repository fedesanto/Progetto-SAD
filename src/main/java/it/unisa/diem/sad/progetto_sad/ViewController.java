package it.unisa.diem.sad.progetto_sad;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    private Pane selectableShapes;
    private Shape highlightedShape;
    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private Pane workspace;

    private ShapeCreator selectedShape;

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
    protected void chosenLine() {
        selectedShape = new Shape1DCreator(Shape1D.TYPE_1D.LINE, strokeColorPicker.getValue());
    }

    @FXML
    protected void chosenRectangle() {
        selectedShape = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, strokeColorPicker.getValue(), fillColorPicker.getValue());
    }

    @FXML
    protected void chosenEllipse() {
        selectedShape = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, strokeColorPicker.getValue(), fillColorPicker.getValue());
    }

    @FXML
    protected void addShape() {
        ShapeInterface shape = selectedShape.createShape();
        workspace.getChildren().add(shape.getShape());
    }

    @FXML
    protected void pickedStrokeColor() {
        selectedShape.setStrokeColor(strokeColorPicker.getValue());
    }

    @FXML
    protected void pickedFillColor() {
        if(selectedShape instanceof Shape2DCreator) {
            ((Shape2DCreator) selectedShape).setFillColor(fillColorPicker.getValue());
        }
    }
}