package it.unisa.diem.sad.progetto_sad;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;

public class HelloController {

    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private ScrollPane pane;

    private ShapeCreator selectedShape;

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
        pane.getContent().getChildren().add(shape.getShape());
    }

    @FXML
    protected void pickedStrokeColor() {
        selectedShape.setStroke(strokeColorPicker.getValue());
    }

    @FXML
    protected void pickedFillColor() {
        selectedShape.setFill(fillColorPicker.getValue());
    }

}