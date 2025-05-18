module it.unisa.diem.sad.progetto_sad {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unisa.diem.sad.progetto_sad to javafx.fxml;
    exports it.unisa.diem.sad.progetto_sad;
}