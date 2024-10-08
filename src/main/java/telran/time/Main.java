package telran.time;

import java.text.DateFormatSymbols;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

record MonthYear(int month, int year) {
}

public class Main {
    private static final int WIDTH = 43;
    private static final int COLUMN_WIDTH = 6;
    private static final int DAYS_IN_WEEK = 7;

    /**
     * 
     * @param args arg0 - month, arg1 - year, arg2 - calendarStartDay
     * 
     */

    public static void main(String[] args) {

        try {
            MonthYear monthYear = getMonthYear(args);
            int calendarStartDay = getCalendarStartDay(args);
            printCalendar(monthYear, calendarStartDay);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static MonthYear getMonthYear(String[] args) throws Exception {
        LocalDate currentDate = LocalDate.now();
        MonthYear res = new MonthYear(currentDate.getMonthValue(), currentDate.getYear());

        if (args.length > 1) {
            try {
                int month = parseMonth(args[0]);
                int year = parseYear(args[1]);
                res = new MonthYear(month, year);
            } catch (NumberFormatException e) {
                throw new Exception("The year and month must be integers.");
            } catch (DateTimeException e) {
                throw new Exception("Invalid value for year or month (month should be 1-12).");
            }
        }
        return res;
    }

    private static int parseMonth(String monthStr) throws DateTimeException {
        int month = Integer.parseInt(monthStr);
        if (month < 1 || month > 12) {
            throw new DateTimeException("Month must be between 1 and 12.");
        }
        return month;
    }

    private static int parseYear(String yearStr) throws NumberFormatException {
        return Integer.parseInt(yearStr);
    }

    private static int getCalendarStartDay(String[] args) throws Exception {
        int startDayIndex = 1;
        if (args.length > 2) {
            try {
                startDayIndex = Integer.parseInt(args[2]);
                if (startDayIndex < 1 || startDayIndex > 7) {
                    throw new Exception("Index of start day must be between 1 (Sunday) and 7 (Saturday)");
                }
            } catch (NumberFormatException e) {
                throw new Exception("The start day index must be an integer.");
            }
        }
        return startDayIndex;
    }

    private static void printCalendar(MonthYear monthYear, int calendarStartDay) {
        printTitle(monthYear);
        printWeekDays(calendarStartDay);
        printDates(monthYear, calendarStartDay);
    }

    private static void printTitle(MonthYear monthYear) {
        System.out.println();
        int year = monthYear.year();
        int month = monthYear.month();
        LocalDate date = LocalDate.of(year, month, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        printCenteredText(String.valueOf(year) + " " + date.format(formatter), 43);
    }

    private static void printWeekDays(int dayOfWeekStart) {

        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] dayNames = symbols.getShortWeekdays();
        String[] reorderedDays = new String[DAYS_IN_WEEK];
        reorderDays(dayOfWeekStart, dayNames, reorderedDays);
        printDaysNames(reorderedDays);
    }

    private static void printDaysNames(String[] reorderedDays) {

        String dashLine = " ".repeat(1) + "-".repeat(WIDTH);
        System.out.println(dashLine);
        System.out.print(" ");
        for (String day : reorderedDays) {
            System.out.printf("%-" + COLUMN_WIDTH + "s", "| " + day + " ");
        }
        System.out.println("|");
        System.out.println(dashLine);
    }

    private static void reorderDays(int dayOfWeekStart, String[] dayNames, String[] reorderedDays) {

        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            int index = (dayOfWeekStart + i - 1) % DAYS_IN_WEEK + 1;
            reorderedDays[i] = dayNames[index];
        }
    }

    private static void printDates(MonthYear monthYear, int calendarStartDay) {
        int lastDayOfMonth = getLastDayOfMonth(monthYear);
        int startDay = getFirstDayOfWeek(monthYear);
        int offset = getOffset(startDay, calendarStartDay);
        printOffset(offset);
        printDatesLayout(lastDayOfMonth, offset);
    }

    private static void printDatesLayout(int lastDayOfMonth, int offset) {

        for (int day = 1; day <= lastDayOfMonth; day++) {
            if (String.valueOf(day).length() < 2) {
                System.out.printf("%6s", "|   " + String.valueOf(day) + "");

            } else {
                System.out.printf("%6s", "|  " + String.valueOf(day) + "");
            }
            if ((day + offset) % DAYS_IN_WEEK == 0) {
                System.out.println(" |");
            }
        }

        if ((lastDayOfMonth + offset) % DAYS_IN_WEEK != 0) {
            System.out.println();
        }
        String dashLine = " ".repeat(1) + "-".repeat(WIDTH);
        System.out.println(dashLine);
        System.out.println();
    }

    private static void printOffset(int offset) {
        System.out.print(" ".repeat(offset * 6));
    }

    private static int getFirstDayOfWeek(MonthYear monthYear) {
        return LocalDate.of(monthYear.year(), monthYear.month(), 1)
                .getDayOfWeek()
                .getValue();
    }

    private static int getOffset(int firstWeekDay, int calendarStartDay) {
        return calendarStartDay != 1 ? (firstWeekDay - calendarStartDay + 8) % 7 : firstWeekDay % 7;
    }

    private static int getLastDayOfMonth(MonthYear monthYear) {
        return YearMonth.of(monthYear.year(), monthYear.month()).lengthOfMonth();
    }

    private static void printCenteredText(String text, int length) {
        if (text.length() >= length) {

            System.out.println(text);
        } else {
            int padLeft = (length - text.length()) / 2;
            System.out.printf("%" + (length - padLeft) + "s%n", text);
        }
    }

}