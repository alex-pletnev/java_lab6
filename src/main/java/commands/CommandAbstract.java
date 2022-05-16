package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandAbstract implements CommandInterface {
    private List<Object> argList = new ArrayList<>();
    private String name;
    @Override
    public String getName() {
        return name;
    }
    @Override
    public List<Object> getArgList() {
        return argList;
    }
    @Override
    public void setArgList(String[] argArr) {
        argList.addAll(Arrays.asList(argArr));
        argList.remove(0);
    }
}
