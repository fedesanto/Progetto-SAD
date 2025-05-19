package it.unisa.diem.sad.progetto_sad;

import javafx.scene.paint.Color;

public class Shape1DCreator extends ShapeCreator {
    public TYPE_1D type;
    private Color stroke;
    private final double D1_LENGTH = 80.0;

    public Shape1DCreator(TYPE_1D type, Color stroke) {
        this.type = type;
        this.stroke = stroke;
    }

    public void setStrokeColor(Color color) {
        this.stroke = color;
    }

    @Override
    public abstract ShapeInterface createShape();

}



