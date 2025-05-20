package it.unisa.diem.sad.progetto_sad.shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EllipseShapeTest {

    private EllipseShape ellipse;

    @BeforeEach
    void setUp() {
        ellipse = new EllipseShape(0, 0, Color.BLACK, 40, 20, Color.WHITE);
    }

    @Test
    void testGetShapeXAndGetShapeY() {
        assertEquals(0, ellipse.getShapeX());
        assertEquals(0, ellipse.getShapeY());

        ellipse.setShapeX(10);
        ellipse.setShapeY(15);
        ellipse.setShapeWidth(50);
        ellipse.setShapeHeight(30);
        assertEquals(10, ellipse.getShapeX());
        assertEquals(15, ellipse.getShapeY());
    }

    @Test
    void testSetShapeX() {
        ellipse.setShapeX(100);
        assertEquals(100, ellipse.getShapeX());
    }

    @Test
    void testSetShapeY() {
        ellipse.setShapeY(80);
        assertEquals(80, ellipse.getShapeY());
    }

    @Test
    void testSetShapeWidthMaintainsCenter() {
        double originalCenterX = ellipse.getShapeX();
        ellipse.setShapeWidth(100);
        assertEquals(100, ellipse.getShapeWidth());
        assertEquals(originalCenterX, ellipse.getShapeX());
    }

    @Test
    void testSetShapeHeightMaintainsCenter() {
        double originalCenterY = ellipse.getShapeY();
        ellipse.setShapeHeight(200);
        assertEquals(200, ellipse.getShapeHeight());
        assertEquals(originalCenterY, ellipse.getShapeY());
    }

    @Test
    void testStrokeColor() {
        assertEquals(Color.BLACK, ellipse.getStrokeColor());
        ellipse.setStrokeColor(Color.GREEN);
        assertEquals(Color.GREEN, ellipse.getStrokeColor());
    }

    @Test
    void testFillColor() {
        assertEquals(Color.WHITE, ellipse.getFillColor());
        ellipse.setFillColor(Color.YELLOW);
        assertEquals(Color.YELLOW, ellipse.getFillColor());
    }

    @Test
    void testToStringFormat() {
        String s = ellipse.toString();

        String[] parts = s.split(";");
        assertEquals("Shape2D", parts[0]);
        assertEquals("EllipseShape", parts[1]);
        assertEquals(Double.toString(ellipse.getShapeX()), parts[2]);
        assertEquals(Double.toString(ellipse.getShapeY()), parts[3]);
        assertEquals(ellipse.getStrokeColor().toString(), parts[4]);
        assertEquals(Double.toString(ellipse.getShapeWidth()), parts[5]);
        assertEquals(Double.toString(ellipse.getShapeHeight()), parts[6]);
        assertEquals(ellipse.getFillColor().toString(), parts[7]);
    }
}