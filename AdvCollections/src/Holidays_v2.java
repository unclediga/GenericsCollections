import java.io.*;
import java.lang.ref.WeakReference;
import java.util.*;

public class Holidays_v2 {

    private static BufferedReader reader;
    private static String paramDay = "2013/01/01";
    private static int paramDays = 10;

    private static TreeSet<Holiday> holidays = new TreeSet<Holiday>();

    static final WeakHashMap<String, WeakReference<String>> countries = new WeakHashMap<String, WeakReference<String>>();
    private static final WeakHashMap<String, String> countries2 = new WeakHashMap<String, String>();
    private static final WeakHashMap<String, WeakReference<String>> holydaysNames = new WeakHashMap<String, WeakReference<String>>();
    private static final WeakHashMap<Date, WeakReference<Date>> dates = new WeakHashMap<Date, WeakReference<Date>>();


    public static void main(String[] args) {

//        if(args.length < 2){
//            System.exit(1);
//        }
        Date firstDate = new Date(paramDay);
        Date secondDate = new Date(firstDate.getTime() + 1000 * 60 * 60 * 24);
        Date endDate = new Date(firstDate.getTime() + 1000 * 60 * 60 * 24 * paramDays);

        System.out.println(firstDate + " - " + endDate);


        File f = new File("./res/data/holidays.txt");

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "windows-1251"));
            String line = null;
            int rowsCount = 0;
            while ((line = reader.readLine()) != null) {
                rowsCount++;
                Holiday h = parseHolidayString(line);
                //System.out.println("adding "+h);
                holidays.add(h);
            }

            reader.close();

            showShedule(firstDate, secondDate, endDate);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            showCollections();


            holidays.clear();

            System.gc();

            System.out.println("\nAfter GC........");

            showCollections();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            showCollections();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void showCollections() {
        System.out.println("Countries size()=" + countries.size());
        System.out.println("Countries2 size()=" + countries2.size());
        System.out.println("Dates size()=" + dates.size());
        System.out.println("Names size()=" + holydaysNames.size());
    }

    private static void showShedule(Date firstDate, Date secondDate, Date endDate) {
        SortedSet<Holiday> h1 = holidays.subSet(new Holiday(firstDate, ""), new Holiday(firstDate, "яя"));
        System.out.println("\nToday........");
        Iterator<Holiday> it = h1.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        SortedSet<Holiday> h2 = holidays.subSet(new Holiday(secondDate, ""), new Holiday(secondDate, "яя"));
        System.out.println("\nTomorrow........");
        it = h2.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        SortedSet<Holiday> h3 = holidays.subSet(new Holiday(secondDate, "яя"), new Holiday(endDate, "яя"));
        System.out.println("\nLater........");
        it = h3.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    private static Holiday parseHolidayString(String line) {

        int n = line.indexOf(' ');
        Date date = new Date(line.substring(0, n));
        String holidayName = line.substring(n + 1, line.lastIndexOf('(')).trim();
        String country = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')')).trim();

        if (dates.containsKey(date))
            date = (Date) ((WeakReference) dates.get(date)).get();
        else
            dates.put(date, new WeakReference(date));


        if (countries.containsKey(country))
            country = (String) ((WeakReference)countries.get(country)).get();
        else{
                countries.put(country, new WeakReference<String>(country));
                // this doing cross ref countries <-> countries2
                // So, GC can not clear this maps.
                //        countries2.put(country, country);
               // hard link val <-> key/
               // countries2 never be cleared by GC
                String s = new String(country);
                countries2.put(s, s);

            }

        if (holydaysNames.containsKey(holidayName)) {
            holidayName = (String) ((WeakReference) holydaysNames.get(holidayName)).get();
        } else {
            holydaysNames.put(holidayName, new WeakReference<String>(holidayName));
        }

        return new Holiday(date, holidayName, country);
    }
}
