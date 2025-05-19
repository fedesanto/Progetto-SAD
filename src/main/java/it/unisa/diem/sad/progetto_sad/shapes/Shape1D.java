package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;

public interface Shape1D extends ShapeInterface{

    enum TYPE_1D {
        LINE
    }

    void setLength(double length);
    double getLength();
}
