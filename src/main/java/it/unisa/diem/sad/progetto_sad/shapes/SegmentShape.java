package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SegmentShape extends Line implements Shape1D {

    /**
     * Costruttore
     *
     * @param X coordinata X del centro della linea
     * @param Y coordinata Y del centro della linea
     * @param stroke colore della linea
     * @param length lunghezza della linea
     */
    public SegmentShape(double X, double Y, Color stroke, double length) {
        super(0., 0., 0., 0.);
        setX(X);
        setY(Y);
        setStrokeColor(stroke);
        setLength(length);
    }

    /**
     * Getter coordinata X centro
     *
     * @return coordinata X centro
     */
    public double getX() {
        return (getStartX() + getEndX())/2;     //Calcolo la coordinata X del centro della linea
    }

    /**
     * Setter coordinata X centro
     *
     * @param X coordinata X del centro della linea
     */
    public void setX(double X) {
        double cy = getY();

        double dx = getEndX() - getStartX();     // vettore direzione
        double dy = getEndY() - getStartY();
        double halfLen = Math.hypot(dx, dy) / 2; // lunghezza metà segmento
        double theta = Math.atan2(dy, dx);       // angolo corrente

        double cos = Math.cos(theta);  // ricalcolo le coordinate degli estremi
        double sin = Math.sin(theta);
        setStartX(X - cos * halfLen);
        setStartY(cy - sin * halfLen);
        setEndX  (X + cos * halfLen);
        setEndY  (cy + sin * halfLen);
    }

    /**
     * Getter coordinata Y centro
     *
     * @return coordinata Y centro
     */
    public double getY() {
        return (getStartY() + getEndY())/2;     //Calcolo la coordinata Y del centro della linea
    }

    /**
     * Setter coordinata Y centro
     *
     * @param Y coordinata Y del centro della linea
     */
    public void setY(double Y) {
        double cx = getX();

        double dx = getEndX() - getStartX();      // vettore direzione
        double dy = getEndY() - getStartY();
        double halfLen = Math.hypot(dx, dy) / 2; // lunghezza metà segmento
        double theta = Math.atan2(dy, dx);       // angolo corrente

        double cos = Math.cos(theta);  // ricalcolo le coordinate degli estremi
        double sin = Math.sin(theta);
        setStartX(cx - cos * halfLen);
        setStartY(Y  - sin * halfLen);
        setEndX  (cx + cos * halfLen);
        setEndY  (Y  + sin * halfLen);
    }

    /**
     * Getter colore della linea
     *
     * @return colore della linea
     */
    public Color getStrokeColor() {
        return (Color) getStroke();
    }

    /**
     * Setter colore della linea
     *
     * @param stroke colore della linea
     */
    public void setStrokeColor(Color stroke) {
        setStroke(stroke);
    }

    /**
     * Getter lunghezza della linea
     *
     * @return lunghezza della linea
     */
    public double getLength() {
        double dx = getEndX() - getStartX();
        double dy = getEndY() - getStartY();

        return Math.hypot(dx, dy) / 2;
    }

    /**
     * Setter lunghezza della linea
     *
     * @param length lunghezza della linea
     */
    public void setLength(double length) {
        double cx = getX();
        double cy = getY();

        double dx = getEndX() - getStartX();    // vettore direzione
        double dy = getEndY() - getStartY();
        double theta = Math.atan2(dy, dx);      // angolo corrente

        double half = length / 2.0; // metà della nuova lunghezza

        double cos = Math.cos(theta);   // ricalcolo le coordinate degli estremi
        double sin = Math.sin(theta);
        setStartX(cx - cos * half);
        setStartY(cy - sin * half);
        setEndX(  cx + cos * half);
        setEndY(  cy + sin * half);
    }


    /**
     * implementazione toString della classe
     *
     * @return descrizione della linea
     */
    public String toString() {
        return "Shape1D;SegmentShape;" + getX() + ";" + getY() + ";" + getStrokeColor() + ";" + getLength();
    }
}