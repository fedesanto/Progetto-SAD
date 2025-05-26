package it.unisa.diem.sad.progetto_sad.shapes;

import it.unisa.diem.sad.progetto_sad.visitors.VisitorShape;
import javafx.scene.paint.Color;

/**
 * Interfaccia che definisce i metodi di base per tutte le forme geometriche
 * gestite nell'applicazione.
 */
public interface ShapeInterface {
    /**
     * Imposta la coordinata X del centro della forma.
     *
     * @param X la nuova ascissa del punto di riferimento della forma
     */
    void setShapeX(double X);

    /**
     * Imposta la coordinata Y del centro della forma.
     *
     * @param Y la nuova ordinata del punto di riferimento della forma
     */
    void setShapeY(double Y);

    /**
     * Restituisce la coordinata X del centro della forma.
     *
     * @return la coordinata X del punto di riferimento della forma
     */
    double getShapeX();

    /**
     * Restituisce la coordinata Y del centro della forma.
     *
     * @return la coordinata Y del punto di riferimento della forma
     */
    double getShapeY();

    /**
     * Imposta il colore del contorno (stroke) della forma.
     *
     * @param color oggetto Color che rappresenta il colore del bordo
     */
    void setStrokeColor(Color color);

    /**
     * Restituisce il colore del contorno (stroke) della forma.
     *
     * @return un oggetto Color che rappresenta il colore del bordo
     */
    Color getStrokeColor();

    /**
     * Restituisce una rappresentazione csv della forma
     *
     * @return una stringa che descrive la forma
     */
    String toString();

    /**
     * Accetta un oggetto Visitor che implementa l'interfaccia VisitorShape
     * e consente di eseguire un'operazione su questa forma senza modificarne la struttura.
     *
     * @param visitor il visitor da applicare alla forma
     */
    void accept(VisitorShape visitor);

    /**
     * Restituisce una copia dell'oggetto che implementa questa interfaccia.
     * La copia dovrebbe contenere gli stessi attributi dell'istanza originale,
     * ma essere indipendente da essa (modificare la copia non deve influire sull'originale).
     *
     * @return una nuova istanza di ShapeInterface con gli stessi valori dell'oggetto corrente
     */
    ShapeInterface clone();


}


