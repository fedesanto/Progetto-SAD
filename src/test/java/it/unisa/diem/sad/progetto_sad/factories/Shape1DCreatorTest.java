package it.unisa.diem.sad.progetto_sad.factories;

import it.unisa.diem.sad.progetto_sad.shapes.SegmentShape;
import it.unisa.diem.sad.progetto_sad.shapes.Shape1D;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Shape1DCreatorTest {

    private Shape1DCreator creator;

    @BeforeEach
    void setUp() {
        creator = new Shape1DCreator(Shape1D.TYPE_1D.LINE, Color.RED);
    }

    @Test
    void testCreateShape_Segment() {
        ShapeInterface shape = creator.createShape();
        assertNotNull(shape, "La forma non dovrebbe essere nulla.");
        assertInstanceOf(SegmentShape.class, shape, "La forma dovrebbe essere un SegmentShape.");

        SegmentShape segment = (SegmentShape) shape;
        assertEquals(Color.RED, segment.getStroke(), "Il colore del bordo non corrisponde.");
        assertEquals(80, segment.getShapeLength(), "La lunghezza del segmento dovrebbe essere 80.");
    }

    @Test
    void testSetStrokeColor() {
        creator.setStrokeColor(Color.BLUE);
        SegmentShape segment = (SegmentShape) creator.createShape();

        assertEquals(Color.BLUE, segment.getStroke(), "Il colore del bordo non è stato aggiornato correttamente.");
    }

    @Test
    void testCreateShape_NullType() {
        Shape1DCreator nullCreator = new Shape1DCreator(null, Color.GREEN);
        ShapeInterface shape = nullCreator.createShape();

        assertNull(shape, "La forma dovrebbe essere nulla se il tipo non è supportato.");
    }

}