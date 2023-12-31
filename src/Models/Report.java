package Models;

public class Report {
    long date;
    String type;
    long total;

    public Report(long date, String type, long total) {
        this.date = date;
        this.type = type;
        this.total = total;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}