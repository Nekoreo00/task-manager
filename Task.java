public class Task {

    String month;
    String day;
    String hour;
    String minute;
    String note;

    boolean completed;

    public Task(
        String month,
        String day,
        String hour,
        String minute,
        String note
    ) {
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.note = note;

        this.completed = false;
    }

    public String getTitle() {

        return month
            + day
            + " "
            + hour.replace("時", "")
            + ":"
            + minute.replace("分", "")
            + " ～"
            + note;
    }
}