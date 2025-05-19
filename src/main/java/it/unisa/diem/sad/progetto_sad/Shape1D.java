package it.unisa.diem.sad.progetto_sad;
import javafx.scene.paint.Paint;

public interface Shape1D extends ShapeInterface{
    void setX(double X);
    void setY(double Y);
    double getX();
    double getY();
    void setStrokeColor(Paint color);
    Paint getStrokeColor();
    void setLength(double length);
    double getLength();
    String toString();

}
