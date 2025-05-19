package it.unisa.diem.sad.progetto_sad;

import javafx.scene.paint.Paint;

public interface ShapeCreator {
    ShapeInterface createShape();
    void setStrokeColor(Paint color);
    void setFillColor(Paint color);

}
