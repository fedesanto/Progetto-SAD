package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;

/**
 * Interfaccia che estende ShapeInterface per le forme a due dimensioni
 */
public interface Shape2D extends ShapeInterface{

    /**
     * Tipologie di forme bidimensionali supportate
     */
    enum TYPE_2D {
        RECTANGLE,      //Rettangolo
        ELLIPSE         //Ellisse
    }

    /**
     * Imposta la larghezza della forma a due dimensioni.
     * Mantiene fisso il centro della forma
     *
     * @param width la nuova larghezza
     */
    void setShapeWidth(double width);

    /**
     * Restituisce la larghezza corrente della forma bidimensionale
     *
     * @return la larghezza della forma
     */
    double getShapeWidth();

    /**
     * Imposta l'altezza della forma a due dimensioni.
     * Mantiene fisso il centro della forma
     *
     * @param height la nuova altezza
     */
    void setShapeHeight(double height);

    /**
     * Restituisce l'altezza corrente della forma a due dimensioni
     *
     * @return l'altezza della forma
     */
    double getShapeHeight();

    /**
     * Imposta il colore di riempimento (fill) della forma
     *
     * @param color oggetto Color che rappresenta il colore di riempimento
     */
    void setFillColor(Color color);

    /**
     * Restituisce il colore di riempimento (fill) della forma
     *
     * @return un oggetto Color che rappresenta il colore di riempimento
     */
    Color getFillColor();
}
