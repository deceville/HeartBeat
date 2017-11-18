package capstone.heartbeat;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;

/**
 * Created by torre on 11/8/2017.
 */

public class Qrisk2Male {
    private double age,sbp, totalchl, hdl, height,weight,bmi,rati;
    private int ethnic, smoke,af, diabType1,diabType2,fhcvd,ra,CKD,CHF, HA, VHD,bptreatment;
    private double town = 1;

    public Qrisk2Male() {
    }

    public  void Qrisk2Male(double age,double sbp,double totalchl,double hdl,double height,double weight, int ethnic,  int smoke
            ,int af,int diabType1, int diabType2, int fhcvd, int ra,int bptreatment){
       this.age= age;
       this.sbp = sbp;
       this.totalchl = totalchl;
       this.af = af;
       this.hdl = hdl;
       this.ethnic = ethnic;
       this.diabType1 = diabType1;
       this.diabType2 = diabType2;
       this.ra = ra;
       this.smoke = smoke;
       this.fhcvd = fhcvd;
       this.height = height;
       this.weight = weight;
       this.bptreatment = bptreatment;
    }



    public double getResult(double[] continuous, int[] bool){
        this.age= continuous[0];
       this.sbp = continuous[1];
      this.totalchl = continuous[2];
        this.af = bool[2];
       this.hdl = continuous[3];
        this.ethnic = bool[0];
        this.diabType1 = bool[3];
        this.diabType2 = bool[4];
        this.ra = bool[6];
        this.smoke = bool[1];
        this.fhcvd = bool[5];
        this.height = continuous[4];
        this.weight = continuous[5];
        this.bptreatment = bool[7];
       double surv = 0.978794217109680;

	/* The conditional arrays */

        double[] Iethrisk = {
            0,
                    0,
                    0.3173321430481919100000000,
                    0.4738590786081115500000000,
                    0.5171314655968145500000000,
                    0.1370301157366419200000000,
                    -0.3885522304972663900000000,
                    -0.3812495485312194500000000,
                    -0.4064461381650994500000000,
                    -0.2285715521377336100000000
        };
        double[] Ismoke = {
            0,
                    0.2684479158158020200000000,
                    0.6307674973877591700000000,
                    0.7178078883378695700000000,
                    0.8704172533465485100000000
        };

	/* Applying the fractional polynomial transforms */
	/* (which includes scaling)                      */



        double dage = age;
        dage=dage/10;
        double age_1 = pow(dage,-1);
        double age_2 = pow(dage,2);
        this.bmi = weight/ pow(height/100,2);
        double dbmi = bmi;
        dbmi=dbmi/10;
        double bmi_2 = pow(dbmi,-2)* log(dbmi);
        double bmi_1 = pow(dbmi,-2);

	/* Centring the continuous variables */

	    this.rati = totalchl/hdl;

        age_1 = age_1 - 0.233734160661697;
        age_2 = age_2 - 18.304403305053711;
        bmi_1 = bmi_1 - 0.146269768476486;
        bmi_2 = bmi_2 - 0.140587374567986;
        double ratio = rati - 4.321151256561279;
        double sbp1 = sbp - 130.589752197265620;
        double town1 = town - 0.551009356975555;

	/* Start of Sum */
        double a=0;

	/* The conditional sums */

        a += Iethrisk[ethnic];
        a += Ismoke[smoke];

	/* Sum from continuous values */

        a += age_1 * -18.0437312550377270000000000;
        a += age_2 * 0.0236486454254306940000000;
        a += bmi_1 * 2.5388084343581578000000000;
        a += bmi_2 * -9.1034725871528597000000000;
        a += ratio * 0.1684397636136909500000000;
        a += sbp1 * 0.0105003089380754820000000;
        a += town1 * 0.0323801637634487590000000;

	/* Sum from boolean values */

        a += af * 1.0363048000259454000000000;
        a += ra * 0.2519953134791012600000000;
        a += CKD * 0.8359352886995286000000000;
        a += bptreatment * 0.6603459695917862600000000;
        a += diabType1 * 1.3309170433446138000000000;
        a += diabType2 * 0.9454348892774417900000000;
        a += fhcvd * 0.5986037897136281500000000;

	/* Sum from interaction terms */
     switch (smoke){
         case 1:a += age_1 * Ismoke[smoke] * 0.6186864699379683900000000;
         case 2:a += age_1 * Ismoke[smoke] * 1.5522017055600055000000000;
         case 3:a += age_1 * Ismoke[smoke] * 2.4407210657517648000000000;
         case 4: a += age_1 * Ismoke[smoke] * 3.5140494491884624000000000;
     }




        a += age_1 * af * 8.0382925558108482000000000;
        a += age_1 * CKD * -1.6389521229064483000000000;
        a += age_1 * bptreatment * 8.4621771382346651000000000;
        a += age_1 * diabType1 * 5.4977016563835504000000000;
        a += age_1 * diabType2 * 3.3974747488766690000000000;
        a += age_1 * bmi_1 * 33.8489881012767600000000000;
        a += age_1 * bmi_2 * -140.6707025404897100000000000;
        a += age_1 * fhcvd * 2.0858333154353321000000000;
        a += age_1 * sbp1 * 0.0501283668830720540000000;
        a += age_1 * town1 * -0.1988268217186850700000000;
        switch (smoke){
            case 1:a += age_2 * Ismoke[smoke] *  -0.0040893975066796338000000;
            case 2:a += age_2 * Ismoke[smoke] * -0.0056065852346001768000000;
            case 3:a += age_2 * Ismoke[smoke] * -0.0018261006189440492000000;
            case 4: a += age_2 * Ismoke[smoke] * -0.0014997157296173290000000;
        }

        a += age_2 * af * 0.0052471594895864343000000;
        a += age_2 * CKD * -0.0179663586193546390000000;
        a += age_2 * bptreatment * 0.0092088445323379176000000;
        a += age_2 * diabType1 * 0.0047493510223424558000000;
        a += age_2 * diabType2 * -0.0048113775783491563000000;
        a += age_2 * bmi_1 * 0.0627410757513945650000000;
        a += age_2 * bmi_2 * -0.2382914909385732100000000;
        a += age_2 * fhcvd * -0.0049971149213281010000000;
        a += age_2 * sbp1 * -0.0000523700987951435090000;
        a += age_2 * town1 * -0.0012518116569283104000000;

	/* Calculate the score itself */
        double score = 100.0 * (1 - pow(surv, exp(a)) );
        return score;
    }

    public double getStrokeResult(int vhd,int ckd,int chf,int ha){
        this.HA = ha;
        this.CHF = chf;
        this.VHD = vhd;
        this.CKD = ckd;
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

        double dage = age;
        dage=dage/10;
        double age_1 = pow(dage,-1);
        double age_2 = pow(dage,-1)*log(dage);
        double dbmi = bmi;
        dbmi=dbmi/10;
        double bmi_1 = pow(dbmi,-2);
        double bmi_2 = pow(dbmi,-2)*log(dbmi);
        double surv2 = 9.2403842314780817000000000;

	/* Centring the continuous variables */

        age_1 = age_1 - 0.223224431276321;
        age_2 = age_2 - 0.334742367267609;
        bmi_1 = bmi_1 - 0.144959196448326;
        bmi_2 = bmi_2 - 0.139980062842369;
        double ratio = rati - 4.374470233917236;
        double sbp2 = sbp - 131.996871948242190;
        double town2 = town - -0.014665771275759;

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
        a += ratio * 0.0740254180616774230000000;
        a += sbp2 * 0.0141266123505316420000000;
        a += town2 * 0.0410201030054811660000000;

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
            case 1:a += age_1 * Ismoke[smoke] *  0.1319310717787916000000000;
            case 2:a += age_1 * Ismoke[smoke] *  -3.7777100480315955000000000;
            case 3:a += age_1 * Ismoke[smoke] * -2.0141772764248631000000000;
            case 4: a += age_1 * Ismoke[smoke] * -3.2046066813681535000000000;
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
        a += age_1 * sbp2 * -0.0525602068113598110000000;
        a += age_1 * town2 * -0.6221233366101081000000000;
        switch (smoke){
            case 1:a += age_2 * Ismoke[smoke] *  1.5734752401338576000000000;
            case 2:a += age_2 * Ismoke[smoke] *   10.1004566263405860000000000;
            case 3:a += age_2 * Ismoke[smoke] * 5.9354809751853459000000000;
            case 4: a += age_2 * Ismoke[smoke] * 9.2403842314780817000000000;
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
        a += age_2 * sbp2 * 0.2323283327543341500000000;
        a += age_2 * town2 * 1.4980914447710214000000000; 

	/* Calculate the score itself */
        double score = 100.0 * (1 - Math.pow(surv2, Math.exp(a)) );
        return score;
    }
}
