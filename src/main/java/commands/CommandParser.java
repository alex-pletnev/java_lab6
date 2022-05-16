package commands;

import commands.com.ExecuteScript;
import commands.com.History;
import exception.ArgumentException;
import exception.CommandException;
import exception.UnknownCommandException;
import util.CommandManager;
import util.Manager;
import util.Reply;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandParser {


    public CommandInterface parseCommand(CommandManager manager, Scanner input) {
            try {
                String str = input.nextLine();
                if (!str.equals("")) {
                    String[] splitStr = str.split(" ");

                    if (splitStr[0].equals("execute_script")) {

                        ExecuteScript executeScript = new ExecuteScript();
                        try {
                            executeScript.setArgList(splitStr);
                            executeScript.checkArguments();
                            System.out.println(executeScript.execute());
                        } catch (ArgumentException | CommandException ex) {
                            System.err.println(ex.getMessage());
                        }

                        return null;
                    }

                    if (manager.getCommandMap().get(splitStr[0]) == null) {
                        try {
                            throw new UnknownCommandException("Введена неизвестная команда!");
                        } catch (UnknownCommandException ex) {
                            System.err.println(ex.getMessage());
                            return null;
                        }
                    } else if (splitStr[0].equals("save")) {
                        System.err.println("Недостаточно прав");
                        return null;
                    }
                    if (splitStr[0].equals("exit")) {
                        System.out.println("Сеанс завершенн");
                        System.exit(1);
                    }
                    manager.getCommandMap().get(splitStr[0]).setArgList(splitStr);
                    CommandInterface command = manager.getCommandMap().get(splitStr[0]);
                    try {
                        command.checkArguments(input, 0);
                    } catch (ArgumentException ex) {
                        System.err.println(ex.getMessage());
                        return null;
                    }



                    return command;

                } else {
                    System.err.print("Enter your command...(plz)\n");
                    return null;
                }
            } catch (NoSuchElementException ex) {
                System.err.println("Обнорженно прирывание!!");
                System.exit(1);
                return null;
            }
    }
    public Reply executeCommand(Manager manager, CommandInterface command) {
        try {
            Reply reply = command.execute(manager);
            History.getHistoryList().add(command.getName());
            return reply;
        } catch (CommandException ex) {
            return new Reply(ex.getMessage());
        }
    }
}
