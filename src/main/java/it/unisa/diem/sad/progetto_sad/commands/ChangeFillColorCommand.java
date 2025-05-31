package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.Shape2D;
import javafx.scene.paint.Color;

public class ChangeFillColorCommand implements Command{
    private final Shape2D shape;
    private final Color fillColor;
    private Color prevFillColor;

    public ChangeFillColorCommand(Shape2D shape, Color fillColor) {
        this.shape = shape;
        this.fillColor = fillColor;
    }

    public void execute() {
        prevFillColor = fillColor;
        shape.setFillColor(fillColor);
    }

    public void undo() {
        shape.setFillColor(prevFillColor);
    }
}
