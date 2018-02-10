package capstone.heartbeat.models;

/**
 * Created by Lenevo on 2/3/2018.
 */

public class Bank extends User {

    public Bank() {
    }

    public int id;
    public int hearts;
    public int coins;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(int totaltime) {
        this.totaltime = totaltime;
    }

    public int totaltime;
}
