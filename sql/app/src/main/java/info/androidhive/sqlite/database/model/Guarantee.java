package info.androidhive.sqlite.database.model;



public class Guarantee {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_FROM_DT = "from_dt";
    public static final String COLUMN_TO_DT = "to_dt";

    private int id;
    private String note;
    private String timestamp;
    private String from_dt;
    private String to_dt;


    // Create table SQL query

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_FROM_DT + " DATETIME,"
                    + COLUMN_TO_DT + " DATETIME"
                    + ")";
    public Guarantee() {
    }

    public Guarantee(int id, String note, String timestamp, String from_dt, String to_dt) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
        this.from_dt = from_dt;
        this.to_dt = to_dt;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFrom_dt() {
        return from_dt;
    }

    public void setFrom_dt(String from_dt) {
        this.from_dt = from_dt;
    }

    public String getTo_dt() {
        return to_dt;
    }

    public void setTo_dt(String to_dt) {
        this.to_dt = to_dt;
    }
}
