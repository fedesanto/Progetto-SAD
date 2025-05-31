package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BringBackCommandTest {

    private Pane workspace;
    private ShapeInterface testShape;
    private BringBackCommand bringBackCommand;

    @BeforeEach
    void setUp() {
        workspace = new Pane();
        testShape = new RectangleShape(40, 40, Color.BLUE, 10, 10, Color.BLACK);

        // Aggiungiamo altre forme per simulare una pila
        ShapeInterface otherShape1 = new RectangleShape(20, 20, Color.GREEN, 5, 5, Color.BLACK);
        ShapeInterface otherShape2 = new RectangleShape(60, 60, Color.YELLOW, 15, 15, Color.BLACK);

        workspace.getChildren().addAll(
                (Shape) otherShape1,
                (Shape) testShape,
                (Shape) otherShape2
        );

        bringBackCommand = new BringBackCommand(testShape, workspace);
    }

    @Test
    void testExecuteBringsShapeToBack() {
        int initialIndex = workspace.getChildren().indexOf((Shape) testShape);
        assertNotEquals(0, initialIndex, "La forma non è inizialmente in fondo");

        bringBackCommand.execute();

        int newIndex = workspace.getChildren().indexOf((Shape) testShape);
        assertEquals(0, newIndex, "La forma dovrebbe essere portata in fondo dopo execute()");
    }

    @Test
    void testUndoRestoresShapeToOriginalPosition() {
        int originalIndex = workspace.getChildren().indexOf((Shape) testShape);

        bringBackCommand.execute();
        bringBackCommand.undo();

        int restoredIndex = workspace.getChildren().indexOf((Shape) testShape);
        assertEquals(originalIndex, restoredIndex, "La forma dovrebbe tornare alla posizione originale dopo undo()");
    }
}
