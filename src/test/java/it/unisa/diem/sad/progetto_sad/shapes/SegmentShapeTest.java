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
    void getX() {
        assertEquals(50, segment.getX());
    }

    @Test
    void setX() {
        segment.setX(100);
        assertEquals(100, segment.getX());
    }

    @Test
    void getY() {
        assertEquals(50, segment.getY());
    }

    @Test
    void setY() {
        segment.setX(100);
        assertEquals(100, segment.getY());
    }

    @Test
    void getLength() {
        assertEquals(100, segment.getLength());
    }

    @Test
    void setLength1() {
        segment.setLength(300);
        assertEquals(300, segment.getLength());
    }

    @Test
    void setLength2() {
        segment.setLength(50);
        assertEquals(50, segment.getLength());
    }

    @Test
    void testToString() {
        assertEquals("Shape1D;SegmentShape;50.0;50.0;0xff0000ff;100.0", segment.toString());
    }
}