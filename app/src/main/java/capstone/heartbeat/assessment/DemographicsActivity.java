package capstone.heartbeat.assessment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.controllers.PickerAdapter;
import capstone.heartbeat.R;
import capstone.heartbeat.models.ScaleView;
import capstone.heartbeat.onViewUpdateListener;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;

import static android.view.View.VISIBLE;
import static android.view.View.GONE;

public class DemographicsActivity extends AppCompatActivity {
    public Button btn_birthdate, btn_female, btn_male;
    private TextView txtValue;
    public ScaleView rulerViewMm;
    public ImageView avatar;
    RecyclerView rv;
    PickerAdapter adapter;

    public float height;

    public int age, currYear, currMonth, currDay;
    boolean selected = false;
    int male = 0;
    int female = 0;
    int gender ,uid,free;
    public String weight;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    int check = 3;
    boolean alldone = false;
    private boolean quest_bp = false;
    private boolean quest_total = false;
    private boolean quest_hdl = false;

    int singleQuest = 10;
    int allQuest = singleQuest*3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographics);

        prefs = getSharedPreferences("values",MODE_PRIVATE);
        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        editor = prefs.edit();
        btn_birthdate = (Button)findViewById(R.id.btn_birthdate);
        btn_female = (Button)findViewById(R.id.btn_female);
        btn_male = (Button)findViewById(R.id.btn_male);
        avatar = (ImageView) findViewById(R.id.avatar);

        uid=pref.getInt("id",1);

        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = true;
                female = 1;
                male = 0;
                gender = 0;
                editor.putInt("Gender", gender);
                avatar.setImageResource(R.drawable.char_undefined);

                if(selected && (male == 1 || female == 0)){
                    btn_female.setTextColor(getResources().getColor(android.R.color.black));
                    btn_male.setTextColor(getResources().getColor(android.R.color.white));
                    btn_male.setBackgroundColor(getResources().getColor(R.color.bg_screen2));
                    btn_female.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }else if(selected && (female == 1 || male == 0)){
                    btn_female.setTextColor(getResources().getColor(android.R.color.white));
                    btn_male.setTextColor(getResources().getColor(android.R.color.black));
                    btn_female.setBackgroundColor(getResources().getColor(R.color.bg_screen3));
                    btn_male.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }
                if(age != 0){
                    setFemaleCharacter(age);
                }else{
                    avatar.setImageResource(R.drawable.char_undefined);
                }
            }
        });

        btn_male.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                selected = true;
                female = 0;
                male = 1;
                gender = 1;
                editor.putInt("Gender", gender);

                if(selected && (male == 1 || female == 0)){
                    btn_female.setTextColor(getResources().getColor(android.R.color.black));
                    btn_male.setTextColor(getResources().getColor(android.R.color.white));
                    btn_male.setBackgroundColor(getResources().getColor(R.color.bg_screen2));
                    btn_female.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }else if(selected && (female == 1 || male == 0)){
                    btn_female.setTextColor(getResources().getColor(android.R.color.white));
                    btn_male.setTextColor(getResources().getColor(android.R.color.black));
                    btn_female.setBackgroundColor(getResources().getColor(R.color.bg_screen3));
                    btn_male.setBackgroundColor(getResources().getColor(R.color.progress_gray));
                }
                if(age != 0){
                    setMaleCharacter(age);
                }else{
                    avatar.setImageResource(R.drawable.char_undefined);
                }
            }
        });


        btn_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                currDay = c.get(Calendar.DAY_OF_MONTH);
                currMonth = c.get(Calendar.MONTH);
                currYear = c.get(Calendar.YEAR);
                Calendar maxDate = Calendar.getInstance();
                maxDate.set(Calendar.YEAR, 1993);
                maxDate.set(Calendar.MONTH, 11);
                maxDate.set(Calendar.DAY_OF_MONTH, 31);
                Calendar minDate = Calendar.getInstance();
                minDate.set(Calendar.YEAR, 1934);
                minDate.set(Calendar.MONTH, 0);
                minDate.set(Calendar.DAY_OF_MONTH, 1);
                DatePickerDialog dialog = new DatePickerDialog(DemographicsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        new mDateSetListener(), currYear, currMonth, currDay){
                    @Override
                    public void onCreate(Bundle savedInstanceState)
                    {
                        super.onCreate(savedInstanceState);
                        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                };
                dialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                dialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                dialog.show();
            }
        });



        final ScaleView rulerViewMm = (ScaleView) findViewById(R.id.my_scale);
        txtValue = (TextView) findViewById(R.id.txt_height);
        rulerViewMm.setStartingPoint(150f);
        rulerViewMm.setUpdateListener(new onViewUpdateListener() {

            @Override
            public void onViewUpdate(float result) {
                height = (float) Math.round(result * 10f) / 10f;
                editor.putInt("height",(int)height);
                txtValue.setText(height + " cm");
            }
        });

        rv = (RecyclerView) findViewById(R.id.rv);

        PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        pickerLayoutManager.setScaleDownBy(0.99f);
        pickerLayoutManager.setScaleDownDistance(0.8f);

        adapter = new PickerAdapter(this, getData(200), rv);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        rv.setLayoutManager(pickerLayoutManager);
        rv.setAdapter(adapter);

        pickerLayoutManager.setOnScrollStopListener(new PickerLayoutManager.onScrollStopListener() {
            @Override
            public void selectedView(View view) {
                weight = ((TextView)view).getText().toString();
                weight = weight.substring(0,weight.length()-3);
                int weigh = Integer.parseInt(weight);
                editor.putInt("weight", weigh);

            }
        });
    }

    public List<String> getData(int count) {
        List<String> data = new ArrayList<>();
        for (int i = 30; i < count; ++i) {
            data.add(String.valueOf(i));
        }
        return data;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.next_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up next_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            editor.commit();

            //Display the dialog
            final Dialog dialog = new Dialog(DemographicsActivity.this, android.R.style.Theme_Material_Light_Dialog);
            dialog.setTitle("Confirmation");
            dialog.setContentView(R.layout.confirm_dialog);

            dialog.show();

            Button confirm_proceed = (Button) dialog.findViewById(R.id.confirm_proceed);
            Button confirm_cancel = (Button) dialog.findViewById(R.id.confirm_cancel);
            Switch sw_bp = (Switch) dialog.findViewById(R.id.sw_bp) ;
            Switch sw_hdl = (Switch) dialog.findViewById(R.id.sw_total) ;
            Switch sw_total = (Switch) dialog.findViewById(R.id.sw_hdl) ;
            final int[] coins = {0};

            final int coin = prefs.getInt("coin",0);

            sw_bp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    quest_bp = isChecked;
                    if (isChecked) {
                        coins[0] = coins[0] + singleQuest;
                        editor.putInt("coin",coins[0]);
                        editor.commit();
                    }
                    else{ coins[0] = coins[0] -singleQuest;
                        editor.putInt("coin",coins[0]);
                        editor.commit();
                    }
                }
            });

            sw_total.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    quest_total = isChecked;
                    if (isChecked) {
                        coins[0] = coins[0] + singleQuest;
                        editor.putInt("coin",coins[0]);
                        editor.commit();
                    }
                    else{ coins[0] = coins[0] -singleQuest;
                        editor.putInt("coin",coins[0]);
                        editor.commit();
                    }
                }
            });

            sw_hdl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    quest_hdl = isChecked;
                    if (isChecked) {
                        coins[0] = coins[0] + singleQuest;
                        editor.putInt("coin",coins[0]);
                        editor.commit();
                    }
                    else{ coins[0] = coins[0] -singleQuest;
                        editor.putInt("coin",coins[0]);
                        editor.commit();
                    }
                }
            });

            confirm_proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    final Dialog d = new Dialog(DemographicsActivity.this, android.R.style.Theme_Material_Light_Dialog);
                    d.setTitle("Quests");
                    d.setContentView(R.layout.all_quest_dialog);

                    d.show();

                    final View viewGroup_bp = d.findViewById(R.id.viewGroup_bp);
                    final View viewGroup_total = d.findViewById(R.id.viewGroup_total);
                    final View viewGroup_hdl = d.findViewById(R.id.viewGroup_hdl);

                    System.out.println(uid);
                    Button quest_done = (Button) d.findViewById(R.id.quest_done);
                    Button quest_back = (Button) d.findViewById(R.id.quest_back);

                    if(quest_hdl && quest_bp && quest_total){
                        HeartBeatDB db = new HeartBeatDB(getApplicationContext());
                        db.open();
                        db.setInitialPoints(uid,0,allQuest, 0);
                        db.close();
                        d.dismiss();
                        startActivity(new Intent(DemographicsActivity.this, LaboratoryActivity.class));
                    }else{
                        if(quest_bp){
                            viewGroup_bp.setVisibility(GONE);
                        }

                        if(quest_total){
                            viewGroup_total.setVisibility(GONE);
                        }

                        if(quest_hdl){
                            viewGroup_hdl.setVisibility(GONE);
                        }
                    }

                    viewGroup_bp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog1 = new Dialog(DemographicsActivity.this, android.R.style.Theme_Material_Light_Dialog);
                            dialog1.setTitle("Blood Pressure Quest");
                            dialog1.setContentView(R.layout.quest_bp);

                            dialog1.show();

                            Button bp_okay = (Button) dialog1.findViewById(R.id.bp_okay);
                            Button bp_cancel = (Button) dialog1.findViewById(R.id.bp_cancel);

                            bp_okay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showCoinDialog(dialog1, singleQuest);
                                    viewGroup_bp.setVisibility(GONE);
                                }
                            });

                            bp_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });
                        }
                    });

                    viewGroup_total.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog1 = new Dialog(DemographicsActivity.this, android.R.style.Theme_Material_Light_Dialog);
                            dialog1.setTitle("Total Cholesterol Quest");
                            dialog1.setContentView(R.layout.quest_total);

                            dialog1.show();

                            Button total_okay = (Button) dialog1.findViewById(R.id.total_okay);
                            Button total_cancel = (Button) dialog1.findViewById(R.id.total_cancel);

                            total_okay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showCoinDialog(dialog1, singleQuest);
                                    viewGroup_total.setVisibility(GONE);
                                }
                            });

                            total_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });
                        }
                    });

                    viewGroup_hdl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog1 = new Dialog(DemographicsActivity.this, android.R.style.Theme_Material_Light_Dialog);
                            dialog1.setTitle("HDL Cholesterol Quest");
                            dialog1.setContentView(R.layout.quest_hdl);

                            dialog1.show();

                            Button hdl_okay = (Button) dialog1.findViewById(R.id.hdl_okay);
                            Button hdl_cancel = (Button) dialog1.findViewById(R.id.hdl_cancel);

                            hdl_okay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showCoinDialog(dialog1, singleQuest);
                                    viewGroup_hdl.setVisibility(GONE);
                                }
                            });

                            hdl_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });
                        }
                    });

                    quest_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alldone = true;
                            showCoinDialog(d, allQuest);
                        }
                    });

                    quest_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                            dialog.show();
                        }
                    });
                }
            });

            confirm_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            StringBuilder birth =  new StringBuilder();
            birth.append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" ");
            String birthday = birth.toString();
            btn_birthdate.setText(birth);
            age = currYear - mYear;
            editor.putString("birth",birthday);
            editor.putInt("age",age);
            editor.commit();
            System.out.println(btn_birthdate.getText().toString() + "Age: " + age);
            if(age > 84 || age < 25){
                alertAgeLimit();
            }

            if(male == 1 && female == 0){
                setMaleCharacter(age);
            }else if(female == 1 && male == 0){
                setFemaleCharacter(age);
            }
        }
    }

    public void setMaleCharacter (int age){

        avatar = (ImageView) findViewById(R.id.avatar);

        if(age >= 25 && age <= 35){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_male_01);
        }else if (age <= 45 && age >= 36){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_male_02);
        }else if (age <= 55 && age >= 46){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_male_03);
        }else if (age <= 70 && age >= 56){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_male_04);
        }else if (age <= 84 && age >= 71){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_male_05);
        }else{
            avatar.setVisibility(View.INVISIBLE);
        }

    }

    public void setFemaleCharacter (int age){

        avatar = (ImageView) findViewById(R.id.avatar);
        if(age >= 25 && age <= 35){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_female_01);
        }else if (age <= 45 && age >= 36){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_female_02);
        }else if (age <= 55 && age >= 46){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_female_03);
        }else if (age <= 70 && age >= 56){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_female_04);
        }else if (age <= 84 && age >= 71){
            avatar.setVisibility(VISIBLE);
            avatar.setImageResource(R.drawable.char_female_03);
        }else{
            avatar.setVisibility(View.INVISIBLE);
        }

    }

    public void alertAgeLimit (){
        AlertDialog.Builder builder = new AlertDialog.Builder(DemographicsActivity.this, R.style.Theme_AppCompat_Dialog_Alert);

        builder.setTitle("Oh no!")
                .setMessage("Your current age is not allowed. Ages only between 25 and 84 are allowed to continue.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        btn_birthdate.setText("MONTH / DAY / YEAR");
                        btn_birthdate.setTextColor(getResources().getColor(R.color.progress_gray_trans));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean alertHeightLimit (){
        AlertDialog.Builder builder = new AlertDialog.Builder(DemographicsActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
        final ScaleView rulerViewM = (ScaleView) findViewById(R.id.my_scale);
        builder.setTitle("Oh no!")
                .setMessage("Your maximum height is 300cm.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       rulerViewM.setStartingPoint(160f);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog alert = builder.create();
        alert.show();
        return true;
    }

    public void showCoinDialog(final Dialog d, int coins){
        final Dialog dialog2 = new Dialog(DemographicsActivity.this);
        dialog2.setContentView(R.layout.coins_dialog);

        Button btn_thank = (Button) dialog2.findViewById(R.id.btn_thank);
        TextView txt_coin = (TextView) dialog2.findViewById(R.id.txt_coin);
        TextView txt_coin_desc = (TextView) dialog2.findViewById(R.id.txt_coin_desc);

        txt_coin.setText(String.format("%d", coins));
        txt_coin_desc.setText("You have earned " + coins + " coins for completing this quest!");
        dialog2.show();

        btn_thank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                dialog2.dismiss();
                check -= 1;
                if(check == 0){
                    startActivity(new Intent(DemographicsActivity.this, LaboratoryActivity.class));
                }

                if(alldone){
                    startActivity(new Intent(DemographicsActivity.this, LaboratoryActivity.class));
                }
            }
        });
    }
}
