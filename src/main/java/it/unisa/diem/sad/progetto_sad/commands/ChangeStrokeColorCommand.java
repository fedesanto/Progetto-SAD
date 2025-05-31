package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;

/**
 * Comando che consente di modificare il colore del bordo di una forma.
 */
public class ChangeStrokeColorCommand implements Command{
    private final ShapeInterface shape;
    private final Color strokeColor;
    private Color prevStrokeColor;


    /**
     * Crea un nuovo comando per modificare il colore del bordo di una forma.
     *
     * @param shape       la forma a cui applicare il nuovo colore del bordo
     * @param strokeColor il nuovo colore del bordo da impostare
     */
    public ChangeStrokeColorCommand(ShapeInterface shape, Color strokeColor) {
        this.shape = shape;
        this.strokeColor = strokeColor;
    }

    /**
     * Esegue il comando impostando il nuovo colore del bordo sulla forma.
     */
    public void execute() {
        prevStrokeColor = shape.getStrokeColor();
        shape.setStrokeColor(strokeColor);
    }

    /**
     * Annulla il comando ripristinando il colore del bordo precedente.
     */
    public void undo() {
        shape.setStrokeColor(prevStrokeColor);
    }
}
