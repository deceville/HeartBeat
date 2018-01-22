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

    public ContentValues getGoals(List<Integer> goalSet) {
        this.height = goalSet.get(0);
        this.weight = goalSet.get(1);

        this.sbp = goalSet.get(2);
        this.dbp = goalSet.get(3);
        this.chl = goalSet.get(4);
        this.hdl = goalSet.get(5);

        ContentValues cv = new ContentValues();

        List<ContentValues> values = new ArrayList<ContentValues>();

        double bmi = weight / Math.pow((double) height / 100, 2);
        double BMIdescripancy;
        double normal = 18;
        double goalWeight;

        String cat = "normal";
        if (bmi >= 18 && bmi < 25) {
            cat = "normal";
            BMIdescripancy = bmi - normal;
            goalWeight =(double)weight - normal * (pow((double)height / 100, 2));
            cv.put("cat", cat);
            cv.put("BMIDescripancy", BMIdescripancy);
            System.out.println(goalWeight);
            cv.put("goalWeight", goalWeight);
            values.add(cv);
            return cv;
        } else if (bmi < 18) {
            cat = "underweight";
            BMIdescripancy = normal - bmi;
            goalWeight = normal * (pow((double)height / 100, 2)) - (double)weight ;
            cv.put("cat", cat);
            cv.put("BMIDescripancy", BMIdescripancy);
            System.out.println(goalWeight);
            cv.put("goalWeight", goalWeight);
            values.add(cv);
            return cv;
        } else if (bmi >= 25 && bmi <= 29) {
            cat = "overweight";
            BMIdescripancy = bmi - normal;
            goalWeight =(double)weight - normal * (pow((double)height / 100, 2));
            System.out.println(goalWeight);
            cv.put("cat", cat);
            cv.put("BMIDescripancy", BMIdescripancy);
            cv.put("goalWeight", goalWeight);
            values.add(cv);
            return cv;
        } else if (bmi >= 30) {
            cat = "obese";
            BMIdescripancy = bmi - normal;
            goalWeight = (double)weight - normal * (pow((double)height / 100, 2));
            System.out.println(goalWeight);
            cv.put("cat", cat);
            cv.put("BMIDescripancy", BMIdescripancy);
            cv.put("goalWeight", goalWeight);
            values.add(cv);
            return cv;
        }


        return cv;
    }


}
