package commands;

import exception.ArgumentException;
import exception.CommandException;
import util.Manager;
import util.Reply;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public interface CommandInterface extends Serializable {
    boolean getNewEl();
    String getName();
    Reply execute(Manager manager) throws CommandException;
    List<Object> getArgList();
    void setArgList(String[] argArr);
    List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException;
}
