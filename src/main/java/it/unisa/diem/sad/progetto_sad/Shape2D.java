package it.unisa.diem.sad.progetto_sad;
import javafx.scene.paint.Paint;

public interface Shape2D extends ShapeInterface{
    void setX(double X);
    void setY( double Y);
    double getX();
    double getY();
    void setStrokeColor(Paint color);
    Paint getStrokeColor();
    void setWidth( double width);
    double getWidth();
    void setHeigth( double heigth);
    double getHeigth();
    void setFillColor( Paint color);
    Paint getFillColor();
    String toString();
}
