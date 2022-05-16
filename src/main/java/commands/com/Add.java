package commands.com;

import commands.CommandAbstract;
import commands.ElementInput;
import data.City;
import exception.ArgumentException;
import exception.CommandException;
import util.Manager;
import util.Reply;

import java.util.List;
import java.util.Scanner;


public class Add extends CommandAbstract {
    private final String name = "add";

    @Override
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
    public Reply execute(Manager manager) throws CommandException{
        City newCity = (City) getArgList().get(0);
        for (City city : manager.getCollectionManager().getCollection()) {
            if (newCity.compareTo(city) == 0) {
                throw new CommandException("Элемент не добавленн т.к. элемент эквивкелентный этому уже есть!");
            }
        }


        manager.getCollectionManager().getCollection().add(newCity);

        return new Reply("Элемент добавлен!");
    }


}
