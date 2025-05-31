package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteCommandTest {

    private Pane workspace;
    private ShapeInterface testShape;
    private Command deleteCommand;

    @BeforeEach
    void setUp() {
        workspace = new Pane();
        testShape = new RectangleShape(30, 45, Color.RED, 20, 20, Color.RED);
        workspace.getChildren().add((Shape) testShape);
        deleteCommand = new DeleteCommand(workspace, testShape);
    }

    @Test
    void testExecuteRemovesShapeFromWorkspace() {
        //La forma dovrebbe essere presente prima dell'esecuzione
        assertTrue(workspace.getChildren().contains(testShape));

        deleteCommand.execute();

        //La forma dovrebbe essere rimossa dopo l'esecuzione
        assertFalse(workspace.getChildren().contains(testShape));
    }

    @Test
    void testUndoRestoresShapeToWorkspace() {
        //La forma dovrebbe essere stata rimossa dopo l'esecuzione
        deleteCommand.execute();
        assertFalse(workspace.getChildren().contains(testShape));

        deleteCommand.undo();

        //La forma dovrebbe essere ripristinata dopo l'annullamento
        assertTrue(workspace.getChildren().contains(testShape));
    }
}