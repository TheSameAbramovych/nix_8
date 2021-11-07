package ua.com.alevel.level_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BracketsSequence {

    public BracketsSequence() {
        Scanner scanner = new Scanner(System.in);
        String tmp = scanner.nextLine();

        List<Character> openBrackets = List.of('(', '{', '[');
        List<Character> closeBrackets = List.of(')', '}', ']');
        List<Character> holder = new ArrayList<>();

        for (char symbol : tmp.toCharArray()) {
            if (openBrackets.contains(symbol)) {
                holder.add(symbol);
            } else if (closeBrackets.contains(symbol)) {
                char openPair = openBrackets.get(closeBrackets.indexOf(symbol));
                if (holder.size() != 0 && holder.get(holder.size() - 1) == openPair) {
                    holder.remove(holder.size() - 1);
                } else {
                    holder.add(symbol);
                    break;
                }
            }
        }
        System.out.println(holder.size() == 0);
    }
}
