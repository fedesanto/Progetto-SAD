package it.unisa.diem.sad.progetto_sad;

import javafx.scene.paint.Color;

public interface Shape2D extends ShapeInterface{

    enum TYPE_2D {
        RECTANGLE,
        ELLIPSE
    }

    void setX(double X);
    void setY( double Y);
    double getX();
    double getY();
    void setStrokeColor(Color color);
    Color getStrokeColor();
    void setWidth( double width);
    double getWidth();
    void setHeigth( double heigth);
    double getHeigth();
    void setFillColor( Color color);
    Color getFillColor();
    String toString();
}
