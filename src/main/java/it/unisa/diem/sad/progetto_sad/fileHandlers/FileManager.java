package it.unisa.diem.sad.progetto_sad.fileHandlers;

import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static boolean saveFile(List<ShapeInterface> shapes, String file) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            for (ShapeInterface shape : shapes) {
                writer.println(shape.toString());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static List<ShapeInterface> loadFile(String file) {
        List<ShapeInterface> shapes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] campi = linea.split(";");

                if(campi[0].equals("Shape1D")) {
                    if(campi[1].equals("SegmentShape")) {
                        double centerX = Double.parseDouble(campi[2]);
                        double centerY = Double.parseDouble(campi[3]);
                        Color stroke = Color.web(campi[4]);
                        double length = Double.parseDouble(campi[5]);

                        Shape1DCreator creator = new Shape1DCreator(Shape1D.TYPE_1D.LINE, stroke);
                        SegmentShape shape = (SegmentShape) creator.createShape();

                        shape.setCenterX(centerX);
                        shape.setCenterY(centerY);
                        shape.setShapeLength(length);

                        shapes.add(shape);
                    }
                }

                if(campi[0].equals("Shape2D")){
                    if(campi[1].equals("RectangleShape")) {
                        double centerX = Double.parseDouble(campi[2]);
                        double centerY = Double.parseDouble(campi[3]);
                        Color stroke = Color.web(campi[4]);
                        double width = Double.parseDouble(campi[5]);
                        double height = Double.parseDouble(campi[6]);
                        Color fill = Color.web(campi[7]);

                        Shape2DCreator creator = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, stroke, fill);
                        RectangleShape shape = (RectangleShape) creator.createShape();

                        shape.setCenterX(centerX);
                        shape.setCenterY(centerY);
                        shape.setShapeWidth(width);
                        shape.setShapeHeight(height);

                        shapes.add(shape);
                    }

                    if(campi[1].equals("Ellipse")) {
                        double centerX = Double.parseDouble(campi[2]);
                        double centerY = Double.parseDouble(campi[3]);
                        Color stroke = Color.web(campi[4]);
                        double width = Double.parseDouble(campi[5]);
                        double height = Double.parseDouble(campi[6]);
                        Color fill = Color.web(campi[7]);

                        Shape2DCreator creator = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, stroke, fill);
                        EllipseShape shape = (EllipseShape) creator.createShape();

                        shape.setCenterX(centerX);
                        shape.setCenterY(centerY);
                        shape.setShapeWidth(width);
                        shape.setShapeHeight(height);

                        shapes.add(shape);
                    }
                }
            }
        } catch (IOException e) {
            return null;
        }

        return shapes;

    }

}

