package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;

public interface Shape2D extends ShapeInterface{

    enum TYPE_2D {
        RECTANGLE,
        ELLIPSE
    }

    void setShapeWidth(double width);
    double getShapeWidth();
    void setShapeHeight(double heigth);
    double getShapeHeight();
    void setFillColor(Color color);
    Color getFillColor();
}
