import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Holidays_v1 {

    private static BufferedReader reader;
    private static String paramDay = "2013/01/01";
    private static int paramDays = 10;

    public static void main(String[] args) {

//        if(args.length < 2){
//            System.exit(1);
//        }
        Date firstDate = new Date(paramDay);
        Date secondDate = new Date(firstDate.getTime() + 1000 * 60 * 60 * 24);
        Date endDate = new Date(firstDate.getTime() + 1000 * 60 * 60 * 24 * paramDays);

        System.out.println(firstDate + " - " + endDate);

        File f = new File("./res/data/holidays.txt");

        TreeSet<Holiday> holidays = new TreeSet<Holiday>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "windows-1251"));
            String line = null;
            int rowsCount = 0;
            while ((line = reader.readLine()) != null) {
                rowsCount++;
                int i = line.indexOf(' ');
                holidays.add(new Holiday(new Date(line.substring(0, i)), line.substring(i + 1)));
            }

            reader.close();

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


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
