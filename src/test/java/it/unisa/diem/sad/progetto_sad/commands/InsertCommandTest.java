package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertCommandTest {

    private Pane workspace;
    private ShapeInterface testShape;
    private Command insertCommand;

    @BeforeEach
    void setUp() {
        workspace = new Pane();
        testShape = new RectangleShape(30, 45, Color.RED, 20, 20, Color.RED);
        insertCommand = new InsertCommand(workspace, testShape);
    }

    @Test
    void testExecuteInsertsShapeIntoWorkspace() {
        //Il workspace non dovrebbe contenere la forma prima dell'esecuzione
        assertFalse(workspace.getChildren().contains(testShape));

        insertCommand.execute();

        //Il workspace dovrebbe contenere la forma dopo l'esecuzione
        assertTrue(workspace.getChildren().contains(testShape));
    }

    @Test
    void testUndoRemovesShapeFromWorkspace() {
        //Il workspace dovrebbe contenere la forma dopo l'esecuzione
        insertCommand.execute();
        assertTrue(workspace.getChildren().contains(testShape));

        insertCommand.undo();

        //Il workspace non dovrebbe contenere la forma dopo l'annullamento
        assertFalse(workspace.getChildren().contains(testShape));
    }
}