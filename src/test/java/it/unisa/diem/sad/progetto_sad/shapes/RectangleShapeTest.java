package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleShapeTest {

    private RectangleShape rect;

    @BeforeEach
    void setUp() {
        rect = new RectangleShape(0, 0, Color.BLACK, 40, 20, Color.WHITE);
    }

    @Test
    void testGetShapeXAndGetShapeY() {
        assertEquals(0, rect.getShapeX());
        assertEquals(0, rect.getShapeY());

        rect.setShapeX(10);
        rect.setShapeY(15);
        rect.setShapeWidth(50);
        rect.setShapeHeight(30);
        assertEquals(10, rect.getShapeX());
        assertEquals(15, rect.getShapeY());
    }

    @Test
    void testSetShapeX() {
        rect.setShapeX(100);
        assertEquals(100, rect.getShapeX());
    }

    @Test
    void testSetShapeY() {
        rect.setShapeY(80);
        assertEquals(80, rect.getShapeY());
    }

    @Test
    void testSetShapeWidthMaintainsCenter() {
        double originalCenterX = rect.getShapeX();
        rect.setShapeWidth(100);
        assertEquals(100, rect.getShapeWidth());
        assertEquals(originalCenterX, rect.getShapeX());
    }

    @Test
    void testSetShapeHeightMaintainsCenter() {
        double originalCenterY = rect.getShapeY();
        rect.setShapeHeight(200);
        assertEquals(200, rect.getShapeHeight());
        assertEquals(originalCenterY, rect.getShapeY());
    }

    @Test
    void testStrokeColor() {
        assertEquals(Color.BLACK, rect.getStrokeColor());
        rect.setStrokeColor(Color.GREEN);
        assertEquals(Color.GREEN, rect.getStrokeColor());
    }

    @Test
    void testFillColor() {
        assertEquals(Color.WHITE, rect.getFillColor());
        rect.setFillColor(Color.YELLOW);
        assertEquals(Color.YELLOW, rect.getFillColor());
    }

    @Test
    void testToStringFormat() {
        String s = rect.toString();

        String[] parts = s.split(";");
        assertEquals("Shape2D", parts[0]);
        assertEquals("RectangleShape", parts[1]);
        assertEquals(Double.toString(rect.getShapeX()), parts[2]);
        assertEquals(Double.toString(rect.getShapeY()), parts[3]);
        assertEquals(rect.getStrokeColor().toString(), parts[4]);
        assertEquals(Double.toString(rect.getShapeWidth()), parts[5]);
        assertEquals(Double.toString(rect.getShapeHeight()), parts[6]);
        assertEquals(rect.getFillColor().toString(), parts[7]);
    }
}