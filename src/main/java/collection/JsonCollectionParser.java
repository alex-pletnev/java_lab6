package collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data.City;
import util.CollectionManager;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class JsonCollectionParser {

    public void parseJsonToSet(CollectionManager collectionManager) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        List<String> dateList = new ArrayList<>();
        File file = new File(collectionManager.getFilePath());
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            System.err.println("Недостаточно прав для создания файла или указан неверный путь!");
            exception.printStackTrace();
        }
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(collectionManager.getFilePath()))) {
            //парсинг всего кроме creationDate
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String json = scanner.hasNext() ? scanner.next() : "";

            TreeSet<City> collection = gson.fromJson(json, new TypeToken<TreeSet<City>>() {}.getType());
            if (collection != null) {

                //парсинг creationDate
                String[] splitJson = json.split("\n");
                for (String str : splitJson) {
                    if (str.matches("[ \t\n\\x0B\f\r]* \"creationDate\": \".*\",")) {
                        dateList.add(str.split("\"")[3]);
                    }
                }
                int i = 0;
                for (City city : collection) {
                    try {
                        city.setCreationDate(LocalDate.parse(dateList.get(i), DateTimeFormatter.ISO_LOCAL_DATE));

                        city.setCreationDateStr(dateList.get(i));
                    } catch (Exception ex) {
                        ex.getMessage();
                        i++;
                    }

                }
                for (City city : collection) {
                    if (city.validation()) {
                        collectionManager.getCollection().add(city);
                    }
                }
            } else {
                collectionManager.setCollection(new TreeSet<>());
            }
        } catch (IOException ex) {
            System.err.println("Ошибка при чтении файла!");
        }
    }
}
