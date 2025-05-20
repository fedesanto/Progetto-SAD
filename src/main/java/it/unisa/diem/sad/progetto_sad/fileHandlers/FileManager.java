package it.unisa.diem.sad.progetto_sad.fileHandlers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import it.unisa.diem.sad.progetto_sad.shapes.EllipseShape;
import it.unisa.diem.sad.progetto_sad.shapes.RectangleShape;
import it.unisa.diem.sad.progetto_sad.shapes.SegmentShape;
import it.unisa.diem.sad.progetto_sad.shapes.ShapeInterface;
import javafx.scene.paint.Color;

/**
 * La classe FileManager gestisce il salvataggio e il caricamento delle forme geometriche
 * da/verso file di testo in formato CSV.
 *
 */
public class FileManager {

    /**
     * Salva una lista di forme su file, una per riga in formato testuale.
     * @param shapes lista delle forme da salvare
     * @param path percorso del file
     * @return true se il salvataggio è avvenuto con successo, false altrimenti
     */
    public static boolean saveFile(List<ShapeInterface> shapes, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            for (ShapeInterface shape : shapes) {
                writer.write(shape.toString() + "\n");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carica le forme da un file CSV precedentemente salvato.
     * @param path percorso del file
     * @return lista delle forme ricostruite
     */
    public static List<ShapeInterface> loadFile(String path) {
        List<ShapeInterface> shapes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                if (tokens[0].equals("Shape2D") && tokens[1].equals("RectangleShape")) {
                    double x = Double.parseDouble(tokens[2]);
                    double y = Double.parseDouble(tokens[3]);
                    Color stroke = Color.web(tokens[4]);
                    Color fill = Color.web(tokens[5]);
                    double width = Double.parseDouble(tokens[6]);
                    double height = Double.parseDouble(tokens[7]);

                    shapes.add(new RectangleShape(x, y, stroke, width, height, fill));
                }

                else if (tokens[0].equals("Shape2D") && tokens[1].equals("EllipseShape")) {
                    double x = Double.parseDouble(tokens[2]);
                    double y = Double.parseDouble(tokens[3]);
                    Color stroke = Color.web(tokens[4]);
                    Color fill = Color.web(tokens[5]);
                    double width = Double.parseDouble(tokens[6]);
                    double height = Double.parseDouble(tokens[7]);

                    shapes.add(new EllipseShape(x, y, stroke, width, height, fill));
                }

                else if (tokens[0].equals("Shape1D") && tokens[1].equals("SegmentShape")) {
                    double x = Double.parseDouble(tokens[2]);
                    double y = Double.parseDouble(tokens[3]);
                    Color stroke = Color.web(tokens[4]);
                    double length = Double.parseDouble(tokens[5]);

                    shapes.add(new SegmentShape(x, y, stroke, length));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Errore durante il caricamento: " + e.getMessage());
        }

        return shapes;
    }
}

