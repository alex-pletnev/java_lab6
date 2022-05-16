package commands.com;

import commands.CommandAbstract;
import data.City;
import exception.ArgumentException;
import util.Manager;
import util.Reply;

import java.util.*;
import java.util.stream.Collectors;

public class Group_counting_by_meters_above_sea_level extends CommandAbstract {
    private final String name = "group_counting_by_meters_above_sea_level";
    private int i = 0;
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

        Set<Double> metersSet = manager.getCollectionManager().getCollection()
                .stream()
                .map(City::getMetersAboveSeaLevel)
                .collect(Collectors.toSet())
                ;

        for (Double key : metersSet) {
            i++;
            ans.append("Группа ")
                    .append(i)
                    .append(": ")
                    .append(manager.getCollectionManager().getCollection()
                            .stream()
                            .filter(city -> city.getMetersAboveSeaLevel() == key)
                            .count()
                    )
                    .append("\n");
        }
        return new Reply(ans.toString());
    }
}
