package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;

public class ChangeStrokeColorCommand implements Command{
    private final ShapeInterface shape;
    private final Color strokeColor;
    private Color prevStrokeColor;


    public ChangeStrokeColorCommand(ShapeInterface shape, Color strokeColor) {
        this.shape = shape;
        this.strokeColor = strokeColor;
    }

    public void execute() {
        prevStrokeColor = strokeColor;
        shape.setStrokeColor(strokeColor);
    }

    public void undo() {
        shape.setStrokeColor(prevStrokeColor);
    }
}
