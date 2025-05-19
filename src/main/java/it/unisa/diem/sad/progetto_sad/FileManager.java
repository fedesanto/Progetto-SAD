package it.unisa.diem.sad.progetto_sad;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {

    public static boolean saveFile(List<ShapeInterface> shapes, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            for (ShapeInterface shape : shapes) {
                writer.write(shape.toString() + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

