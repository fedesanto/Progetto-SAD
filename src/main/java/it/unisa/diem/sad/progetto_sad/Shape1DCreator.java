package it.unisa.diem.sad.progetto_sad;

import javafx.scene.paint.Paint;

public abstract class Shape1DCreator implements ShapeCreator {
    private final String type;
    private Paint stroke;

    public Shape1DCreator(String type, Paint stroke) {
        this.type = type;
        this.stroke = stroke;
    }

    public void setStrokeColor(Paint color) {
        this.stroke = color;
    }

    @Override
    public abstract ShapeInterface createShape();

}



