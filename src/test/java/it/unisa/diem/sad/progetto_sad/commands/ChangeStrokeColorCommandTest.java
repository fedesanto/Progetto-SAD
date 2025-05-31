package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeStrokeColorCommandTest {

    private ShapeInterface testShape;
    private Command changeStrokeColorCommand;
    private Color originalStrokeColor;
    private Color newStrokeColor;

    @BeforeEach
    void setUp() {
        originalStrokeColor = Color.BLACK;
        newStrokeColor = Color.BLUE;
        testShape = new RectangleShape(30, 45, originalStrokeColor, 20, 20, Color.RED);
        changeStrokeColorCommand = new ChangeStrokeColorCommand(testShape, newStrokeColor);
    }

    @Test
    void testExecuteChangesStrokeColor() {
        // La forma dovrebbe avere il colore del bordo originale prima dell'esecuzione
        assertEquals(originalStrokeColor, testShape.getStrokeColor());

        changeStrokeColorCommand.execute();

        // La forma dovrebbe avere il nuovo colore del bordo dopo l'esecuzione
        assertEquals(newStrokeColor, testShape.getStrokeColor());
    }

    @Test
    void testUndoRestoresPreviousStrokeColor() {
        // La forma dovrebbe avere il nuovo colore del bordo dopo l'esecuzione
        changeStrokeColorCommand.execute();
        assertEquals(newStrokeColor, testShape.getStrokeColor());

        changeStrokeColorCommand.undo();

        // La forma dovrebbe avere il colore del bordo precedente dopo l'annullamento
        assertEquals(originalStrokeColor, testShape.getStrokeColor());
    }
}