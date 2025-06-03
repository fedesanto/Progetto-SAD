package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;

/**
 * Comando per la registrazione del trascinamento di una forma
 */
public class DragCommand implements Command {
    private final ShapeInterface shape;
    private final double startX;
    private final double startY;


    /**
     * Crea il comando
     *
     * @param shape forma da trascinare
     */
    public DragCommand(ShapeInterface shape, double startX, double startY) {
        this.shape = shape;
        this.startX = startX;
        this.startY = startY;
    }

    /**
     * Metodo di esecuzione del comando.
     * Non effettua nessuna operazione
     */
    public void execute() {

    }

    /**
     * Metodo di annullamento del comando.
     * Riporta la forma nella posizione registrata quando il comando è stato eseguito
     */
    public void undo() {
        shape.setShapeX(startX);
        shape.setShapeY(startY);
    }
}