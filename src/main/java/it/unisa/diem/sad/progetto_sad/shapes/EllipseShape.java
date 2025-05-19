package it.unisa.diem.sad.progetto_sad.shapes;
import javafx.scene.paint.Color;

public abstract class EllipseShape implements Shape2D {
    private double X;
    private double Y;
    private Color stroke;
    private double width;
    private double heigth;
    private Color fill;

    public EllipseShape(double X, double Y, Color stroke, double width, double heigth, Color fill) {
        this.X = X;
        this.Y = Y;
        this.stroke = stroke;
        this.width = width;
        this.heigth = heigth;
        this.fill = fill;
    }

    public double getCenterX() {
        return X;
    }

    public void setCenterX(double X) {
        this.X = X;
    }

    public double getCenterY() {
        return Y;
    }

    public void setCenterY(double Y) {
        this.Y = Y;
    }

    public Color getStroke() {
        return stroke;
    }

    public void setStroke(Color stroke) {
        this.stroke = stroke;
    }

    public double getShapeWidth() {
        return width;
    }

    public void setShapeWidth(double width) {
        this.width = width;
    }

    public double getShapeHeight() {
        return heigth;
    }

    public void setShapeHeight(double heigth) {
        this.heigth = heigth;
    }

    public Color getFill() {
        return fill;
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }
    @Override
    public String toString() {
        return "EllipseShape;" +
                X + ";" +
                Y + ";" +
                stroke.toString() + ";" +
                width + ";" +
                heigth + ";" +
                fill.toString();
    }

}
