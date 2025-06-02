package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DragCommandTest {

    private ShapeInterface testShape;
    private Command dragCommand;

    @BeforeEach
    void setUp() {
        testShape = new RectangleShape(30, 45, Color.RED, 20, 20, Color.RED);
        dragCommand = new DragCommand(testShape, 30, 45);
    }

    @Test
    void testExecuteStoresInitialPosition() {
        dragCommand.execute();

        // Sposta la forma dopo execute per simulare un drag
        testShape.setShapeX(200);
        testShape.setShapeY(50);

        // L'undo dovrebbe riportarla alla posizione iniziale (100, 200)
        dragCommand.undo();

        // La posizione della forma dovrebbbe essere ripristinata
        assertEquals(30, testShape.getShapeX());
        assertEquals(45, testShape.getShapeY());
    }
}