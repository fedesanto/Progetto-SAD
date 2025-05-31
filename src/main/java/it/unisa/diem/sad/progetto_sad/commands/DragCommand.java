package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;

/**
 * Comando per la registrazione del trascinamento di una forma
 */
public class DragCommand implements Command {
    private final ShapeInterface shape;
    private double startX, startY;


    /**
     * Crea il comando
     *
     * @param shape forma da trascinare
     */
    public DragCommand(ShapeInterface shape) {
        this.shape = shape;
    }

    /**
     * Metodo di esecuzione del comando.
     * Registra la posizione attuale della forma
     */
    public void execute() {
        this.startX = shape.getShapeX();
        this.startY = shape.getShapeY();
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