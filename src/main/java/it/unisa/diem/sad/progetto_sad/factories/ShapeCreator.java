package it.unisa.diem.sad.progetto_sad.factories;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;

public interface ShapeCreator {
    ShapeInterface createShape();
    void setStrokeColor(Color color);

}
