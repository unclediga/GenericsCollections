import java.text.SimpleDateFormat;
import java.util.Date;

public class Holiday implements Comparable {
    private Date date;
    private String name;

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
        if (getDate().compareTo(h.getDate()) == 0)
            return getName().compareTo(h.getName());
        else
            return getDate().compareTo(h.getDate());
    }
}
