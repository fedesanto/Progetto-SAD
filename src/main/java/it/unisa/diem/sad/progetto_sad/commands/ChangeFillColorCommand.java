package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.Shape2D;
import javafx.scene.paint.Color;

/**
 * Comando che consente di cambiare il colore di riempimento di una forma bidimensionale.
 */
public class ChangeFillColorCommand implements Command{
    private final Shape2D shape;
    private final Color fillColor;
    private Color prevFillColor;

    /**
     * Crea un nuovo comando per modificare il colore di riempimento di una forma.
     *
     * @param shape     la forma a cui applicare il nuovo colore
     * @param fillColor il nuovo colore di riempimento da impostare
     */
    public ChangeFillColorCommand(Shape2D shape, Color fillColor) {
        this.shape = shape;
        this.fillColor = fillColor;
    }

    /**
     * Esegue il comando impostando il nuovo colore di riempimento sulla forma.
     */
    public void execute() {
        prevFillColor = shape.getFillColor();
        shape.setFillColor(fillColor);
    }

    /**
     * Annulla il comando ripristinando il colore di riempimento precedente.
     */
    public void undo() {
        shape.setFillColor(prevFillColor);
    }
}
