package commands.com;

import commands.CommandAbstract;
import commands.ElementInput;
import data.City;
import exception.ArgumentException;
import util.Manager;
import util.Reply;

import java.util.List;
import java.util.Scanner;

public class Remove_greater extends CommandAbstract {
    private final String name = "remove_greater";
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        if (getArgList().size() != 0) {
            throw new ArgumentException("Аргумент введен неверно!");
        }
        City newCity = ElementInput.getNewElement(scanner, mode);
        if (newCity == null) {
            throw new ArgumentException("Ошибка в файле");
        }
        getArgList().add(newCity);

        return getArgList();
    }
    @Override
    public boolean getNewEl() {
        return true;
    }
    @Override
    public Reply execute(Manager manager) {
        City testCity = (City) getArgList().get(0);
        manager.getCollectionManager().getCollection().removeIf(city -> city.compareTo(testCity) > 0);
        return new Reply("Комманда выполненна!");
    }
}
