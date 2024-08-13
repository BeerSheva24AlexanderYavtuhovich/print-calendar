package telran.time;

record MonthYear(int month, int year) {
}

public class Main {
    public static void main(String[] args) {

        try {
            MonthYear monthYear = getMonthYear(args); // if no arguments current year and month by default
            printCalendar(monthYear);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static MonthYear getMonthYear(String[] args) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDates(monthYear);
    }

    private static void printTitle(MonthYear monthYear) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void printWeekDays() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static void printDates(MonthYear monthYear) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private int getFirstDayOfWeek(MonthYear monthYear) {
        return -1;// or exception unsupported operation exception
    }

    private int getOffset(int firstWeekDay) {
        // TODO
        // shift on this offset for starting printing

        return -1;
    }

    private int getLastDayOfMonth(MonthYear monthYear) {
        // TODO
        return -1;
    }

    // printf("%4d",number) until last day of month
    // <condition of \n>
}