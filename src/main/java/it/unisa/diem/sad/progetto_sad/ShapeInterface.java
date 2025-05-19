package it.unisa.diem.sad.progetto_sad;


public interface ShapeInterface {
    void setX(double X);
    void setY(double Y);
    double getX();
    double getY();
    void setStrokeColor(Color color);
    Color getStrokeColor();
    void setLength(double length);
    double getLength();
    String toString();
}
