import java.util.ArrayList;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        String str = "qwerty";
        List<Object> oList = new ArrayList<>();
        oList.add(str);
        str = (String) oList.get(0);
        System.out.println(str);

    }
}
