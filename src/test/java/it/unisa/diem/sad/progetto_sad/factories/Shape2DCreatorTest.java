package it.unisa.diem.sad.progetto_sad.factories;

import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Shape2DCreatorTest {

    private Shape2DCreator rectangleCreator;
    private Shape2DCreator ellipseCreator;

    @BeforeEach
    void setUp() {
        rectangleCreator = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, Color.BLACK, Color.YELLOW);
        ellipseCreator = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, Color.BLUE, Color.GREEN);
    }

    @Test
    void testCreateShape_Rectangle() {
        ShapeInterface shape = rectangleCreator.createShape();
        assertNotNull(shape, "La forma non dovrebbe essere nulla.");
        assertInstanceOf(RectangleShape.class, shape, "La forma dovrebbe essere un RectangleShape.");

        RectangleShape rectangle = (RectangleShape) shape;
        assertEquals(Color.BLACK, rectangle.getStroke(), "Colore del bordo errato.");
        assertEquals(Color.YELLOW, rectangle.getFill(), "Colore di riempimento errato.");
        assertEquals(30, rectangle.getShapeWidth(), "Larghezza errata.");
        assertEquals(20, rectangle.getShapeHeight(), "Altezza errata.");
    }

    @Test
    void testCreateShape_Ellipse() {
        ShapeInterface shape = ellipseCreator.createShape();
        assertNotNull(shape, "La forma non dovrebbe essere nulla.");
        assertInstanceOf(EllipseShape.class, shape, "La forma dovrebbe essere un EllipseShape.");

        EllipseShape ellipse = (EllipseShape) shape;
        assertEquals(Color.BLUE, ellipse.getStroke(), "Colore del bordo errato.");
        assertEquals(Color.GREEN, ellipse.getFill(), "Colore di riempimento errato.");
        assertEquals(30, ellipse.getShapeWidth(), "Larghezza errata.");
        assertEquals(20, ellipse.getShapeHeight(), "Altezza errata.");
    }

    @Test
    void testSetStrokeColor() {
        rectangleCreator.setStrokeColor(Color.PURPLE);
        RectangleShape rectangle = (RectangleShape) rectangleCreator.createShape();

        assertEquals(Color.PURPLE, rectangle.getStroke(), "Il colore del bordo non è stato aggiornato correttamente.");
    }

    @Test
    void testSetFillColor() {
        ellipseCreator.setFillColor(Color.ORANGE);
        EllipseShape ellipse = (EllipseShape) ellipseCreator.createShape();

        assertEquals(Color.ORANGE, ellipse.getFill(), "Il colore di riempimento non è stato aggiornato correttamente.");
    }

    @Test
    void testCreateShape_InvalidType() {
        Shape2DCreator invalidCreator = new Shape2DCreator(null, Color.RED, Color.BLUE);
        ShapeInterface shape = invalidCreator.createShape();

        assertNull(shape, "La forma dovrebbe essere nulla se il tipo non è supportato.");
    }

}
