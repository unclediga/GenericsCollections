import java.text.SimpleDateFormat;
import java.util.Date;

public class Holiday implements Comparable {
    private Date date;
    private String name;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Holiday(Date date, String name, String country) {

        this.date = date;
        this.name = name;
        this.country = country;
    }

    private String country;

    public Holiday(Date date, String name) {
        this.date = date;
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "date=" + SimpleDateFormat.getDateInstance().format(date) +
                ", name='" + name + '\'' +
                ", count='" + country + '\'' +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        Holiday h = (Holiday) o;
//        if(o == null){
//            System.err.println("this "+this);
//            System.err.println("o "+ h);
//
//        }
//        System.err.println("this " + getDate());
//        System.err.println("o "+ h.getDate());

        if (getDate().compareTo(h.getDate()) == 0){
//            System.err.println("this " + getName());
//            System.err.println("o "+ h.getName());

            return getName().compareTo(h.getName());
        }
        else
            return getDate().compareTo(h.getDate());
    }
}
