package it.unisa.diem.sad.progetto_sad.shapes;

public interface Shape1D extends ShapeInterface{

    enum TYPE_1D {
        LINE
    }

    void setShapeLength(double length);
    double getShapeLength();
}
