package util;

import java.security.SecureRandom;
import java.util.Set;
import java.util.TreeSet;

public class IdManager {
    private static Set<Long> idSet = new TreeSet<>();
    private static final SecureRandom sr = new SecureRandom();
    public static long generateNewId() {
        long newId = 0;
        boolean flag = false;
        while (!flag) {
            newId = Math.abs(sr.nextLong());
            if (!idSet.contains(newId)) {
                idSet.add(newId);
                flag = true;
            }
        }
        return newId;
    }
}
