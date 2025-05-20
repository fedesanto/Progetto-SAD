package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleShape extends Rectangle implements Shape2D {

    /**
     * Crea un nuovo rettangolo
     *
     * @param X         la coordinata X del centro del rettangolo
     * @param Y         la coordinata Y del centro del rettangolo
     * @param stroke    il colore del bordo (stroke) del rettangolo
     * @param width     la larghezza del rettangolo
     * @param height    l’altezza del rettangolo
     * @param fill      il colore di riempimento (fill) del rettangolo
     */
    public RectangleShape(double X, double Y, Color stroke, double width, double height, Color fill) {
        super(0., 0., 0., 0.);

        setCenterX(X);
        setCenterY(Y);
        setStrokeColor(stroke);
        setShapeWidth(width);
        setShapeHeight(height);
        setFillColor(fill);
    }

    /**
     * Restituisce la coordinata X del centro del rettangolo.
     *
     * @return la coordinata X del punto centrale
     */
    public double getCenterX() {
        return getX() + getWidth() / 2.0;
    }

    /**
     * Restituisce la coordinata Y del centro del rettangolo.
     *
     * @return la coordinata Y del punto centrale
     */
    public double getCenterY() {
        return getY() + getHeight() / 2.0;
    }

    /**
     * Sposta il rettangolo in modo che il centro abbia ascissa {@code X},
     * mantenendo invariata l’altezza e la rotazione.
     *
     * @param X la nuova coordinata X del centro
     */
    public void setCenterX(double X) {
        setX(X - getWidth() / 2.0);
    }

    /**
     * Sposta il rettangolo in modo che il centro abbia ordinata {@code cy},
     * mantenendo invariata la larghezza e la rotazione.
     *
     * @param cy la nuova coordinata Y del centro
     */
    public void setCenterY(double cy) {
        setY(cy - getHeight() / 2.0);
    }

    /**
     * Restituisce la larghezza corrente del rettangolo.
     *
     * @return la larghezza
     */
    public double getShapeWidth() {
        return getWidth();
    }

    /**
     * Restituisce l’altezza corrente del rettangolo.
     *
     * @return l’altezza
     */
    public double getShapeHeight() {
        return getHeight();
    }

    /**
     * Imposta la larghezza del rettangolo mantenendo fisso il centro orizzontale.
     *
     * @param width la nuova larghezza
     */
    public void setShapeWidth(double width) {
        double cx = getCenterX();
        setWidth(width);
        setCenterX(cx);
    }

    /**
     * Imposta l’altezza del rettangolo mantenendo fisso il centro verticale.
     *
     * @param height la nuova altezza
     */
    public void setShapeHeight(double height) {
        double cy = getCenterY();
        setHeight(height);
        setCenterY(cy);
    }

    /**
     * Restituisce il colore del bordo (stroke) del rettangolo.
     *
     * @return il colore di contorno
     */
    public Color getStrokeColor() {
        return (Color) getStroke();
    }

    /**
     * Imposta il colore del bordo (stroke) del rettangolo.
     *
     * @param stroke il nuovo colore di contorno
     */
    public void setStrokeColor(Color stroke) {
        setStroke(stroke);
    }

    /**
     * Restituisce il colore di riempimento (fill) del rettangolo.
     *
     * @return il colore di riempimento
     */
    public Color getFillColor() {
        return (Color) getFill();
    }

    /**
     * Imposta il colore di riempimento (fill) del rettangolo.
     *
     * @param fill il nuovo colore di riempimento
     */
    public void setFillColor(Color fill) {
        setFill(fill);
    }

    /**
     * Restituisce una rappresentazione testuale del rettangolo in formato CSV
     *
     * @return la stringa CSV descrittiva del rettangolo
     */
    public String toString() {
        return "Shape2D;RectangleShape;" + getCenterX() + ";" + getCenterY() + ";" + getStrokeColor() + ";" + getFillColor() + ";" + getHeight() + ";" + getFillColor();
    }

}
