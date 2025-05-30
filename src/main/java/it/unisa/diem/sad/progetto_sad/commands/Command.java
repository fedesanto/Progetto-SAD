package it.unisa.diem.sad.progetto_sad.commands;

/**
 * Interfaccia comune ai comandi dell'applicazione
 */
public interface Command {

    /**
     *  Metodo di esecuzione del comando
     */
    void execute();

    /**
     * Metodo di esecuzione del comando
     */
    void undo();
}
