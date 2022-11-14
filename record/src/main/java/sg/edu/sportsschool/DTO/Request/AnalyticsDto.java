package sg.edu.sportsschool.DTO.Request;

public class AnalyticsDto {
    private int fromDay;
    private int fromMonth;
    private int fromYear;
    private int toDay;
    private int toMonth;
    private int toYear;

    public AnalyticsDto() {}

    public AnalyticsDto(int fromDay, int fromMonth, int fromYear, int toDay, int toMonth, int toYear) {
        this.fromDay = fromDay;
        this.fromMonth = fromMonth;
        this.fromYear = fromYear;
        this.toDay = toDay;
        this.toMonth = toMonth;
        this.toYear = toYear;
    }

    public int getFromDay() {
        return fromDay;
    }

    public void setFromDay(int fromDay) {
        this.fromDay = fromDay;
    }

    public int getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(int fromMonth) {
        this.fromMonth = fromMonth;
    }

    public int getFromYear() {
        return fromYear;
    }

    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    public int getToDay() {
        return toDay;
    }

    public void setToDay(int toDay) {
        this.toDay = toDay;
    }

    public int getToMonth() {
        return toMonth;
    }

    public void setToMonth(int toMonth) {
        this.toMonth = toMonth;
    }

    public int getToYear() {
        return toYear;
    }

    public void setToYear(int toYear) {
        this.toYear = toYear;
    }
}
