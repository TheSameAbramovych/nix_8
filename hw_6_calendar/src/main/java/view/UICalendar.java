package view;

import java.io.BufferedReader;

public class UICalendar {

    private static void runNavigation() {
        System.out.println();
        System.out.println("""
                Создать групу -> 1
                Изменить данные групы -> 2
                Удалить групу -> 3
                Найти групу по Id -> 4
                Список всех груп -> 5
                Удалить студента из групы -> 6
                Назад -> 0
                """);
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> runNavigation();

        }


    }
}
