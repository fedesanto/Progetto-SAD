package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.Shape1D;
import it.unisa.diem.sad.progetto_sad.shapes.Shape2D;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import it.unisa.diem.sad.progetto_sad.visitors.VisitorResize;


public class ResizeCommand implements Command {
    private final ShapeInterface shape;
    private double dim1;
    private double dim2;

    public ResizeCommand(ShapeInterface shape) {
        this.shape = shape;
    }

    public void execute() {
        shape.accept(new VisitorResize());

        if (shape instanceof Shape1D)
            dim1 = ((Shape1D) shape).getShapeLength();
        else{
            dim1 = ((Shape2D) shape).getShapeWidth();
            dim2 = ((Shape2D) shape).getShapeHeight();
        }

    }

    public void undo() {
        if (shape instanceof Shape1D)
            ((Shape1D) shape).setShapeLength(dim1);
        else{
            ((Shape2D) shape).setShapeWidth(dim1);
            ((Shape2D) shape).setShapeHeight(dim2);
        }
    }
}
