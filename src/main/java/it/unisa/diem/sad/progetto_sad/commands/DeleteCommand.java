package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * Comando che consente di eliminare una forma dallo spazio di lavoro.
 */
public class DeleteCommand implements Command{
    private final Pane workspace;
    private final ShapeInterface shape;


    /**
     * Crea un nuovo comando di eliminazione per una forma specifica.
     *
     * @param workspace il pannello da cui rimuovere la forma
     * @param shape     la forma da eliminare
     */
    public DeleteCommand(Pane workspace, ShapeInterface shape) {
        this.workspace = workspace;
        this.shape = shape;
    }

    /**
     * Esegue il comando rimuovendo la forma dallo spazio di lavoro.
     */
    public void execute() {
        this.workspace.getChildren().remove((Shape) this.shape);
    }

    /**
     * Annulla il comando ripristinando la forma nello spazio di lavoro.
     */
    public void undo() {
        this.workspace.getChildren().add((Shape) this.shape);
    }
}
