package capstone.heartbeat;

/**
 * Created by torre on 11/8/2017.
 */

public class Qrisk2Female {
    private double age,sbp, totalchl, hdl, height,weight;
    private int ethnic,  gender, smoke,af, diabType1,diabType2,fhcvd,ra,CKD,CHF, HA, VHD,bptreatment;
    private double town = 1;
    public  void Qrisk2Female(double age,double sbp,double totalchl,double hdl,double height,double weight, int ethnic, int gender, int smoke
            ,int af,int diabType1, int diabType2, int fhcvd, int ra, int CKD, int CHF, int HA, int VHD,int bptreatment){
       this.age= age;
       this.sbp = sbp;
       this.totalchl = totalchl;
       this.af = af;
       this.hdl = hdl;
       this.gender = gender;
       this.ethnic = ethnic;
       this.diabType1 = diabType1;
       this.diabType2 = diabType2;
       this.ra = ra;
       this.CHF = CHF;
       this.smoke = smoke;
       this.fhcvd = fhcvd;
       this.CKD = CKD;
       this.HA = HA;
       this.VHD = VHD;
       this.height = height;
       this.weight = weight;
       this.bptreatment = bptreatment;
    }

    public double getResult(){
       double surv = 0.989747583866119;

	/* The conditional arrays */

        double[] Iethrisk = {
                0,
                0,
                0.2574099349831925900000000,
                0.6129795430571779400000000,
                0.3362159841669621300000000,
                0.1512517303224336400000000,
                -0.1794156259657768100000000,
                -0.3503423610057745400000000,
                -0.2778372483233216800000000,
                -0.1592734122665366000000000

        };
        double[] Ismoke = {
                0,
                0.2119377108760385200000000,
                0.6618634379685941500000000,
                0.7570714587132305600000000,
                0.9496298251457036000000000

        };

	/* Applying the fractional polynomial transforms */
	/* (which includes scaling)                      */

        double dage = age;
        dage=dage/10;
        double age_1 = Math.pow(dage,.5);
        double age_2 = dage;
        double bmi = weight/Math.pow(height/100,2);
        double dbmi = bmi;
        dbmi=dbmi/10;
        double bmi_2 = Math.pow(dbmi,-2)*Math.log(dbmi);
        double bmi_1 = Math.pow(dbmi,-2);

	/* Centring the continuous variables */

	    double rati = totalchl/hdl;

        age_1 = age_1 - 2.086397409439087;
        age_2 = age_2 - 4.353054523468018;
        bmi_1 = bmi_1 - 0.152244374155998;
        bmi_2 = bmi_2 - 0.143282383680344;
        rati = rati - 3.506655454635620;
        sbp = sbp - 125.040039062500000;
        town = town - 0.416743695735931;


	/* Start of Sum */
        double a=0;

	/* The conditional sums */

        a += Iethrisk[ethnic];
        a += Ismoke[smoke];

	/* Sum from continuous values */

        a += age_1 * 4.4417863976316578000000000;
        a += age_2 * 0.0281637210672999180000000;
        a += bmi_1 * 0.8942365304710663300000000;
        a += bmi_2 * -6.5748047596104335000000000;
        a += rati * 0.1433900561621420900000000;
        a += sbp * 0.0128971795843613720000000;
        a += town * 0.0664772630011438850000000;


	/* Sum from boolean values */

        a += af * 1.6284780236484424000000000;
        a += ra * 0.2901233104088770700000000;
        a += CKD * 1.0043796680368302000000000;
        a += bptreatment * 0.6180430562788129500000000;
        a += diabType1 * 1.8400348250874599000000000;
        a += diabType2 * 1.1711626412196512000000000;
        a += fhcvd * 0.5147261203665195500000000;


	/* Sum from interaction terms */
     switch (smoke){
         case 1:a += age_1 * Ismoke[smoke] * 0.6186864699379683900000000;
         case 2:a += age_1 * Ismoke[smoke] * 1.5522017055600055000000000;
         case 3:a += age_1 * Ismoke[smoke] * 2.4407210657517648000000000;
         case 4: a += age_1 * Ismoke[smoke] * 3.5140494491884624000000000;
     }


        a += age_1 * af * -7.0177986441269269000000000;
        a += age_1 * CKD * -2.9684019256454390000000000;
        a += age_1 * bptreatment* -4.2219906452967848000000000;
        a += age_1 * diabType1 * 1.6835769546040080000000000;
        a += age_1 * diabType2 * -2.9371798540034648000000000;
        a += age_1 * bmi_1 * 0.1797196207044682300000000;
        a += age_1 * bmi_2 * 40.2428166760658140000000000;
        a += age_1 * fhcvd * 0.1439979240753906700000000;
        a += age_1 * sbp * -0.0362575233899774460000000;
        a += age_1 * town * 0.3735138031433442600000000;

        switch (smoke){
            case 1:a += age_1 * Ismoke[smoke] *  0.7464406144391666500000000;
            case 2:a += age_1 * Ismoke[smoke] *0.2568541711879666600000000;
            case 3:a += age_1 * Ismoke[smoke] * -1.5452226707866523000000000;
            case 4: a += age_1 * Ismoke[smoke] * -1.7113013709043405000000000;
        }

        a += age_2 * af * 1.1395776028337732000000000;
        a += age_2 * CKD * 0.4356963208330940600000000;
        a += age_2 * bptreatment * 0.7265947108887239600000000;
        a += age_2 * diabType1 * -0.6320977766275653900000000;
        a += age_2 * diabType2 * 0.4023270434871086800000000;
        a += age_2 * bmi_1 * 0.1319276622711877700000000;
        a += age_2 * bmi_2 * -7.3211322435546409000000000;
        a += age_2 * fhcvd * -0.1330260018273720400000000;
        a += age_2 * sbp * 0.0045842850495397955000000;
        a += age_2 * town * -0.0952370300845990780000000;


	/* Calculate the score itself */
        double score = 100.0 * (1 - Math.pow(surv, Math.exp(a)) );
        return score;
    }
}
