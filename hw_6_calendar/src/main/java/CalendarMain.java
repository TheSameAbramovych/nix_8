import util.Calendar;
import util.SimpleDateParser;
import util.TimeType;

public class CalendarMain {
    public static void main(String[] args) {
        try {
            Calendar calendar = new Calendar(TimeType.MILLISECOND.to(0, TimeType.MILLISECOND));
//        Scanner scanner = new Scanner(System.in);
//        Calendar calendar = new Calendar(scanner.nextLine());
//        Calendar calendar1 = new Calendar("//5000 23");
//        System.out.println(calendar.interval(calendar1).to(TimeType.MILLISECOND));
//        System.out.println(calendar.to(TimeType.DAY));
//        System.out.println(calendar.dayInYear());
//        System.out.println(calendar.getDaysInYears(2000,2100));
//        System.out.println(calendar.getMonthsInYear(2000,2100));

//        System.out.println(TimeType.DAY.to(1826 + 1826, TimeType.MILLISECOND) == TimeType.YEAR.to(10, TimeType.MILLISECOND));
//        System.out.println(TimeType.DAY.to(1826 + 1826, TimeType.HOUR) == TimeType.YEAR.to(10, TimeType.HOUR));
//        System.out.println(TimeType.MONTH.to(120, TimeType.MILLISECOND) == TimeType.YEAR.to(10, TimeType.MILLISECOND));
//        System.out.println(TimeType.YEAR.to(2021, TimeType.MILLISECOND) - TimeType.YEAR.to(1970, TimeType.MILLISECOND));
//        System.out.println(System.currentTimeMillis());
//        System.out.println(TimeType.DAY.to(1825, TimeType.MONTH));
//        System.out.println(calendar.to(TimeType.DAY));
//        System.out.println(calendar.add(1, TimeType.DAY).to(TimeType.DAY));

//        System.out.println(
//                (calendar.add(338, TimeType.DAY)
//                        .add(18, TimeType.HOUR)
//                        .add(24, TimeType.MINUTE).to(TimeType.MILLISECOND) -
//                        System.currentTimeMillis()));

//            System.out.println(calendar.add(1, TimeType.MONTH)
//                    .interval(new Calendar(TimeType.DAY.to(29, TimeType.MILLISECOND))).to(TimeType.HOUR));

            SimpleDateParser parser = new SimpleDateParser("dd/mm/yyyy 00:00");
            long ff = System.currentTimeMillis();
            Calendar date = parser.parse("09/12/51 00:00")
//                    .subtract(new Calendar().add(1970, TimeType.YEAR).to(TimeType.DAY), TimeType.DAY)
                    .subtract(ff, TimeType.MILLISECOND);
            long gg = date.to(TimeType.DAY);

            System.out.println(gg);
            System.out.println(parser.parse("2/2/0 00:00").to(TimeType.DAY));
            System.out.println(parser.format(parser.parse("21/0/3 00:00")));
            System.out.println(TimeType.DAY.to(2, TimeType.HOUR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
