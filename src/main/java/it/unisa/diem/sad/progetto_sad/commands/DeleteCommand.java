package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class DeleteCommand implements Command{
    private final Pane workspace;
    private final ShapeInterface shape;

    public DeleteCommand(Pane workspace, ShapeInterface shape) {
        this.workspace = workspace;
        this.shape = shape;
    }

    public void execute() {
        this.workspace.getChildren().remove((Shape) this.shape);
    }

    public void undo() {
        this.workspace.getChildren().add((Shape) this.shape);
    }
}
