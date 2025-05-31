package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * Comando per l'inserimento di una forma su spazio di lavoro
 */
public class InsertCommand implements Command{
    private final Pane workspace;
    private final ShapeInterface shape;


    /**
     * Crea il comando
     *
     * @param workspace    spazio di lavoro in cui inserire la forma
     * @param shape        forma da inserire
     */
    public InsertCommand(Pane workspace, ShapeInterface shape){
        this.workspace = workspace;
        this.shape = shape;
    }

    /**
     * Metodo di esecuzione del comando.
     * Inserisce la nuova forma nello spazio di lavoro
     */
    public void execute() {
        workspace.getChildren().add((Shape) shape);
    }

    /**
     * Metodo di annullamento del comando.
     * Elimina dal workspace la forma precedentemente inserita
     */
    public void undo() {
        workspace.getChildren().remove((Shape) shape);
    }
}
