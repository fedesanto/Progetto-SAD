package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleShapeTest {

    private RectangleShape rect;

    @BeforeEach
    void setUp() {
        // Initialize a default rectangle before each test
        rect = new RectangleShape(0, 0, Color.BLACK, 40, 20, Color.WHITE);
    }

    @Test
    void testGetCenterXAndGetCenterY() {
        assertEquals(0, rect.getCenterX());
        assertEquals(0, rect.getCenterY());

        rect.setX(10);
        rect.setY(15);
        rect.setShapeWidth(50);
        rect.setShapeHeight(30);
        assertEquals(10 + 50 / 2.0, rect.getCenterX());
        assertEquals(15 + 30 / 2.0, rect.getCenterY());
    }

    @Test
    void testSetCenterX() {
        rect.setCenterX(100);
        assertEquals(100, rect.getCenterX());
        assertEquals(100 - rect.getShapeWidth() / 2.0, rect.getX());
    }

    @Test
    void testSetCenterY() {
        rect.setCenterY(80);
        assertEquals(80, rect.getCenterY());
        assertEquals(80 - rect.getShapeHeight() / 2.0, rect.getY());
    }

    @Test
    void testSetShapeWidthMaintainsCenter() {
        double originalCenterX = rect.getCenterX();
        rect.setShapeWidth(100);
        assertEquals(100, rect.getShapeWidth());
        assertEquals(originalCenterX, rect.getCenterX());
    }

    @Test
    void testSetShapeHeightMaintainsCenter() {
        double originalCenterY = rect.getCenterY();
        rect.setShapeHeight(200);
        assertEquals(200, rect.getShapeHeight());
        assertEquals(originalCenterY, rect.getCenterY());
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
        assertEquals(Double.toString(rect.getCenterX()), parts[2]);
        assertEquals(Double.toString(rect.getCenterY()), parts[3]);
        assertEquals(rect.getStrokeColor().toString(), parts[4]);
        assertEquals(rect.getFillColor().toString(), parts[5]);
        assertEquals(Double.toString(rect.getHeight()), parts[6]);
        assertEquals(rect.getFillColor().toString(), parts[7]);
    }
}