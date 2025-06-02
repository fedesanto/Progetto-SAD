package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BringFrontCommandTest {

    private Pane workspace;
    private ShapeInterface testShape;
    private BringFrontCommand bringFrontCommand;

    @BeforeEach
    void setUp() {
        workspace = new Pane();
        testShape = new RectangleShape(40, 40, Color.BLUE, 10, 10, Color.BLACK);

        // Aggiungiamo più forme per simulare la profondità
        ShapeInterface otherShape1 = new RectangleShape(20, 20, Color.GREEN, 5, 5, Color.BLACK);
        ShapeInterface otherShape2 = new RectangleShape(60, 60, Color.YELLOW, 15, 15, Color.BLACK);

        workspace.getChildren().addAll(
                otherShape1.toJavaFXShape(),
                testShape.toJavaFXShape(),
                otherShape2.toJavaFXShape()
        );

        bringFrontCommand = new BringFrontCommand(workspace, testShape);
    }

    @Test
    void testExecuteBringsShapeToFront() {
        int initialIndex = workspace.getChildren().indexOf(testShape.toJavaFXShape());
        int lastIndex = workspace.getChildren().size() - 1;
        assertNotEquals(lastIndex, initialIndex, "La forma non è inizialmente in primo piano");

        bringFrontCommand.execute();

        int newIndex = workspace.getChildren().indexOf(testShape.toJavaFXShape());
        assertEquals(lastIndex, newIndex, "La forma dovrebbe essere in primo piano dopo execute()");
    }

    @Test
    void testUndoRestoresShapeToOriginalPosition() {
        int originalIndex = workspace.getChildren().indexOf(testShape.toJavaFXShape());

        bringFrontCommand.execute();
        bringFrontCommand.undo();

        int restoredIndex = workspace.getChildren().indexOf(testShape.toJavaFXShape());
        assertEquals(originalIndex, restoredIndex, "La forma dovrebbe tornare alla posizione originale dopo undo()");
    }
}