package sg.edu.sportsschool.Sample;

import java.time.LocalDate;

public class AvailablePassesView {
    public AvailablePassesView(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private LocalDate date;
    private String description;

}
