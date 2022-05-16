package commands.com;

import commands.CommandAbstract;
import exception.ArgumentException;
import util.Manager;
import util.Reply;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class History extends CommandAbstract {
    private final String name = "history";
    private static final List<String> historyList = new ArrayList<>();
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
        ans.append("---------------------");
        ans.append("---------------------");
        while (historyList.size() > 14) {
            historyList.remove(0);
        }
        for (String com : historyList) {
            ans.append(com);
        }
        ans.append("---------------------");
        ans.append("---------------------");

        return new Reply(ans.toString());
    }

    public static List<String> getHistoryList() {
        return historyList;
    }
}
