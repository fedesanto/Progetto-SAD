package it.unisa.diem.sad.progetto_sad.factories;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import it.unisa.diem.sad.progetto_sad.shapes.Shape1D;
import it.unisa.diem.sad.progetto_sad.shapes.SegmentShape;
import javafx.scene.paint.Color;

/**
 * Factory per la creazione di forme monodimensionali (1D), come linee.
 * Implementa l'interfaccia ShapeCreator per permettere la generazione di oggetti ShapeInterface.
 */
public class Shape1DCreator  implements ShapeCreator {
    private final double D1_LENGTH = 80;

    /**
     * Tipo della forma 1D da creare.
     */
    public Shape1D.TYPE_1D type;

    /**
     * Colore del bordo della forma.
     */
    private Color stroke;

    /**
     * Costruttore.
     *
     * @param type   Tipo di forma 1D da generare.
     * @param stroke Colore del bordo della forma.
     */
    public Shape1DCreator(Shape1D.TYPE_1D type, Color stroke) {
        this.type = type;
        this.stroke = stroke;
    }

    /**
     * Crea una forma 1D basata sul tipo specificato nel costruttore.
     *
     * @return un oggetto ShapeInterface che rappresenta la forma creata,
     * oppure null se il tipo non è supportato.
     */
    @Override
    public ShapeInterface createShape(){
        if (this.type == Shape1D.TYPE_1D.LINE) {
            return new SegmentShape(0, 0, this.stroke, D1_LENGTH);
        } else {
            return null;
        }
    }

    /**
     * Imposta il colore del bordo per la forma.
     *
     * @param color Nuovo colore da applicare al bordo.
     */
    public void setStrokeColor(Color color) {
        this.stroke = color;
    }

}



