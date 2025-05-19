package it.unisa.diem.sad.progetto_sad.factories;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;

/**
 * Interfaccia che rappresenta un creatore di forme geometriche.
 * Fornisce i metodi necessari per creare una forma e definire il suo colore di contorno.
 */
public interface ShapeCreator {
    /**
     * Crea una nuova forma geometrica secondo i parametri specificati
     * nell'implementazione concreta.
     *
     * @return un oggetto che implementa ShapeInterface, rappresentante la forma creata.
     */
    ShapeInterface createShape();

    /**
     * Imposta il colore del bordo della forma.
     *
     * @param color colore da applicare al bordo.
     */
    void setStrokeColor(Color color);
}
