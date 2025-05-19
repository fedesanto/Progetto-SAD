package it.unisa.diem.sad.progetto_sad;

import javafx.scene.paint.Color;

public abstract class RectangleShape implements Shape2D {
    private double X;
    private double Y;
    private Color stroke;
    private double width;
    private double heigth;
    private Color fill;

    public RectangleShape(double X, double Y, Color stroke, double width, double heigth, Color fill) {
        this.X = X;
        this.Y = Y;
        this.stroke = stroke;
        this.width = width;
        this.heigth = heigth;
        this.fill = fill;
    }

    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return Y;
    }

    public void setY(double Y) {
        this.Y = Y;
    }

    public Color getStroke() {
        return stroke;
    }

    public void setStroke(Color stroke) {
        this.stroke = stroke;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeigth() {
        return heigth;
    }

    public void setHeigth(double heigth) {
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
        return "RectangleShape;" +
                X + ";" +
                Y + ";" +
                stroke.toString() + ";" +
                width + ";" +
                heigth + ";" +
                fill.toString();
    }

}
