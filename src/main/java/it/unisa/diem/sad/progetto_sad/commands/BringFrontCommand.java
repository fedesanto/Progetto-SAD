package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * Command per portare una forma in primo piano all'interno di un Pane.
 */
public class BringFrontCommand implements Command{

    private final Pane workspace;
    private final ShapeInterface shape;
    private int oldIndex;

    /**
     * Costruisce un comando per portare una forma in primo piano nel workspace.
     *
     * @param shape     la forma da portare in primo piano
     * @param workspace il pane che contiene la forma
     */
    public BringFrontCommand(Pane workspace, ShapeInterface shape){
        this.workspace = workspace;
        this.shape = shape;
    }

    /**
     * Esegue il comando, portando la forma in primo piano.
     */
    public void execute(){
        oldIndex = workspace.getChildren().indexOf((Shape) shape);
        shape.shapeToFront();
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
