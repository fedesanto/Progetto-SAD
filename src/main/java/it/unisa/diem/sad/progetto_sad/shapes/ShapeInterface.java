package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;

public interface ShapeInterface {
    void setShapeX(double X);
    void setShapeY(double Y);
    double getShapeX();
    double getShapeY();
    void setStrokeColor(Color color);
    Color getStrokeColor();
    String toString();
}
