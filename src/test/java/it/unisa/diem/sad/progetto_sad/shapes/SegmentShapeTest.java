package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.Segment;

import static org.junit.jupiter.api.Assertions.*;
class SegmentShapeTest {

    private SegmentShape segment;

    @BeforeEach
    void setUp() {
        segment = new SegmentShape(50, 50, Color.BLUE, 100);
    }

    @Test
    void getShapeX() {
        assertEquals(50, segment.getShapeX());
    }

    @Test
    void setShapeX() {
        segment.setShapeX(100);
        assertEquals(100, segment.getShapeX());
    }

    @Test
    void getShapeY() {
        assertEquals(50, segment.getShapeY());
    }

    @Test
    void setShapeY() {
        segment.setShapeY(100);
        assertEquals(100, segment.getShapeY());
    }

    @Test
    void getShapeLength() {
        assertEquals(100, segment.getShapeLength());
    }

    @Test
    void setShapeLength1() {
        segment.setShapeLength(300);
        assertEquals(300, segment.getShapeLength());
    }

    @Test
    void setShapeLength2() {
        segment.setShapeLength(50);
        assertEquals(50, segment.getShapeLength());
    }

    @Test
    void testToString() {
        String s = segment.toString();

        String[] parts = s.split(";");
        assertEquals("Shape1D", parts[0]);
        assertEquals("SegmentShape", parts[1]);
        assertEquals(Double.toString(segment.getShapeX()), parts[2]);
        assertEquals(Double.toString(segment.getShapeY()), parts[3]);
        assertEquals(segment.getStrokeColor().toString(), parts[4]);
        assertEquals(Double.toString(segment.getShapeLength()), parts[5]);
    }


    @Test
    void testClone() {
        // Clona l'oggetto
        ShapeInterface cloned = segment.clone();

        // Verifica che il clone sia un'altra istanza
        assertNotSame(segment, cloned);
        assertInstanceOf(SegmentShape.class, cloned);

        // Cast per confronto
        SegmentShape copy = (SegmentShape) cloned;

        // Verifica che gli attributi siano identici
        assertEquals(segment.getShapeX(), copy.getShapeX());
        assertEquals(segment.getShapeY(), copy.getShapeY());
        assertEquals(segment.getStrokeColor(), copy.getStrokeColor());
        assertEquals(segment.getShapeLength(), copy.getShapeLength());
    }


    @Test
    public void testToJavaFXShape() {
        Line result = segment.toJavaFXShape();

        // Il metodo toJavaFXShape dovrebbe restituire this
        assertSame(segment, result);

        // Il valore restituito dovrebbe essere un'istanza di Ellipse
        assertInstanceOf(Line.class, result);
    }
}