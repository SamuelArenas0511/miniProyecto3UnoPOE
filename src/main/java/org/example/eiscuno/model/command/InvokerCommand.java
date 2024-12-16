package org.example.eiscuno.model.command;

import org.example.eiscuno.model.command.specific_commads.ShowResult;

public class InvokerCommand{
    private Command command;

    /**
     * Assigns the command to be executed.
     *
     * @param command El comando a ejecutar.
     */
    public InvokerCommand(Command command){
        this.command = command;
    }

    /**
     * Execute the assigned command.
     */
    public void invoke() {
        if (command != null) {
            command.execute();
        }else{
            System.out.println("Command is null");
        }
    }
}
