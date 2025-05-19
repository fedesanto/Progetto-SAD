module it.unisa.diem.sad.progetto_sad {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unisa.diem.sad.progetto_sad to javafx.fxml;
    exports it.unisa.diem.sad.progetto_sad;
    exports it.unisa.diem.sad.progetto_sad.shapes;
    opens it.unisa.diem.sad.progetto_sad.shapes to javafx.fxml;
    exports it.unisa.diem.sad.progetto_sad.factories;
    opens it.unisa.diem.sad.progetto_sad.factories to javafx.fxml;
    exports it.unisa.diem.sad.progetto_sad.fileHandlers;
    opens it.unisa.diem.sad.progetto_sad.fileHandlers to javafx.fxml;
}