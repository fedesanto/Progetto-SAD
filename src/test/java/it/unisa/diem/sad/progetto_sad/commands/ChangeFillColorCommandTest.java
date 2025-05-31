package it.unisa.diem.sad.progetto_sad.commands;

import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import it.unisa.diem.sad.progetto_sad.shapes.Shape2D;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeFillColorCommandTest {

    private Shape2D testShape;
    private Command changeFillColorCommand;
    private Color originalFillColor;
    private Color newFillColor;

    @BeforeEach
    void setUp() {
        originalFillColor = Color.RED;
        newFillColor = Color.BLUE;
        testShape = new RectangleShape(30, 45, Color.BLACK, 20, 20, originalFillColor);
        changeFillColorCommand = new ChangeFillColorCommand(testShape, newFillColor);
    }

    @Test
    void testExecuteChangesFillColor() {
        // La forma dovrebbe avere il colore di riempimento originale prima dell'esecuzione
        assertEquals(originalFillColor, testShape.getFillColor());

        changeFillColorCommand.execute();

        // La forma dovrebbe avere il nuovo colore di riempimento dopo l'esecuzione
        assertEquals(newFillColor, testShape.getFillColor());
    }

    @Test
    void testUndoRestoresPreviousFillColor() {
        // La forma dovrebbe avere il nuovo colore di riempimento dopo l'esecuzione
        changeFillColorCommand.execute();
        assertEquals(newFillColor, testShape.getFillColor());

        changeFillColorCommand.undo();

        // La forma dovrebbe avere il colore di riempimento precedente dopo l'annullamento
        assertEquals(originalFillColor, testShape.getFillColor());
    }

}