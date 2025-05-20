package it.unisa.diem.sad.progetto_sad.shapes;
import javafx.scene.paint.Color;

import javafx.scene.shape.Ellipse;

public class EllipseShape extends Ellipse implements Shape2D {

    /**
     * Crea una nuova ellisse
     *
     * @param X         la coordinata X del centro dell'elllisse
     * @param Y         la coordinata Y del centro dell'ellisse
     * @param stroke    il colore del bordo (stroke) dell'ellisse
     * @param width     la larghezza dell'ellisse (lunghezza diametro X)
     * @param height    l’altezza dell'ellisse (lunghezza diametro Y)
     * @param fill      il colore di riempimento (fill) dell'ellisse
     */
    public EllipseShape(double X, double Y, Color stroke, double width, double height, Color fill) {
        super(0., 0., 0., 0.);

        setShapeX(X);
        setShapeY(Y);
        setStrokeColor(stroke);
        setShapeWidth(width);
        setShapeHeight(height);
        setFillColor(fill);
    }

    /**
     * Restituisce la coordinata X del centro dell'ellisse
     *
     * @return la coordinata X del punto centrale
     */
    public double getShapeX() {
        return getCenterX();
    }

    /**
     * Restituisce la coordinata Y del centro dell'ellisse
     *
     * @return la coordinata Y del punto centrale
     */
    public double getShapeY() {
        return getCenterY();
    }

    /**
     * Imposta la coordintata X del centro
     *
     * @param X la nuova coordinata X del centro
     */
    public void setShapeX(double X) {
        setCenterX(X);
    }

    /**
     * Imposta la coordintata Y del centro
     *
     * @param Y la nuova coordinata Y del centro
     */
    public void setShapeY(double Y) {
        setCenterY(Y);
    }

    /**
     * Restituisce la larghezza corrente dell'ellisse.
     *
     * @return la larghezza
     */
    public double getShapeWidth() {
        return getRadiusX() * 2;
    }

    /**
     * Restituisce l’altezza corrente dell'ellisse.
     *
     * @return l’altezza
     */
    public double getShapeHeight() {
        return getRadiusY() * 2;
    }

    /**
     * Imposta la larghezza dell'elllisse mantenendo fisso il centro orizzontale.
     *
     * @param width la nuova larghezza
     */
    public void setShapeWidth(double width) {
        double cx = getShapeX();
        setRadiusX(width/2);
        setShapeX(cx);
    }

    /**
     * Imposta l'altezza dell'elllisse mantenendo fisso il centro orizzontale.
     *
     * @param height la nuova altezza
     */
    public void setShapeHeight(double height) {
        double cy = getShapeY();
        setRadiusY(height/2);
        setShapeY(cy);
    }

    /**
     * Restituisce il colore del bordo (stroke) dell'ellisse.
     *
     * @return il colore di contorno
     */
    public Color getStrokeColor() {
        return (Color) getStroke();
    }

    /**
     * Imposta il colore del bordo (stroke) dell'ellisse.
     *
     * @param stroke il nuovo colore di contorno
     */
    public void setStrokeColor(Color stroke) {
        setStroke(stroke);
    }

    /**
     * Restituisce il colore di riempimento (fill) dell'ellisse.
     *
     * @return il colore di riempimento
     */
    public Color getFillColor() {
        return (Color) getFill();
    }

    /**
     * Imposta il colore di riempimento (fill) dell'ellisse.
     *
     * @param fill il nuovo colore di riempimento
     */
    public void setFillColor(Color fill) {
        setFill(fill);
    }

    /**
     * Restituisce una rappresentazione testuale dell'ellisse in formato CSV
     *
     * @return la stringa CSV descrittiva del rettangolo
     */
    public String toString() {
        return "Shape2D;EllipseShape;" + getShapeX() + ";" + getShapeY() + ";" + getStrokeColor() + ";" + getShapeWidth() + ";" + getShapeHeight() + ";" + getFillColor();    }

}
