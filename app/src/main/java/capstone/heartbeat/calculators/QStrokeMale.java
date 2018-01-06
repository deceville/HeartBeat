package capstone.heartbeat.calculators;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;

/**
 * Created by torre on 11/8/2017.
 */

public class QStrokeMale {
    private double age,sbp, totalchl, hdl, height,weight,bmi,rati;
    private int ethnic, smoke,af, diabType1,diabType2,fhcvd,ra,CKD,CHF, HA, VHD,bptreatment;
    private double town = 1;

    public QStrokeMale() {
    }




    public double getResult(double[] continuous, int[] bool,int vhd,int ckd,int chf,int ha){
        age= continuous[0];
       sbp = continuous[1];
       totalchl = continuous[2];
       af = bool[2];
       hdl = continuous[3];
       ethnic = bool[0];
       diabType1 = bool[3];
       diabType2  = bool[4];
       ra = bool[6];
       smoke = bool[1];
       fhcvd = bool[5];
       height = continuous[4];
       weight = continuous[5];
       bptreatment = bool[7];
       HA = ha;
       CHF = chf;
       VHD = vhd;
       CKD = ckd;
        double[] Iethrisk = {
                0,
                0,
                -0.0764368476458289900000000,
                0.0571357655504688010000000,
                0.0750168509341927470000000,
                -0.0255093418294540240000000,
                -0.0196755890137448010000000,
                -0.1747887493090693200000000,
                -0.4099010101086592600000000,
                -0.3078265643907502900000000
        };
        double[] Ismoke = {
                0,
                0.1552773552459725000000000,
                0.5296226955713869700000000,
                0.5329542987864534000000000,
                0.7249648735764742100000000
        };

	/* Applying the fractional polynomial transforms */
	/* (which includes scaling)                      */

     this.bmi = weight/ pow(height/100,2);
        this.rati = totalchl/hdl;
        double dage = age;
        dage=dage/10;
        double age_1 = Math.pow(dage,-1);
        double age_2 = Math.pow(dage,-1)*Math.log(dage);
        double dbmi = bmi;
        dbmi=dbmi/10;
        double bmi_1 = Math.pow(dbmi,-2);
        double bmi_2 = Math.pow(dbmi,-2)*Math.log(dbmi);
        double surv = 9.2403842314780817000000000;

	/* Centring the continuous variables */

        age_1 = age_1 - 0.223224431276321;
        age_2 = age_2 - 0.334742367267609;
        bmi_1 = bmi_1 - 0.144959196448326;
        bmi_2 = bmi_2 - 0.139980062842369;
        rati = rati - 4.374470233917236;
        sbp = sbp - 131.996871948242190;
        town = town - -0.014665771275759;

	/* Start of Sum */
        double a=0;

	/* The conditional sums */

        a += Iethrisk[ethnic];
        a += Ismoke[smoke];

	/* Sum from continuous values */

        a += age_1 * -3.9673936261765470000000000;
        a += age_2 * -39.0347420211951500000000000;
        a += bmi_1 * 5.1085638322885796000000000;
        a += bmi_2 * -10.3408874619570030000000000;
        a += rati * 0.0740254180616774230000000;
        a += sbp * 0.0141266123505316420000000;
        a += town * 0.0410201030054811660000000;

	/* Sum from boolean values */

        a += af * 0.4688956565252527800000000;
        a += CHF * 0.8849826595982599500000000;
        a += HA * 0.9405469903780190300000000;
        a += ra * 0.2165383322630131900000000;
        a += CKD * 0.3326830725248204300000000;
        a += bptreatment * 0.6116303404347371900000000;
        a += diabType1 * 1.2863652184197687000000000;
        a += diabType2 * 0.6758136449121166000000000;
        a += VHD * 0.8767695242359757600000000;
        a += fhcvd * 0.2555160668352805500000000;

	/* Sum from interaction terms */

        switch (smoke){
            case 1:a += age_1 * Ismoke[smoke] *  0.1319310717787916000000000;break;
            case 2:a += age_1 * Ismoke[smoke] *  -3.7777100480315955000000000;break;
            case 3:a += age_1 * Ismoke[smoke] * -2.0141772764248631000000000;break;
            case 4: a += age_1 * Ismoke[smoke] * -3.2046066813681535000000000;break;
        }

        a += age_1 * CHF * 16.9738747052129460000000000;
        a += age_1 *  HA * 13.2310834025785430000000000;
        a += age_1 * bptreatment * 11.4597040046828130000000000;
        a += age_1 * diabType1 * -2.5922675469325065000000000;
        a += age_1 * diabType2 * 2.3309672629845148000000000;
        a += age_1 * VHD * -0.5990694384329117200000000;
        a += age_1 * bmi_1 * 11.6370752202834820000000000;
        a += age_1 * bmi_2 * -106.2099990656921900000000000;
        a += age_1 * fhcvd * 1.6754678961432341000000000;
        a += age_1 * sbp * -0.0525602068113598110000000;
        a += age_1 * town * -0.6221233366101081000000000;
        switch (smoke){
            case 1:a += age_2 * Ismoke[smoke] *  1.5734752401338576000000000;break;
            case 2:a += age_2 * Ismoke[smoke] *   10.1004566263405860000000000;break;
            case 3:a += age_2 * Ismoke[smoke] * 5.9354809751853459000000000;break;
            case 4: a += age_2 * Ismoke[smoke] * 9.2403842314780817000000000;break;
        }

        a += age_2 * CHF * -11.7308096828027860000000000;
        a += age_2 * HA * -6.4805609091441072000000000;
        a += age_2 * bptreatment* -9.0447741378075630000000000;
        a += age_2 * diabType1 * 16.4496674300914910000000000;
        a += age_2 * diabType2 * 3.2445091076320054000000000;
        a += age_2 * VHD * 10.9327837964980700000000000;
        a += age_2 * bmi_1 * 31.2645930525128970000000000;
        a += age_2 * bmi_2 * 32.9081007514201290000000000;
        a += age_2 * fhcvd * 1.3708144818564409000000000;
        a += age_2 * sbp * 0.2323283327543341500000000;
        a += age_2 * town * 1.4980914447710214000000000;

	/* Calculate the score itself */
        double score = 100.0 * (1 - Math.pow(surv, Math.exp(a)) );
        return score;
    }


}
