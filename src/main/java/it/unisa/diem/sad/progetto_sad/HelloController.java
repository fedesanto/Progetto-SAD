package it.unisa.diem.sad.progetto_sad;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;

public class HelloController {
    public enum TYPE { LINE, RECTANGLE, ELLIPSE }

    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;

    private ShapeCreator selectedShape;

    @FXML
    protected void chosenLine() {
        selectedShape = new Shape1DCreator(TYPE.LINE, strokeColorPicker.getValue());
    }

    @FXML
    protected void chosenRectangle() {
        selectedShape = new Shape2DCreator(TYPE.RECTANGLE, strokeColorPicker.getValue(), fillColorPicker.getValue());
    }

    @FXML
    protected void chosenEllipse() {
        selectedShape = new Shape2DCreator(TYPE.ELLIPSE, strokeColorPicker.getValue(), fillColorPicker.getValue());
    }
}