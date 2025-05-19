package it.unisa.diem.sad.progetto_sad.factories;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import it.unisa.diem.sad.progetto_sad.shapes.Shape2D;
import it.unisa.diem.sad.progetto_sad.shapes.EllipseShape;
import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import javafx.scene.paint.Color;

/**
 * Factory per la creazione di forme bidimensionali (2D).
 * Implementa l'interfaccia ShapeCreator per generare oggetti ShapeInterface.
 */
public class Shape2DCreator implements ShapeCreator {
    private final double D2_WIDTH = 30;
    private final double D2_HEIGHT = 20;

    /**
     * Tipo della forma 2D da creare.
     */
    public Shape2D.TYPE_2D type;

    /**
     * Colore del bordo della forma.
     */
    private Color stroke;

    /**
     * Colore di riempimento della forma.
     */
    private Color fill;

    /**
     * Costruttore
     *
     * @param type   Tipo di forma 2D da generare.
     * @param stroke Colore del bordo della forma.
     * @param fill   Colore di riempimento della forma.
     */
    public Shape2DCreator(Shape2D.TYPE_2D type, Color stroke, Color fill) {
        this.type = type;
        this.stroke = stroke;
        this.fill = fill;
    }

    /**
     * Crea una forma 2D in base al tipo specificato.
     *
     * @return un oggetto ShapeInterface che rappresenta la forma creata,
     * oppure null se il tipo non è supportato.
     */
    @Override
    public ShapeInterface createShape(){
        switch (this.type) {
            case RECTANGLE:
                return new RectangleShape(0, 0, this.stroke, this.D2_WIDTH, this.D2_HEIGHT, this.fill);
            //case ELLIPSE:
            //    return new EllipseShape(0, 0, this.stroke, this.D2_WIDTH, this.D2_HEIGHT, this.fill);
            default:
                return null;
        }
    }

    /**
     * Imposta il colore del bordo della forma.
     *
     * @param color Nuovo colore per il bordo.
     */
    public void setStrokeColor(Color color) {
        this.stroke = color;
    }

    /**
     * Imposta il colore di riempimento della forma.
     *
     * @param color Nuovo colore per il riempimento.
     */
    public void setFillColor(Color color) {
        this.fill = color;
    }

}
