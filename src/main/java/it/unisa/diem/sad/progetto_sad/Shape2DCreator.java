package it.unisa.diem.sad.progetto_sad;

public abstract class Shape2DCreator implements ShapeCreator {
        private String type;
        private Paint stroke;
        private Paint fill;

        public Shape2DCreator(String type, Paint stroke, Paint fill) {
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
