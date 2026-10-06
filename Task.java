public class Task {

    private int id;

    private String month;
    private String day;
    private String hour;
    private String minute;
    private String note;

    private boolean completed;


    // 新規タスク作成用
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


    // DBから読み込む用
    public Task(
        int id,
        String month,
        String day,
        String hour,
        String minute,
        String note,
        boolean completed
    ) {

        this.id = id;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.note = note;
        this.completed = completed;
    }


    // =========================
    // getter
    // =========================

    public int getId() {
        return id;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getNote() {
        return note;
    }

    public boolean isCompleted() {
        return completed;
    }


    // =========================
    // setter
    // =========================

    public void setId(
        int id
    ) {
        this.id = id;
    }

    public void setMonth(
        String month
    ) {
        this.month = month;
    }

    public void setDay(
        String day
    ) {
        this.day = day;
    }

    public void setHour(
        String hour
    ) {
        this.hour = hour;
    }

    public void setMinute(
        String minute
    ) {
        this.minute = minute;
    }

    public void setNote(
        String note
    ) {
        this.note = note;
    }

    public void setCompleted(
        boolean completed
    ) {
        this.completed = completed;
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