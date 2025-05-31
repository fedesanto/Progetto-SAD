package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * Command per portare una forma in fondo all'interno di un Pane.
 */
public class BringBackCommand implements Command{
    private final ShapeInterface shape;
    private final Pane workspace;
    private int oldIndex;

    /**
     * Costruisce un comando per portare una forma in fondo nel workspace.
     *
     * @param shape     la forma da portare in fondo
     * @param workspace il pane che contiene la forma
     */
    public BringBackCommand(ShapeInterface shape, Pane workspace){
        this.shape = shape;
        this.workspace = workspace;
    }

    /**
     * Esegue il comando, portando la forma in fondo.
     */
    public void execute(){
        oldIndex = workspace.getChildren().indexOf((Shape) shape);
        shape.ShapetoBack();
    }

    /**
     * Annulla l'effetto dell'esecuzione del comando,
     * ripristinando la forma alla sua posizione originale nel pane.
     */
    public void undo(){
        Shape castedShape = (Shape) shape;
        workspace.getChildren().remove(castedShape);
        workspace.getChildren().add(oldIndex, castedShape);
    }
}
