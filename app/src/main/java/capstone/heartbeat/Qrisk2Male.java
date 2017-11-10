package capstone.heartbeat;

/**
 * Created by torre on 11/8/2017.
 */

public class Qrisk2Male {
    private double age,sbp, totalchl, hdl, height,weight;
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


    public void setValue( double[] continuous, int[] bool){
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
    }
    public double getResult(){
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
        double age_1 = Math.pow(dage,-1);
        double age_2 = Math.pow(dage,2);
        double bmi = weight/Math.pow(height/100,2);
        double dbmi = bmi;
        dbmi=dbmi/10;
        double bmi_2 = Math.pow(dbmi,-2)*Math.log(dbmi);
        double bmi_1 = Math.pow(dbmi,-2);

	/* Centring the continuous variables */

	    double rati = totalchl/hdl;

        age_1 = age_1 - 0.233734160661697;
        age_2 = age_2 - 18.304403305053711;
        bmi_1 = bmi_1 - 0.146269768476486;
        bmi_2 = bmi_2 - 0.140587374567986;
        rati = rati - 4.321151256561279;
        sbp = sbp - 130.589752197265620;
        town = town - 0.551009356975555;

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
        a += rati * 0.1684397636136909500000000;
        a += sbp * 0.0105003089380754820000000;
        a += town * 0.0323801637634487590000000;

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
        a += age_1 * sbp * 0.0501283668830720540000000;
        a += age_1 * town * -0.1988268217186850700000000;
        switch (smoke){
            case 1:a += age_1 * Ismoke[smoke] *  -0.0040893975066796338000000;
            case 2:a += age_1 * Ismoke[smoke] * -0.0056065852346001768000000;
            case 3:a += age_1 * Ismoke[smoke] * -0.0018261006189440492000000;
            case 4: a += age_1 * Ismoke[smoke] * -0.0014997157296173290000000;
        }

        a += age_2 * af * 0.0052471594895864343000000;
        a += age_2 * CKD * -0.0179663586193546390000000;
        a += age_2 * bptreatment * 0.0092088445323379176000000;
        a += age_2 * diabType1 * 0.0047493510223424558000000;
        a += age_2 * diabType2 * -0.0048113775783491563000000;
        a += age_2 * bmi_1 * 0.0627410757513945650000000;
        a += age_2 * bmi_2 * -0.2382914909385732100000000;
        a += age_2 * fhcvd * -0.0049971149213281010000000;
        a += age_2 * sbp * -0.0000523700987951435090000;
        a += age_2 * town * -0.0012518116569283104000000;

	/* Calculate the score itself */
        double score = 100.0 * (1 - Math.pow(surv, Math.exp(a)) );
        return score;
    }
}
