package util;

public class OutputManager {
    public static void printReply(String rep) {
        System.out.println(rep);
    }
    public static void printReply(Reply rep) {
        System.out.println(rep);
    }
    public static void printErr(String err) {
        System.err.println(err);
    }
}
