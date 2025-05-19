package it.unisa.diem.sad.progetto_sad.factories;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;

public  class Shape2DCreator  implements ShapeCreator {
    public TYPE_2D type;
    private Color stroke;
    private final double D2_WIDTH = 30.0;
    private final double D2_HEIGHT = 20.0;
    private Color fill;

        public Shape2DCreator(TYPE_2D type, Color stroke, Color fill) {
            this.type = type;
            this.stroke = stroke;
            this.fill = fill;
        }

        public void setStrokeColor(Color color) {
            this.stroke = color;
        }

        public void setFillColor(Color color) {
            this.fill = color;
        }

        @Override
        public abstract ShapeInterface createShape();
    }
