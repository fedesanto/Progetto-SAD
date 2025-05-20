package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SegmentShapeTest {

    private SegmentShape segment;

    @BeforeEach
    void setUp() {
        SegmentShape segment = new SegmentShape(50, 50, Color.BLUE, 100);
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
        segment.setShapeX(100);
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
        assertEquals("Shape1D;SegmentShape;50.0;50.0;0xff0000ff;100.0", segment.toString());
    }
}