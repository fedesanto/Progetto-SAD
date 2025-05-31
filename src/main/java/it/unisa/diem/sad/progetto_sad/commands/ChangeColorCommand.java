package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.Shape2D;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ChangeColorCommand implements Command{
    private final ShapeInterface shape;
    private final Color strokeColor;
    private final Color fillColor;
    private Color prevStrokeColor;
    private Color prevFillColor;

    public ChangeColorCommand(ShapeInterface shape, Color strokeColor, Color fillColor) {
        this.shape = shape;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    public void execute() {
        prevStrokeColor = strokeColor;
        prevFillColor = fillColor;

        shape.setStrokeColor(strokeColor);

        if(shape instanceof Shape2D)
            ((Shape2D) shape).setFillColor(fillColor);
    }

    public void undo() {
        shape.setStrokeColor(prevStrokeColor);

        if(shape instanceof Shape2D)
            ((Shape2D) shape).setFillColor(prevFillColor);
    }
}
