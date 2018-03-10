package capstone.heartbeat.sidebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import capstone.heartbeat.R;
import capstone.heartbeat.controllers.ExpandableListAdapter;


public class FAQ_Activity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> faqlist;
    HashMap<String,List<String>> faqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_);

        expListView = (ExpandableListView)findViewById(R.id.lv_faqs);

        faqlist = new ArrayList<>();
        faqlist.add("What is Heart Beat?");
        faqlist.add("Who can use the Heart Beat application?");
        faqlist.add("What is a cardiovascular disease?");
        faqlist.add("What is Stroke?");
        faqlist.add("What is Heart Attack?");
        faqlist.add("What is HDL?");
        faqlist.add("What is LDL?");
        faqlist.add("How can Heart Beat help my heart health?");
        faqlist.add("What does gamification mean?");

        String answer1 = "Heart Beat is an application developed to predict the user's risk of having heart attack and stroke and to suggest lifestyle change activities to lessen the risk.";
        String answer2 = "Anyone who is curious of their risk of having stroke and heart attack with ages 24 to 85 years old";
        String answer3 = "Cardiovascular disease or CVD is a type of heart disease that involves the heart";
        String answer4 = "Stroke is a disease in which a blood flow to your brain is interrupted";
        String answer5 = "Heart attack is a disease in which a supply or flow of blood to the heart is blocked";
        String answer6 = "HDL or High-Density Lipids is  type of cholesterol known good. The higher the value of HDL the better";
        String answer7 = "LDL or Low-density Lipids is a lipoprotein of blood plasma that is composed of a moderate proportion of protein with little triglyceride and a high proportion of cholesterol. it is also known as bad cholesterol.";
        String answer8 = "Heart beat can help your heart health by providing you lifestyle changing activities based on your data from the assessment.";
        String answer9 = "Gamification means incorporating game mechanics into the application. Heart Beat is a gamified application since it incorporates the use of coins in prolonging the time of performing the activities. Users must buy coins in order to have more time to perform the activities. Heart Beat also incorporates rewards system in the application by the use of badges.";
        faqs = new HashMap<>();

        List<String> answers1 = new ArrayList<>();
        answers1.add(answer1);
        faqs.put(faqlist.get(0),answers1);

        List<String> answers2 = new ArrayList<>();
        answers2.add(answer2);
        faqs.put(faqlist.get(1),answers2);

        List<String> answers3 = new ArrayList<>();
        answers3.add(answer3);
        faqs.put(faqlist.get(2),answers3);

        List<String> answers4 = new ArrayList<>();
        answers4.add(answer4);
        faqs.put(faqlist.get(3),answers4);

        List<String> answers5 = new ArrayList<>();
        answers5.add(answer5);
        faqs.put(faqlist.get(4),answers5);

        List<String> answers6 = new ArrayList<>();
        answers6.add(answer6);
        faqs.put(faqlist.get(5),answers6);


        List<String> answers7 = new ArrayList<>();
        answers7.add(answer7);
        faqs.put(faqlist.get(6),answers7);

        List<String> answers8 = new ArrayList<>();
        answers8.add(answer8);
        faqs.put(faqlist.get(7),answers8);

        List<String> answers9 = new ArrayList<>();
        answers9.add(answer9);
        faqs.put(faqlist.get(8),answers9);


        listAdapter = new ExpandableListAdapter(getApplicationContext(), faqlist, faqs);

        expListView.setAdapter(listAdapter);
    }
}
