package ua.com.alevel.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Task1 {
    private final List<Character> delimiters = List.of('/', ':', '.', '-', ',');
    private final String day = "day";
    private final String month = "month";
    private final String year = "year";
    private final Map<String, List<String>> formats = Map.of(
            "/", List.of(day, month, year),
            "-", List.of(month, day, year),
            ".", List.of(day, month, year),
            ":", List.of(day, month, year)
    );

    public void calendar() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Hello, enter the date: ");
            String dataInput = reader.readLine();
            List<String> stringList = List.of(dataInput.split(","));
            StringBuilder outputFinal = new StringBuilder();
            for (String s : stringList) {
                char[] symbol = s.toCharArray();
                List<String> result = new ArrayList<>();
                String delimiter = null;
                StringBuilder tmp = new StringBuilder();

                for (char c : symbol) {
                    if (Character.isDigit(c)) {
                        tmp.append(c);
                    }
                    if (delimiters.contains(c)) {
                        delimiter = delimiter == null ? String.valueOf(c) : delimiter;
                        result.add(tmp.toString());
                        tmp = new StringBuilder();
                    }
                }
                result.add(tmp.toString());

                if (delimiter != null) {
                    String stringFinal = String.join(delimiter, result);

                    if (!stringFinal.equals(s) && result.size() == 3) {
                        System.out.println("Incorrect input");
                        break;
                    } else {
                        List<String> seq = formats.get(delimiter);
                        if (seq != null
                                && !result.get(seq.indexOf(day)).isEmpty()
                                && !result.get(seq.indexOf(month)).isEmpty()
                                && !result.get(seq.indexOf(year)).isEmpty()
                                && Integer.parseInt(result.get(seq.indexOf(day))) <= 31
                                && Integer.parseInt(result.get(seq.indexOf(month))) <= 12) {

                            String fin = result.get(seq.indexOf(year)) + result.get(seq.indexOf(month))
                                    + result.get(seq.indexOf(day));

                            outputFinal.append(fin).append(", ");
                        } else {
                            System.out.println("Incorrect input");
                            break;
                        }
                    }
                } else {
                    System.out.println("Incorrect input");
                    break;
                }
            }

            System.out.println(outputFinal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
