package it.unisa.diem.sad.progetto_sad.commands;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Classe adapter per l'implementazione di una history di comandi
 */
public class CommandHistory {
    private final Stack<Command> history;
    private final ReadOnlyBooleanWrapper emptyProperty;     //Proprietà osservabile per verificare se lo stack è vuoto

    /**
     * Crea la history dei comandi
     */
    public CommandHistory(){
        history = new Stack<>();
        emptyProperty = new ReadOnlyBooleanWrapper(true);
    }

    /**
     * Inserimento di un nuovo comando nella history
     *
     * @param command il comando da inserire
     */
    public void push(Command command){
        history.push(command);
        updateState();
    }

    /**
     * Estrae l'ultimo command inserito e lo restituisce.
     * Se la history è vuota, lancia un'eccezzione
     *
     * @return il comando estratto
     * @throws EmptyStackException se la history è vuota
     */
    public Command pop(){
        Command command = history.pop();
        updateState();

        return command;
    }

    /**
     * Verifica se la history è vuota.
     *
     * @return true se la history è vuota
     */
    public boolean isEmpty(){
        return history.isEmpty();
    }

    /**
     * Restistuisce la proprietà osservabile che consente di verificare se la history è vuota
     *
     * @return proprietà booleana osservabile
     */
    public ReadOnlyBooleanProperty emptyProperty() {
        return emptyProperty.getReadOnlyProperty();
    }


    /**
     * Metodo per aggiornare la proprietà osservabile della history
     */
    private void updateState() {
        emptyProperty.set(history.isEmpty());
    }
}
