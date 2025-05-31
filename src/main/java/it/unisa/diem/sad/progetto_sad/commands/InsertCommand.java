package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.ViewController;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class InsertCommand implements Command{
    private final Pane workspace;
    private final ShapeInterface shape;


    public InsertCommand(Pane workspace, ShapeInterface shape){
        this.workspace = workspace;
        this.shape = shape;
    }

    /**
     * Metodo di esecuzione del comando.
     * Inserisce la nuova forma nello spazio di lavoro
     */
    @Override
    public void execute() {
        workspace.getChildren().add((Shape) shape);
    }

    /**
     * Metodo di annullamento del comando.
     * Elimina dal workspace la forma precedentemente inserita
     */
    @Override
    public void undo() {
        workspace.getChildren().remove((Shape) shape);
    }
}
