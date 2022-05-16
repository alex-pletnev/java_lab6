package util;

import data.City;

import java.time.LocalDateTime;
import java.util.TreeSet;

public class CollectionManager {
    private TreeSet<City> collection = new TreeSet<>();
    private final LocalDateTime collectionInitTime = LocalDateTime.now();
    private final String FILE_PATH = System.getenv("MY_PATH");
    public TreeSet<City> getCollection() {
        return collection;
    }
    public LocalDateTime getCollectionInitTime() {
        return collectionInitTime;
    }
    public String getFilePath() {
        return FILE_PATH;
    }
    public void setCollection(TreeSet<City> collection) {
        this.collection = collection;
    }
}