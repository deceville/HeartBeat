package capstone.heartbeat.models;

public class Suggestions {
    int id;
    String title;
    String desc;
    boolean checked;



    Suggestions(int id, String title, String desc, boolean checked) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
