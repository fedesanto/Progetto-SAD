package it.unisa.diem.sad.progetto_sad.shapes;

/**
 * Interfaccia che estende ShapeInterface per le forme ad una dimensione
 */
public interface Shape1D extends ShapeInterface{

    /**
     * Tipologie di forme ad una dimensione supportate
     */
    enum TYPE_1D {
        LINE        //Segmento di linea
    }

    /**
     * Imposta la lunghezza della forma
     *
     * @param length la nuova lunghezza della forma
     */
    void setShapeLength(double length);

    /**
     * Restituisce la lunghezza della forma
     *
     * @return la lunghezza corrente della forma
     */
    double getShapeLength();
}
