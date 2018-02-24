package capstone.heartbeat.models;

/**
 * Created by torre on 2/13/2018.
 */

public class Plan {
    Plan(){}

    private String title;
    private String progress_desc;
    private double max;
    private double progress;
    private int id;

    public Plan(String title, String progress_desc, double max, double progress, int id) {
        this.title = title;
        this.progress_desc = progress_desc;
        this.max = max;
        this.progress = progress;
        this.id = id;
    }

    public Plan (String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProgress_desc() {
        return progress_desc;
    }

    public void setProgress_desc(String progress_desc) {
        this.progress_desc = progress_desc;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
