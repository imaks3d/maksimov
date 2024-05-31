package dataBase;

import java.util.HashSet;
import java.util.Set;

public class IdManager {
    public static Set<Integer> ListID = new HashSet();
    static int current_id = 1;

    public static void AddId(Integer id) {
        ListID.add(id);
    }

    public static void RemoveId(Integer id) {
        ListID.remove(id);
    }

    public static Integer GetNewId() {
        for(current_id = 1; ListID.contains(current_id); ++current_id) {
        }

        return current_id;
    }
}
