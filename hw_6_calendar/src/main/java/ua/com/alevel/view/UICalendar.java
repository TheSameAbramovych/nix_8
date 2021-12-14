package ua.com.alevel.view;

import ua.com.alevel.util.Calendar;
import ua.com.alevel.util.SimpleDateParser;
import ua.com.alevel.util.TimeType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class UICalendar {
    private SimpleDateParser parser = new SimpleDateParser("dd/mm/yyyy 00:00");

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                if (position.equals("0")) {
                    System.exit(0);
                }
                crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("Вас приветствует Календарь!");
        System.out.println("Дефолтный формат даты: dd/mm/yyyy 00:00");
        System.out.println("""
                Выбор формата даты -> 1
                Отнять дату от даты -> 2
                Отнять число от даты -> 3
                Добавит число к дате -> 4
                Сортировать даты по возростанию -> 5
                Сортировать даты по убыванию -> 6
                Выход -> 0
                """);
    }

    private void crud(String position, BufferedReader reader) {
        try {
            switch (position) {
                case "1" -> dataParser(reader);
                case "2" -> subtract(reader);
                case "3" -> subtractElement(reader);
                case "4" -> add(reader);
                case "5" -> sortAsc(reader);
                case "6" -> sortDesc(reader);
            }
            runNavigation();
        } catch (Exception e) {
            System.out.println("Неккоректный ввод: " + e.getMessage());
        }
    }

    public void dataParser(BufferedReader reader) {
        try {
            System.out.println("Введите свой формат, используя разделитель '/' для дат и ':' для времени :");
            System.out.println("d,dd - день; m,mm,mmm - месяц; yyyy - год");
            System.out.print("Формат для mmm: ");
            System.out.println("JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER");
            parser = new SimpleDateParser(reader.readLine());
        } catch (Exception e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    public void subtract(BufferedReader reader) {
        try {
            System.out.println("Выберите выводимый тип: ");
            System.out.println("""
                    MS->Миллисекунды
                    S->Секунды
                    D->Дни
                    M->Месяцы
                    Y->Года
                    """);
            String input = reader.readLine();
            if (input.equals("MS")) {
                input = "MILLISECOND";
            }
            if (input.equals("S")) {
                input = "SECOND";
            }
            if (input.equals("D")) {
                input = "DAY";
            }
            if (input.equals("M")) {
                input = "MONTH";
            }
            if (input.equals("Y")) {
                input = "YEAR";
            }
            TimeType type = TimeType.valueOf(input);
            System.out.println("Введите дату от которой отнимаем: ");
            Calendar calendar = new Calendar(parser.parse(reader.readLine()).to(TimeType.MILLISECOND));
            System.out.println("Введите отнимаемую дату: ");
            Calendar calendar2 = new Calendar(parser.parse(reader.readLine()).to(TimeType.MILLISECOND));
            System.out.println(calendar.subtract(calendar2).to(type));
        } catch (Exception e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    public void subtractElement(BufferedReader reader) {
        try {
            System.out.println("Выберите выводимый/вводимый тип: ");
            System.out.println("""
                    MS->Миллисекунды
                    S->Секунды
                    D->Дни
                    M->Месяцы
                    Y->Года
                    """);
            String input = reader.readLine();
            if (input.equals("MS")) {
                input = "MILLISECOND";
            }
            if (input.equals("S")) {
                input = "SECOND";
            }
            if (input.equals("D")) {
                input = "DAY";
            }
            if (input.equals("M")) {
                input = "MONTH";
            }
            if (input.equals("Y")) {
                input = "YEAR";
            }
            TimeType type = TimeType.valueOf(input);
            System.out.println("Введите дату: ");
            Calendar calendar = new Calendar(parser.parse(reader.readLine()).to(TimeType.MILLISECOND));
            System.out.println("Введите число: ");
            long time = Long.parseLong(reader.readLine());
            String fin = parser.format(calendar.subtract(time, type));
            System.out.println(fin);
        } catch (Exception e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    public void add(BufferedReader reader) {
        try {
            System.out.println("Выберите выводимый/вводимый тип: ");
            System.out.println("""
                    MS->Миллисекунды
                    S->Секунды
                    D->Дни
                    M->Месяцы
                    Y->Года
                    """);
            String input = reader.readLine();
            if (input.equals("MS")) {
                input = "MILLISECOND";
            }
            if (input.equals("S")) {
                input = "SECOND";
            }
            if (input.equals("D")) {
                input = "DAY";
            }
            if (input.equals("M")) {
                input = "MONTH";
            }
            if (input.equals("Y")) {
                input = "YEAR";
            }
            TimeType type = TimeType.valueOf(input);
            System.out.println("Введите дату: ");
            Calendar calendar = parser.parse(reader.readLine());
            System.out.println("Введите число: ");
            long time = Long.parseLong(reader.readLine());
            String fin = parser.format(calendar.add((time), type));
            System.out.println(fin);
        } catch (Exception e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    public void sortDesc(BufferedReader reader) {
        try {
            System.out.println("Введите перечень дат, используя ',' как разделитель между датами.");
            String[] d = reader.readLine().split(",");
            List<Calendar> cal = new ArrayList<>(d.length);
            for (String s : d) {
                cal.add(parser.parse(s));
            }
            cal.sort(Calendar::compareT);
            for (Calendar s : cal) {
                System.out.print(parser.format(s) + "; ");
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

    public void sortAsc(BufferedReader reader) {
        try {
            System.out.println("Введите перечень дат, используя ',' как разделитель между датами.");
            String[] d = reader.readLine().split(",");
            List<Calendar> cal = new ArrayList<>(d.length);
            for (String s : d) {
                cal.add(parser.parse(s));
            }
            cal.sort(Calendar::compareTo);
            for (Calendar s : cal) {
                System.out.print(parser.format(s) + "; ");

            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Проблемка : " + e.getMessage());
        }
    }

}
