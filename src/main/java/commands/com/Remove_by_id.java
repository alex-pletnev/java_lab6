package commands.com;

import commands.CommandAbstract;
import data.City;
import exception.ArgumentException;
import exception.CommandException;
import util.Manager;
import util.Reply;

import java.util.List;
import java.util.Scanner;

public class Remove_by_id extends CommandAbstract {
    private final String name = "remove_by_id";

    @Override
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        if (getArgList().size() != 1) {
            throw new ArgumentException("Неверное количество аргументов повторите ввод команды!");
        }
        try {
            Long.parseLong(((String) getArgList().get(0)));
        } catch (NumberFormatException exception) {
            throw new ArgumentException("Неверный тип данных аргументов(тип данных long)");
        }
        return getArgList();
    }

    @Override
    public boolean getNewEl() {
        return false;
    }
    @Override
    public Reply execute(Manager manager) throws CommandException {
        long id = Long.parseLong(((String) getArgList().get(0)));
        City correctCity = manager.getCollectionManager().getCollection()
                .stream()
                .filter(city -> city.getId() == id)
                .findAny()
                .orElse(null);

        if (correctCity == null) {
            throw new CommandException("Элемент с данным id не найден");
        }

        manager.getCollectionManager().getCollection().remove(correctCity);
        return new Reply("Элемент удаллен!");
    }
}
