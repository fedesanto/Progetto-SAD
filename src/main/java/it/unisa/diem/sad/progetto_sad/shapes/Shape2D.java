package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;

public interface Shape2D extends ShapeInterface{

    enum TYPE_2D {
        RECTANGLE,
        ELLIPSE
    }

    void setWidth(double width);
    double getWidth();
    void setHeigth(double heigth);
    double getHeigth();
    void setFillColor(Color color);
    Color getFillColor();
}
