package it.unisa.diem.sad.progetto_sad.commands;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Classe adapter per l'implementazione di una history di comandi
 */
public class CommandHistory {
    private final Stack<Command> history;

    /**
     * Crea la history dei comandi
     */
    public CommandHistory(){
        history = new Stack<Command>();
    }

    /**
     * Inserimento di un nuovo comando nella history
     *
     * @param command il comando da inserire
     */
    public void push(Command command){
        history.push(command);
    }

    /**
     * Estrae l'ultimo command inserito e lo restituisce.
     * Se la history è vuota, lancia un'eccezzione
     *
     * @return il comando estratto
     * @throws EmptyStackException se la history è vuota
     */
    public Command pop(){
        return history.pop();
    }

    /**
     * Verifica se la history è vuota.
     *
     * @return true se la history è vuota
     */
    public boolean isEmpty(){
        return history.isEmpty();
    }
}
