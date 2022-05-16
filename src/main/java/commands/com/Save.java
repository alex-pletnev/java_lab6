package commands.com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import commands.CommandAbstract;
import exception.ArgumentException;
import exception.CommandException;
import util.Manager;
import util.Reply;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Save extends CommandAbstract {
    private final String name = "save";
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
    public Reply execute(Manager manager) throws CommandException{
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        try (PrintWriter printWriter = new PrintWriter(manager.getCollectionManager().getFilePath())){
            gson.toJson(manager.getCollectionManager().getCollection(), printWriter);
        } catch (IOException ex) {
            throw new CommandException("Ошибка при сохранении в файл!");
        }

        return new Reply("Коллекция сохранена в фаил");
    }
}
