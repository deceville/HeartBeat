package capstone.heartbeat.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import capstone.heartbeat.MainActivity;
import capstone.heartbeat.assessment.RiskResultsActivity;
import capstone.heartbeat.calculators.QStrokeFemale;
import capstone.heartbeat.calculators.QStrokeMale;
import capstone.heartbeat.calculators.Qrisk2Female;
import capstone.heartbeat.calculators.Qrisk2Male;
import capstone.heartbeat.models.Activity;
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
    DecimalFormat df;
    Context c;

    public ResultEvaluator(Context c) {
        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.c = c;
    }



    public double getSuggestedMet() {
        boolean giveSuggestion = false;
        pref = c.getSharedPreferences("values",MODE_PRIVATE);
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

    public String getActCategory(){
        double met = getSuggestedMet();
        String cat = null;

        if (met <= 3  ){
            cat = "light";
            return cat;
        }else if (met <= 6 ){
            cat = "light to moderate";
            return cat;
        }else{
            cat = "light to vigorous";
            return cat;
        }


    };

    public Goal getBMIGoal(double weight,double height){

        double BMIdescripancy;
        double normal = 22.5;

        Goal myGoal = new Goal();

        String cat = "normal";
        double bmi = weight / Math.pow( height / 100, 2);
        double goalWeight = weight - normal * (pow(height / 100, 2));
        BMIdescripancy = bmi - normal;

        myGoal.setDescription("BMI");
        myGoal.setValue(Double.parseDouble(df.format(goalWeight)));
        myGoal.setCompleted(false);
        myGoal.setDuration("1 month");

        if ( bmi >= 18 && bmi < 25) {
            myGoal.setAction("reduce");
            return myGoal;
        } else if (bmi < 18) {
           goalWeight = normal * (pow(height / 100, 2))- weight;
           myGoal.setValue(Double.parseDouble(df.format(goalWeight)));
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

    public Goal getLDLGoal(double ldl){
        Goal arvi_goal = new Goal();
        arvi_goal.setDuration("1 month");
        arvi_goal.setDescription("LDL");
        arvi_goal.setCompleted(false);
        if (ldl > 100){
            arvi_goal.setValue(ldl - 100);
            arvi_goal.setAction("reduce");
            return arvi_goal;

        }else  {
            arvi_goal.setValue(ldl);
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

    public Goal getSBPGoal(double sbp){
        Goal arvi_goal = new Goal();
        arvi_goal.setDuration("1 month");
        arvi_goal.setDescription("SBP");
        arvi_goal.setCompleted(false);
         if(sbp > 120){
            arvi_goal.setValue(sbp - 120);
            arvi_goal.setAction("reduce");
            return arvi_goal;

        }else  {
            arvi_goal.setValue(sbp);
            arvi_goal.setAction("maintain");
            return arvi_goal;
        }

    }

    public Goal getDBPGoal(double dbp){
        Goal arvi_goal = new Goal();
        arvi_goal.setDuration("1 month");
        arvi_goal.setDescription("DBP");
        arvi_goal.setCompleted(false);
        if(dbp > 80){
            arvi_goal.setValue(dbp - 80);
            arvi_goal.setAction("reduce");
            return arvi_goal;

        }else  {
            arvi_goal.setValue(dbp);
            arvi_goal.setAction("maintain");
            return arvi_goal;
        }

    }

    public  double getCalorie(double weight,double mets){
        double cal = ((mets*3.5*weight)/200)*15;
        return cal;
    }
    public  double getWeightEquivalent(double mets,double weight){
        double cal = getCalorie(weight,mets) * 15;
        double cals =  (cal/3500);
        double weigh = 453.59237*cals;
        return weigh;

    }


    public double getNormalWeight(double bmi,double height){
        double weight = 0;
        weight  = 22.5 * Math.pow( height / 100, 2);
        System.out.println(weight +", "+height+": "+bmi);
        return  weight;
    }

    public int getAge(String birth){
        return 0;
    }

    public String getBMICat(double bmi){
        String category= null;

        if(bmi>=18.5&&bmi<25){
            category = "healthy";
        }else if(bmi<18.5){
            category = "underweight";
        }else if(bmi>=25 && bmi <30){
            category = "overweight";
        }else if(bmi>30){
            category = "Obese";
        }
        return  category;
    }

    //getDays
    public double getGoalDays(List<Activity> activities){
         double days =0 ;
         double total = 0;
         Goal g = getBMIGoal(weight,height);
         double goal=g.getValue()*1000;
        for (Activity a:activities
             ) {
            total = total + getWeightEquivalent(a.getWeightLoss(),weight)*15;
        }

        days = goal/total;
        return days;
    }
}
