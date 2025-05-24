package it.unisa.diem.sad.progetto_sad.visitors;

import it.unisa.diem.sad.progetto_sad.shapes.*;

/**
 * Interfaccia del Visitor che definisce le operazioni da eseguire sulle
 * forme geometriche dell'applicazione.
 *
 * Implementa il pattern Visitor, permettendo di applicare operazioni
 * specifiche ai diversi tipi di forme Shape1D e  Shape2D
 */
public interface VisitorShape {

    /**
     * Visita una forma unidimensionale ed esegue un'operazione specifica su di essa.
     *
     * @param shape1D la forma 1D da visitare
     */
    void visit(Shape1D shape1D);

    /**
     * Visita una forma bidimensionale ed esegue un'operazione specifica su di essa.
     *
     * @param shape2D la forma 2D da visitare
     */
    void visit(Shape2D shape2D);
}
