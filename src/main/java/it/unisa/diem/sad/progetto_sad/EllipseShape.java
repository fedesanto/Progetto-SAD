package it.unisa.diem.sad.progetto_sad;
import javafx.scene.paint.Paint;

public abstract class EllipseShape implements Shape2D {
    private double X;
    private double Y;
    private Paint stroke;
    private double width;
    private double heigth;
    private Paint fill;

    public EllipseShape(double X, double Y, Paint stroke, double width, double heigth, Paint fill) {
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

    public Paint getStroke() {
        return stroke;
    }

    public void setStroke(Paint stroke) {
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

    public Paint getFill() {
        return fill;
    }

    public void setFill(Paint fill) {
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
