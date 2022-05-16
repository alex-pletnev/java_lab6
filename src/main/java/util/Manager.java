package util;

public class Manager {
    private final CollectionManager collectionManager = new CollectionManager();
    private final CommandManager commandManager = new CommandManager();

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
