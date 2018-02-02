package capstone.heartbeat.calculators;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;

/**
 * Created by torre on 11/8/2017.
 */

public class QStrokeFemale {
    private double age,sbp, totalchl, hdl, height,weight,bmi,rati,dbp;
    private int ethnic, smoke,af, diabType1,diabType2,fhcvd,ra,CKD,CHF, HA, VHD,bptreatment;
   // private double town = 1;

    public QStrokeFemale() {
    }




    public double getResult(double[] continuous, int[] bool,int vhd,int chf,int ha){
        this.age= continuous[0];
       this.sbp = continuous[1];
       this.dbp = continuous[2];
      this.totalchl = continuous[3];
        this.af = bool[2];
       this.hdl = continuous[4];
        this.ethnic = bool[0];
        this.diabType1 = bool[3];
        this.diabType2 = bool[4];
        this.ra = bool[6];
        this.smoke = bool[1];
        this.fhcvd = bool[5];
        this.height = continuous[5];
        this.weight = continuous[6];
        this.bptreatment = bool[7];
        this.HA = ha;
        this.CHF = chf;
        this.VHD = vhd;
        this.CKD = bool[8];
        this.bmi = weight/ pow(height/100,2);
        this.rati = totalchl/hdl;
        double surv = 0.994471669197083;
        double town = 1;
        double[] Iethrisk = {
                0,
                0,
                -0.0305394929576588490000000,
                0.4427611182840293100000000,
                0.2673795862919842200000000,
                -0.2598308364468712700000000,
                -0.0488012419137342430000000,
                -0.2788553112730757200000000,
                -0.6767143572327304300000000,
                -0.1611535009190185600000000
        };
        double[] Ismoke = {
                0,
                0.1498305575251711900000000,
                0.4519183493573110700000000,
                0.6146335482699555300000000,
                0.8131785122592680700000000
        };

	/* Applying the fractional polynomial transforms */
	/* (which includes scaling)                      */

        double dage = age;
        dage=dage/10;
        double age_1 = pow(dage,2);
        double age_2 = pow(dage,3);
        double dbmi = bmi;
        dbmi=dbmi/10;
        double bmi_1 = pow(dbmi,-2);
        double bmi_2 = pow(dbmi,-2)*log(dbmi);

	/* Centring the continuous variables */

        age_1 = age_1 - 20.710655212402344;
        age_2 = age_2 - 94.252044677734375;
        bmi_1 = bmi_1 - 0.152111470699310;
        bmi_2 = bmi_2 - 0.143223732709885;
        rati = rati - 3.597237586975098;
        sbp = sbp - 127.181053161621090;
        town = town - -0.092155806720257;

	/* Start of Sum */
        double a=0;

	/* The conditional sums */

        a += Iethrisk[ethnic];
        a += Ismoke[smoke];

	/* Sum from continuous values */

        a += age_1 * 0.1697320518297478200000000;
        a += age_2 * -0.0093489805139222847000000;
        a += bmi_1 * 2.3158227325733081000000000;
        a += bmi_2 * -8.3927388985024596000000000;
        a += rati * 0.0763818306795134570000000;
        a += sbp * 0.0110106548819008700000000;
        a += town * 0.0569282538300162900000000;

	/* Sum from boolean values */

        a += af * 1.1236185329326394000000000;
        a +=CHF * 1.0018266666317022000000000;
        a += HA * 1.1384605450143492000000000;
        a += ra * 0.2895019448611921300000000;
        a += CKD * 0.3840433250496960200000000;
        a += bptreatment * 0.6000589936358939900000000;
        a += diabType1 * 1.2931653635533871000000000;
        a += diabType2 * 0.7743227044341611800000000;
        a += VHD * 0.8823685364057471900000000;
        a += fhcvd * 0.2851807472688047700000000;

	/* Sum from interaction terms */

        switch (smoke){
            case 1:a += age_1 * Ismoke[smoke] * -0.0021738924907283397000000;break;
            case 2:a += age_1 * Ismoke[smoke] *  0.0092958354691607976000000;break;
            case 3:a += age_1 * Ismoke[smoke] *  -0.0238445203390637290000000;break;
            case 4: a += age_1 * Ismoke[smoke] * -0.0442081168504774720000000;break;
        }
        a += age_1 * af * -0.0378746660763275570000000;
        a += age_1 * CHF * -0.0703338932750366820000000;
        a += age_1 * HA * -0.1242795995222882000000000;
        a += age_1 * bptreatment * -0.0547533824497907770000000;
        a += age_1 * diabType1 * -0.0104325848808183630000000;
        a += age_1 * diabType2 * -0.0543527244063423470000000;
        a += age_1 * VHD * -0.0681294786249953820000000;
        a += age_1 * bmi_1 * 0.0366468069607603160000000;
        a += age_1 * bmi_2 * 0.8374396689614352900000000;
        a += age_1 * fhcvd * -0.0209588947842873420000000;
        a += age_1 * sbp * -0.0000512566841259084420000;
        a += age_1 * town * -0.0016537747988553985000000;
        switch (smoke){
            case 1:a += age_2 * Ismoke[smoke] * 0.0000417982804007417310000;break;
            case 2:a += age_2 * Ismoke[smoke] *  -0.0015127223843011640000000;break;
            case 3:a += age_2 * Ismoke[smoke] *  0.0018092337569956974000000;break;
            case 4: a += age_2 * Ismoke[smoke] * 0.0037591358040036760000000;break;
        }
        a += age_2 * af * 0.0026309641213272967000000;
        a += age_2 * CHF * 0.0052237718320413701000000;
        a += age_2 * HA * 0.0105765921592317290000000;
        a += age_2 * bptreatment * 0.0045886801340551909000000;
        a += age_2 * diabType1 * -0.0031870689210806201000000;
        a += age_2 * diabType2 * 0.0044700226930346355000000;
        a += age_2 * VHD * 0.0052433370244195712000000;
        a += age_2 * bmi_1 * -0.0082953327794458437000000;
        a += age_2 * bmi_2 * -0.0642802685411782150000000;
        a += age_2 * fhcvd * 0.0014106757779590851000000;
        a += age_2 * sbp * -0.0000179366158909618290000;
        a += age_2 * town * 0.0000037518903323942880000;

	/* Calculate the score itself */
        double score = 100.0 * (1 - Math.pow(surv, Math.exp(a)) );
        return score;
    }


}
