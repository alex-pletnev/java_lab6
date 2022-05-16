import java.util.Collection;

public class testWiled {
    public void met1(Collection<?> collection) {
        collection.forEach(System.out::println);
    }
    public double met2(Collection<? extends Number> collection) {
        double x = 0;
        for (Number number : collection){
            x = number.doubleValue() + x;
        }
        return x;
    }
    public void met3(Collection<? super Number> collection) {
        collection.forEach(System.out::println);
    }

}
