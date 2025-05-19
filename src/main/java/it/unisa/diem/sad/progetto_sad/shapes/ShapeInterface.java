package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;

public interface ShapeInterface {
    void setX(double X);
    void setY(double Y);
    double getX();
    double getY();
    void setStrokeColor(Color color);
    Color getStrokeColor();
    String toString();
}
