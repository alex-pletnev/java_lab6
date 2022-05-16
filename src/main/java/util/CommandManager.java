package util;

import commands.CommandInterface;
import commands.com.*;

import java.util.Map;
import java.util.TreeMap;

public class CommandManager {
    private final Map<String , CommandInterface> commandMap = new TreeMap<>();
    {
        commandMap.put("help", new Help());
        commandMap.put("info", new Info());
        commandMap.put("show", new Show());
        commandMap.put("add", new Add());
        commandMap.put("update", new Update());
        commandMap.put("remove_by_id", new Remove_by_id());
        commandMap.put("clear", new Clear());
        commandMap.put("save", new Save());
        commandMap.put("exit", new Exit());
        commandMap.put("add_if_max", new Add_if_max());
        commandMap.put("remove_greater", new Remove_greater());
        commandMap.put("history", new History());
        commandMap.put("remove_any_by_meters_above_sea_level", new Remove_any_by_meters_above_sea_level());
        commandMap.put("group_counting_by_meters_above_sea_level", new Group_counting_by_meters_above_sea_level());
        commandMap.put("print_ascending", new Print_ascending());

    }

    public Map<String, CommandInterface> getCommandMap() {
        return commandMap;
    }
}
