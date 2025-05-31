package it.unisa.diem.sad.progetto_sad.shapes;

import it.unisa.diem.sad.progetto_sad.visitors.VisitorShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SegmentShape extends Line implements Shape1D {

    private final double D1_STROKE_WIDTH = 4;
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

        setShapeX(X);
        setShapeY(Y);
        setShapeLength(length);
        setStrokeColor(stroke);
        setStrokeWidth(D1_STROKE_WIDTH);
    }

    /**
     * Getter coordinata X centro
     *
     * @return coordinata X centro
     */
    public double getShapeX() {
        return (getStartX() + getEndX())/2;     //Calcolo la coordinata X del centro della linea
    }

    /**
     * Setter coordinata X centro
     *
     * @param X coordinata X del centro della linea
     */
    public void setShapeX(double X) {
        double cy = getShapeY();

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
    public double getShapeY() {
        return (getStartY() + getEndY())/2;     //Calcolo la coordinata Y del centro della linea
    }

    /**
     * Setter coordinata Y centro
     *
     * @param Y coordinata Y del centro della linea
     */
    public void setShapeY(double Y) {
        double cx = getShapeX();

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
    public double getShapeLength() {
        double dx = getEndX() - getStartX();
        double dy = getEndY() - getStartY();

        return Math.hypot(dx, dy);
    }

    /**
     * Setter lunghezza della linea
     *
     * @param length lunghezza della linea
     */
    public void setShapeLength(double length) {
        double cx = getShapeX();
        double cy = getShapeY();

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
     * Restituisce una rappresentazione testuale del segmento in formato CSV
     *
     * @return la stringa CSV descrittiva del segmento
     */
    public String toString() {
        return "Shape1D;SegmentShape;" + getShapeX() + ";" + getShapeY() + ";" + getStrokeColor() + ";" + getShapeLength();
    }

    /**
     * Applica un oggetto Visitor a questa forma.
     *
     * @param visitor il visitor da applicare a questa forma
     */
    public void accept(VisitorShape visitor) {
        visitor.visit(this);
    }

    /**
     * Crea e restituisce una copia dell'oggetto SegmentShape corrente.
     * La copia è una nuova istanza con gli stessi valori per posizione, dimensioni,
     * colore del contorno e colore di riempimento dell'istanza originale.
     * *
     * @return una nuova istanza di SegmentShape con gli stessi attributi dell'oggetto corrente
     */
    public ShapeInterface clone() {
        return new SegmentShape(
                getShapeX(),
                getShapeY(),
                getStrokeColor(),
                getShapeLength()
        );
    }

    /**
     * Porta il segmento in primo piano nel contesto grafico.
     */
    public void shapeToFront(){
        toFront();
    }

    /**
     * Porta il segmento in fondo nel contesto grafico.
     */
    public void shapeToBack(){
        toBack();
    }
}