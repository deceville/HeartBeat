package capstone.heartbeat.models;

/**
 * Created by Lenevo on 1/19/2018.
 */

public class Goal {
    public String USERUID;
    public int GoalID;
    public String Description;
    public String Duration;
    public boolean completed;
    public String goals;
    public String action;



    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double value;

    public String getGoals() {
        return goals;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }
    public Goal() {
    }

    public Goal(String USERUID, int goalID, String description, String duration, boolean completed, String goals,double value,String action) {
        this.USERUID = USERUID;
        GoalID = goalID;
        Description = description;
        Duration = duration;
        this.completed = completed;
        this.goals = goals;
        this.value = value;
        this.action = action;
    }

    public String getUSERUID() {
        return USERUID;
    }

    public void setUSERUID(String USERUID) {
        this.USERUID = USERUID;
    }

    public int getGoalID() {
        return GoalID;
    }

    public void setGoalID(int goalID) {
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
