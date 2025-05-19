package it.unisa.diem.sad.progetto_sad;

public abstract class Shape2DCreator implements ShapeCreator {
    public TYPE_2D type;
    private Paint stroke;
    private final double D2_WIDTH = 30.0;
    private final double D2_HEIGHT = 20.0;
    private Paint fill;

        public Shape2DCreator(TYPE_2D type, Paint stroke, Paint fill) {
            this.type = type;
            this.stroke = stroke;
            this.fill = fill;
        }

        public void setStrokeColor(Paint color) {
            this.stroke = color;
        }

        public void setFillColor(Paint color) {
            this.fill = color;
        }

        @Override
        public abstract ShapeInterface createShape();
    }
