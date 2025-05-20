package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;

public interface ShapeInterface {
    void setCenterX(double X);
    void setCenterY(double Y);
    double getCenterX();
    double getCenterY();
    void setStrokeColor(Color color);
    Color getStrokeColor();
    String toString();
}
