package it.unisa.diem.sad.progetto_sad.fileHandlers;

import it.unisa.diem.sad.progetto_sad.factories.Shape1DCreator;
import it.unisa.diem.sad.progetto_sad.factories.Shape2DCreator;
import it.unisa.diem.sad.progetto_sad.shapes.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe FileManager fornisce metodi statici per salvare e caricare una lista di oggetti ShapeInterface
 * da/verso un file. Le forme sono serializzate come stringhe testuali seguendo un formato delimitato da punto e virgola.
 *
 * Formato atteso per una forma nel file:
 * - SegmentShape: Shape1D;SegmentShape;X;Y;Colore;Lunghezza
 * - RectangleShape: Shape2D;RectangleShape;X;Y;StrokeColor;Width;Height;FillColor
 * - EllipseShape: Shape2D;EllipseShape;X;Y;StrokeColor;Width;Height;FillColor
 */
public class FileManager {

    /**
     * Salva su file una lista di forme (ShapeInterface), usando il metodo toString() di ciascuna forma.
     *
     * @param shapes lista di forme da salvare
     * @param file   percorso del file su cui salvare
     * @return true se il salvataggio ha avuto successo, false in caso di errore
     */
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

    /**
     * Carica una lista di forme da un file. Ogni riga del file deve contenere una rappresentazione testuale
     * di una forma secondo il formato specificato nella classe.
     *
     * @param file percorso del file da cui caricare
     * @return lista di forme caricate, o null in caso di errore
     */
    public static List<ShapeInterface> loadFile(String file) {
        List<ShapeInterface> shapes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] campi = linea.split(";");

                // Parsing di una forma 1D
                if(campi[0].equals("Shape1D")) {
                    //SegmentShape
                    if(campi[1].equals("SegmentShape")) {
                        double centerX = Double.parseDouble(campi[2]);
                        double centerY = Double.parseDouble(campi[3]);
                        Color stroke = Color.web(campi[4]);
                        double length = Double.parseDouble(campi[5]);

                        Shape1DCreator creator = new Shape1DCreator(Shape1D.TYPE_1D.LINE, stroke);
                        SegmentShape shape = (SegmentShape) creator.createShape();

                        shape.setShapeX(centerX);
                        shape.setShapeY(centerY);
                        shape.setShapeLength(length);

                        shapes.add(shape);
                    }
                }

                // Parsing di forme 2D
                if(campi[0].equals("Shape2D")){
                    //RectangleShape
                    if(campi[1].equals("RectangleShape")) {
                        double centerX = Double.parseDouble(campi[2]);
                        double centerY = Double.parseDouble(campi[3]);
                        Color stroke = Color.web(campi[4]);
                        double width = Double.parseDouble(campi[5]);
                        double height = Double.parseDouble(campi[6]);
                        Color fill = Color.web(campi[7]);

                        Shape2DCreator creator = new Shape2DCreator(Shape2D.TYPE_2D.RECTANGLE, stroke, fill);
                        RectangleShape shape = (RectangleShape) creator.createShape();

                        shape.setShapeX(centerX);
                        shape.setShapeY(centerY);
                        shape.setShapeWidth(width);
                        shape.setShapeHeight(height);

                        shapes.add(shape);
                    }

                    //EllipseShape
                    if(campi[1].equals("EllipseShape")) {
                        double centerX = Double.parseDouble(campi[2]);
                        double centerY = Double.parseDouble(campi[3]);
                        Color stroke = Color.web(campi[4]);
                        double width = Double.parseDouble(campi[5]);
                        double height = Double.parseDouble(campi[6]);
                        Color fill = Color.web(campi[7]);

                        Shape2DCreator creator = new Shape2DCreator(Shape2D.TYPE_2D.ELLIPSE, stroke, fill);
                        EllipseShape shape = (EllipseShape) creator.createShape();

                        shape.setShapeX(centerX);
                        shape.setShapeY(centerY);
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

