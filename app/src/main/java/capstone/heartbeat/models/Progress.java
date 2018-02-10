package capstone.heartbeat.models;

/**
 * Created by Lenevo on 2/3/2018.
 */

public class Progress {
    public String type;
    public double progress;

    public Progress() {
    }

    public Progress(String type, double progress, String date) {
        this.type = type;
        this.progress = progress;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;


}
