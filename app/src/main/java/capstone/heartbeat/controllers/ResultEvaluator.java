package capstone.heartbeat.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import capstone.heartbeat.MainActivity;
import capstone.heartbeat.assessment.RiskResultsActivity;
import capstone.heartbeat.calculators.QStrokeFemale;
import capstone.heartbeat.calculators.QStrokeMale;
import capstone.heartbeat.calculators.Qrisk2Female;
import capstone.heartbeat.calculators.Qrisk2Male;
import capstone.heartbeat.models.Goal;
import capstone.heartbeat.others.AddPlanActivity;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.pow;

/**
 * Created by torre on 1/10/2018.
 */

public class ResultEvaluator extends AddPlanActivity {
    private double suggestedMet;
    private SharedPreferences pref;
    private int weight, height, sbp, dbp, chl, hdl;


    public ResultEvaluator() {
    }


    public double getSuggestedMet() {
        boolean giveSuggestion = false;
        pref = AddPlanActivity.prefs;
        int h = pref.getInt("normalHA", 0);
        int s = pref.getInt("normalST", 0);
        int ha = pref.getInt("haResult", 0);
        int st = pref.getInt("stResult", 0);
        int gender = pref.getInt("gender", 0);
        int age = pref.getInt("age", 0);

        int haDif = ha - h;
        int stDif = st - s;
        System.out.println(haDif + "  " + stDif + " " + s + ha + h);
        if (haDif > 0 || stDif > 0) {
            giveSuggestion = true;
            if (gender == 0) {
                if (age >= 25 && age <= 35) {
                    suggestedMet = 12;
                    return suggestedMet;
                } else if (age >= 36 && age <= 45) {
                    suggestedMet = 10;
                    return suggestedMet;
                } else if (age >= 46 && age <= 55) {
                    suggestedMet = 8;
                    return suggestedMet;
                } else if (age >= 56 && age <= 65) {
                    suggestedMet = 4;
                    return suggestedMet;
                } else if (age >= 66 && age <= 84) {
                    suggestedMet = 2;
                    return suggestedMet;
                } else {
                    suggestedMet = 1.5;
                    return suggestedMet;
                }

            } else if (gender == 1) {
                if (age >= 25 && age <= 35) {
                    suggestedMet = 20;
                    return suggestedMet;
                } else if (age >= 36 && age <= 45) {
                    suggestedMet = 15;
                    return suggestedMet;
                } else if (age >= 46 && age <= 55) {
                    suggestedMet = 10;
                    return suggestedMet;
                } else if (age >= 56 && age <= 65) {
                    suggestedMet = 6;
                    return suggestedMet;
                } else if (age >= 66 && age <= 84) {
                    suggestedMet = 3;
                    return suggestedMet;
                } else {
                    suggestedMet = 1.5;
                    return suggestedMet;
                }
            }
        } else {
            suggestedMet = 1.5;
            return suggestedMet;
        }

        return suggestedMet;
    }

    public Goal getBMIGoal(double weight,double height){

        double BMIdescripancy;
        double normal = 22.5;

        Goal myGoal = new Goal();

        String cat = "normal";
        double bmi = weight / Math.pow( height / 100, 2);
        double goalWeight = weight - normal * (pow(height / 100, 2));
        BMIdescripancy = bmi - normal;

        myGoal.setDescription("BMI");
        myGoal.setValue(goalWeight);
        myGoal.setCompleted(false);
        myGoal.setDuration("1 month");

        if ( bmi >= 18 && bmi < 25) {
            myGoal.setAction("reduce");
            return myGoal;
        } else if (bmi < 18) {
           goalWeight = normal * (pow(height / 100, 2))- weight;
           myGoal.setValue(goalWeight);
           myGoal.setAction("gain");
           return myGoal;

        } else if (bmi >= 25 && bmi < 30) {
            myGoal.setAction("reduce");
            return myGoal;
        } else if (bmi >= 30) {
            myGoal.setAction("reduce");
            return myGoal;
        }

        return myGoal;

    }

    public double getBMI(double weight,double height){
        double bmi = 0;
        bmi = weight / Math.pow( height / 100, 2);
        System.out.println(weight +", "+height+": "+bmi);
        return  bmi;
    }

    public Goal getCholGoal(double chol){
        Goal arvi_goal = new Goal();
        arvi_goal.setDuration("1 month");
        arvi_goal.setDescription("Total Cholesterol");
        arvi_goal.setCompleted(false);
        if (chol > 200){
            arvi_goal.setValue(chol - 200);
            arvi_goal.setAction("reduce");
            return arvi_goal;

        }else  {
            arvi_goal.setValue(chol);
            arvi_goal.setAction("maintain");
            return arvi_goal;
        }
    }

    public Goal getHDLGoal(double hdl){
        Goal arvi_goal = new Goal();
        arvi_goal.setDuration("1 month");
        arvi_goal.setDescription("HDL");
        arvi_goal.setCompleted(false);
        if (hdl < 60 ){
            arvi_goal.setValue(60-hdl);
            arvi_goal.setAction("gain");
            return arvi_goal;

        }else  {
            arvi_goal.setValue(hdl);
            arvi_goal.setAction("maintain");
            return arvi_goal;
        }
    }

    public Goal getBPGoal(double sbp,double dbp){
        Goal arvi_goal = new Goal();
        arvi_goal.setDuration("1 month");
        arvi_goal.setDescription("BP");
        arvi_goal.setCompleted(false);
        /*if (sbp > 120){
            arvi_goal.setValue(chol - 200);
            arvi_goal.setAction("reduce");
            return arvi_goal;

        }else  {
            arvi_goal.setValue(chol);
            arvi_goal.setAction("maintain");
            return arvi_goal;
        }*/
        return arvi_goal;
    }


    public double getNormalWeight(double bmi,double height){
        double weight = 0;
        weight  = 22.5 * Math.pow( height / 100, 2);
        System.out.println(weight +", "+height+": "+bmi);
        return  weight;
    }

}
