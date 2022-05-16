package commands.com;

import commands.CommandAbstract;
import exception.ArgumentException;
import util.Manager;
import util.Reply;

import java.util.List;
import java.util.Scanner;

public class Show extends CommandAbstract {
    private final String name = "show";
    @Override
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        if (getArgList().size() != 0) {
            throw new ArgumentException("Аргумент введен неверно!");
        }
        return getArgList();
    }
    @Override
    public boolean getNewEl() {
        return false;
    }
    @Override
    public Reply execute(Manager manager) {
        StringBuilder ans = new StringBuilder();
        manager.getCollectionManager().getCollection().forEach(city -> ans.append("City: ").append(city).append("\n"));
        return new Reply(ans.toString());
    }
}
