package ua.com.alevel.task2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Task2 {
    public void uniqueElem() {
        List<String> list = new ArrayList<>();
        Map<String, Boolean> result = new LinkedHashMap<>();
        list.add("Egor");
        list.add("Arnold");
        list.add("Arnold");
        list.add("David");
        list.add("David");
        list.add("Petr");
        list.add("Egor");
        list.add("Rudolf");
        list.add("Frank");
        list.add("Bohdan");
        list.add("Georg");

        for (String s : list) {
            if (result.containsKey(s)) {
                result.put(s, false);
            } else {
                result.put(s, true);
            }
        }

        for (Map.Entry<String, Boolean> entry : result.entrySet()) {
            if (entry.getValue()) {
                System.out.println(entry.getKey());
                break;
            }
        }

    }
}
