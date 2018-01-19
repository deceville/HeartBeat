package capstone.heartbeat.models;

/**
 * Created by Lenevo on 1/19/2018.
 */

public class Goal {
    public String USERUID;
    public String GoalID;
    public String Description;
    public String Duration;
    public boolean completed;
    public Goal() {
    }

    public String getUSERUID() {
        return USERUID;
    }

    public void setUSERUID(String USERUID) {
        this.USERUID = USERUID;
    }

    public String getGoalID() {
        return GoalID;
    }

    public void setGoalID(String goalID) {
        GoalID = goalID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
