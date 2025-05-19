package it.unisa.diem.sad.progetto_sad;

import javafx.scene.paint.Color;

public abstract class SegmentShape implements Shape1D {
    private double X;
    private double Y;
    private Color stroke;
    private double length;

    public SegmentShape(double X, double Y, Color stroke, double length) {
        this.X = X;
        this.Y = Y;
        this.stroke = stroke;
        this.length = length;
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }


    @Override
    public String toString() {
        return "SegmentShape;" +
                X + ";" +
                Y + ";" +
                stroke.toString() + ";" +
                length;
    }
}