package capstone.heartbeat.controllers;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import capstone.heartbeat.assessment.RiskResultsActivity;
import capstone.heartbeat.calculators.QStrokeFemale;
import capstone.heartbeat.calculators.QStrokeMale;
import capstone.heartbeat.calculators.Qrisk2Female;
import capstone.heartbeat.calculators.Qrisk2Male;

/**
 * Created by torre on 1/10/2018.
 */

public class ResultEvaluator extends RiskResultsActivity {
    private double suggestedMet;
    private SharedPreferences prefs;


    public ResultEvaluator(){}


    public double getSuggestedMet(){
        boolean giveSuggestion = false;
        int h = getBaseResult()[0];
        int s = getBaseResult()[1];

        int haDif = ha - h;
        int stDif = st - s;

        if (haDif > 0 || stDif>0){
            giveSuggestion = true;
            if (gender==0){
                if (age >=25&&age<=35){
                    suggestedMet = 12;return suggestedMet;
                }else if (age >=36&& age <=45){
                    suggestedMet = 10;return suggestedMet;
                }else if (age >=46&& age <=55){
                    suggestedMet = 8;return suggestedMet;
                }else if (age >=56&& age <=65){
                    suggestedMet = 4;return suggestedMet;
                }else if (age >=66&& age <=84){
                    suggestedMet = 2; return suggestedMet;
                }

            }else if (gender ==1){
                if (age >=25&&age<=35){
                    suggestedMet = 20;return suggestedMet;
                }else if (age >=36&& age <=45){
                    suggestedMet = 15;return suggestedMet;
                }else if (age >=46&& age <=55){
                    suggestedMet = 10;return suggestedMet;
                }else if (age >=56&& age <=65){
                    suggestedMet = 6;return suggestedMet;
                }else if (age >=66&& age <=84){
                    suggestedMet = 3; return suggestedMet;
                }
            }
        }else {
            giveSuggestion = false;
        }

        return suggestedMet;
    }

    public int[] getBaseResult(){

        final int bool[]={5,0,af,diabType1,diabType2,fhcvd,ra,0};
        final double continuous[]={age,120,200,60,170,65};

        int[] res = new int[2];
        Qrisk2Male hm = new Qrisk2Male();
        Qrisk2Female hf = new Qrisk2Female();
        QStrokeMale sm = new QStrokeMale();
        QStrokeFemale sf = new QStrokeFemale();
        int ha, st;
        double heartAttack=0,Stroke=0;
        if (gender==0){
            heartAttack = hf.getResult(continuous,bool);
            Stroke = sf.getResult(continuous,bool,VHD,CKD,CHF,HA);
            System.out.println(heartAttack+" "+Stroke);
            ha = (int) Math.floor(heartAttack);
            st = (int)Math.floor(Stroke);
            res[0]=ha;
            res[1]=st;
            return  res;

        }else if(gender==1){
            heartAttack = hm.getResult(continuous,bool);
            Stroke = sm.getResult(continuous,bool,VHD,CKD,CHF,HA);
            System.out.println(heartAttack+" "+Stroke);
            ha = (int) Math.floor(heartAttack);
            st = (int)Math.floor(Stroke);
            res[0]=ha;
            res[1]=st;
            return  res;


        }
        return res;
    }

}
