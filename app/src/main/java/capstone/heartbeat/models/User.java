package capstone.heartbeat.models;

/**
 * Created by Lenevo on 1/19/2018.
 */

public class User {

    public int id;

    public double getMets() {
        return mets;
    }

    public void setMets(double mets) {
        this.mets = mets;
    }

    public double mets;
    public String username;
    public String email;
    public String name;
    public String password;

    public String birth;
    public String gender;
    public int chl;
    public int hdl;
    public int sbp;
    public int dbp;
    public int smoke;
    public int bptr;
    public String sleep;
    public int act;
    public int diab1;
    public int diab2;
    public int chf;
    public int ha;
    public int vhd;
    public int ra;
    public int rha;
    public int ckd;
    public int fhcvd;
    public int heart_attack;
    public int stroke;
    public int age;
    public double weight;
    public double height;
    public boolean isCalculated;

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setCalculated(boolean calculated) {
        isCalculated = calculated;
    }

    public User() {
    }

    public User(String username,String email,String name,String password){
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(double mets){
        this.mets = mets;
    }

    public User(String birth,String gender,int chl,int hdl,int sbp,int dbp,int smoke,int bptr,String sleep,int act,int diab1,int diab2,int chf,
                int ha,int vhd,int ra,int rha,int ckd,int fhcvd,int heart_attack,int stroke,double weight,double height){
        this.birth = birth;
        this.gender = gender;
        this.chl = chl;
        this.hdl = hdl;
        this.sbp = sbp;
        this.dbp = dbp;
        this.smoke = smoke;
        this.bptr = bptr;
        this.sleep = sleep;
        this.act = act;
        this.diab1 = diab1;
        this.diab2 = diab2;
        this.chf = chf;
        this.ha = ha;
        this.vhd = vhd;
        this.ra = ra;
        this.rha = rha;
        this.ckd = ckd;
        this.fhcvd = fhcvd;
        this.heart_attack = heart_attack;
        this.stroke = stroke;
        this.weight = weight;
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getChl() {
        return chl;
    }

    public void setChl(int chl) {
        this.chl = chl;
    }

    public int getHdl() {
        return hdl;
    }

    public void setHdl(int hdl) {
        this.hdl = hdl;
    }

    public int getSbp() {
        return sbp;
    }

    public void setSbp(int sbp) {
        this.sbp = sbp;
    }

    public int getDbp() {
        return dbp;
    }

    public void setDbp(int dbp) {
        this.dbp = dbp;
    }

    public int getSmoke() {
        return smoke;
    }

    public void setSmoke(int smoke) {
        this.smoke = smoke;
    }

    public int getBptr() {
        return bptr;
    }

    public void setBptr(int bptr) {
        this.bptr = bptr;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public int getAct() {
        return act;
    }

    public void setAct(int act) {
        this.act = act;
    }

    public int getDiab1() {
        return diab1;
    }

    public void setDiab1(int diab1) {
        this.diab1 = diab1;
    }

    public int getDiab2() {
        return diab2;
    }

    public void setDiab2(int diab2) {
        this.diab2 = diab2;
    }

    public int getChf() {
        return chf;
    }

    public void setChf(int chf) {
        this.chf = chf;
    }

    public int getHa() {
        return ha;
    }

    public void setHa(int ha) {
        this.ha = ha;
    }

    public int getVhd() {
        return vhd;
    }

    public void setVhd(int vhd) {
        this.vhd = vhd;
    }

    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public int getRha() {
        return rha;
    }

    public void setRha(int rha) {
        this.rha = rha;
    }

    public int getCkd() {
        return ckd;
    }

    public void setCkd(int ckd) {
        this.ckd = ckd;
    }

    public int getFhcvd() {
        return fhcvd;
    }

    public void setFhcvd(int fhcvd) {
        this.fhcvd = fhcvd;
    }

    public int getHeart_attack() {
        return heart_attack;
    }

    public void setHeart_attack(int heart_attack) {
        this.heart_attack = heart_attack;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

}
