package it.unisa.diem.sad.progetto_sad;

import javafx.scene.paint.Paint;

public abstract class Shape1DCreator implements ShapeCreator {
    public TYPE_1D type;
    private Paint stroke;
    private final double D1_LENGTH = 80.0;

    public Shape1DCreator(TYPE_1D type, Paint stroke) {
        this.type = type;
        this.stroke = stroke;
    }

    public void setStrokeColor(Paint color) {
        this.stroke = color;
    }

    @Override
    public abstract ShapeInterface createShape();

}



