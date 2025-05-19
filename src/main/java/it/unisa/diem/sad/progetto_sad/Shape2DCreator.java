package it.unisa.diem.sad.progetto_sad;

public class Shape2DCreator {
    public abstract class Shape2DCreator implements ShapeCreator {
        private String type;
        private Paint stroke;
        private double width;
        private double height;
        private Paint fill;

        public Shape2DCreator(String type, Paint stroke, Paint fill, double width, double height) {
            this.type = type;
            this.stroke = stroke;
            this.fill = fill;
            this.width = width;
            this.height = height;
        }

        public void setStrokeColor(Paint color) {
            this.stroke = color;
        }


        public void setWidth(double width) {
            this.width = width;
        }

        public void setHeight(double height) {
            this.height = height;
        }
        public void setFillColor(Paint color) {
            this.fill = color;
        }

        @Override
        public abstract ShapeInterface createShape();


    }
