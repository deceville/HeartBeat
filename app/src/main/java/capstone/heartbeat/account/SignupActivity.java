package capstone.heartbeat.account;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import capstone.heartbeat.MainActivity;
import capstone.heartbeat.R;
import capstone.heartbeat.assessment.DemographicsActivity;
import capstone.heartbeat.controllers.HeartBeatDB;
import capstone.heartbeat.models.Bank;

/**
 * Created by Lenevo on 11/2/2017.
 */

public class SignupActivity extends AppCompatActivity {

    private EditText txtName,txtPassword,txtPassConfirm;
    private Button btnSignup;
    private CheckBox cbCondition;
    private AutoCompleteTextView txtUsername;
    private FirebaseAuth mAuth;
    private String username,name,pass,passconfirm;
    private DatabaseReference rootRef, userRef,account;
    private int counter;
    private boolean conditionCheck;
    private TextView login;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //database reference
        rootRef  = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("Users");

        //binding views
        txtUsername = (AutoCompleteTextView) findViewById(R.id.username);
        txtName = (EditText)findViewById(R.id.fullname);
        txtPassword =(EditText)findViewById(R.id.password);
        txtPassConfirm =(EditText)findViewById(R.id.confirmPassowrd);
        btnSignup = (Button)findViewById(R.id.email_sign_up_button);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = txtUsername.getText().toString();
                name = txtName.getText().toString();
                pass = txtPassword.getText().toString();
                passconfirm = txtPassConfirm.getText().toString();

                attemptSignup();


            }
        });

        login = (TextView) findViewById(R.id.login_frmSignUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });


    }

    private void attemptSignup() {

        /*if (mAuthTask != null) {
            return;

        }*/

        // Reset errors.
        txtName.setError(null);
        txtUsername.setError(null);
        txtPassConfirm.setError(null);
        txtPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPass = txtPassConfirm.getText().toString();
        String fullname = txtName.getText().toString();
        cbCondition = (CheckBox)findViewById(R.id.check);

        boolean cancel = false;
        View focusView = null;

        boolean usernameCheck = !TextUtils.isEmpty(username) && isUsernameValid(username);
        boolean passwordCheck = !TextUtils.isEmpty(password) && isPasswordValid(password);
        boolean confirmPassCheck = !TextUtils.isEmpty(confirmPass) && isPasswordMatched(password,confirmPass);
        boolean fullnameCheck = !TextUtils.isEmpty(fullname) && isFullNameValid(fullname);
        conditionCheck = false;


        if (cbCondition.isChecked()){
            conditionCheck = true;
        }else{
            conditionCheck = false;
        }


        if(usernameCheck && confirmPassCheck && passwordCheck && fullnameCheck && conditionCheck){
            /*mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            userRef.push().setValue(email);
                            userRef.push().setValue(username);
                            userRef.push().setValue(name);
                            userRef.push().setValue(pass);
                            getDatabaseCount();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"An error occurred",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
*/
            HeartBeatDB db = new HeartBeatDB(getApplicationContext());
            db.open();
            db.insertUser(username,name,pass);
            getDatabaseCount();
            System.out.println("Success");
            id = db.getUserID(username);

            final Dialog dialog2 = new Dialog(SignupActivity.this);
            dialog2.setContentView(R.layout.coins_dialog);

            Button btn_thank = (Button) dialog2.findViewById(R.id.btn_thank);
            TextView txt_coin = (TextView) dialog2.findViewById(R.id.txt_coin);
            TextView txt_coin_desc = (TextView) dialog2.findViewById(R.id.txt_coin_desc);

            txt_coin.setText(String.format("%d", 20));
            txt_coin_desc.setText("You have earned 20 coins for signing up!");
            db.addCoins(id,20);
            db.close();
            dialog2.show();

            btn_thank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog2.dismiss();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            });
        }else{

            if(TextUtils.isEmpty(confirmPass)){
                txtPassConfirm.setError(getString(R.string.error_field_required));
                focusView = txtPassConfirm;
                cancel = true;
            }else if (!isPasswordMatched(password, confirmPass)) {
                txtPassConfirm.setError(getString(R.string.error_password_confirm));
                focusView = txtPassConfirm;
                cancel = true;
            }

            // Check for a valid password, if the user entered one.
            if(TextUtils.isEmpty(password)){
                txtPassword.setError(getString(R.string.error_field_required));
                focusView = txtPassword;
                cancel = true;
            }else if (!isPasswordValid(password)) {
                txtPassword.setError(getString(R.string.error_invalid_password));
                focusView = txtPassword;
                cancel = true;
            }

            if (TextUtils.isEmpty(fullname)) {
                txtName.setError(getString(R.string.error_field_required));
                focusView = txtName;
                cancel = true;
            } else if (!isFullNameValid(name)) {
                txtName.setError(getString(R.string.error_invalid_fullname));
                focusView = txtName;
                cancel = true;
            }

            // Check for a valid username
            if (TextUtils.isEmpty(username)) {
                txtUsername.setError(getString(R.string.error_field_required));
                focusView = txtUsername;
                cancel = true;
            } else if (!isUsernameValid(username)) {
                txtUsername.setError(getString(R.string.error_invalid_username));
                focusView = txtUsername;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } /*else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            // mAuthTask.execute((Void) null);

            HeartBeatDB db = new HeartBeatDB(getApplicationContext());
            db.open();
            boolean exists = db.checkUser(username,password);
            int id = db.getUserID(username);
            if (exists){
                System.out.println(exists+"");
                prefs = getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("session",1);
                editor.putInt("id",id);
                editor.commit();
                boolean isCalculated = prefs.getBoolean("isCalculated",false);
                if (isCalculated){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(), DemographicsActivity.class));
                    finish();
                }
            }else{
                System.out.println(exists+"");
                showProgress(false);

            }
            db.close();
            mAuth = FirebaseAuth.getInstance();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {



                    if (task.isSuccessful()){
                        prefs = getSharedPreferences("login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("session",1);
                        editor.commit();
                        boolean isCalculated = prefs.getBoolean("isCalculated",false);
                        if (isCalculated){
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(getApplicationContext(), DemographicsActivity.class));
                            finish();
                        }
                    }else{
                        showProgress(false);

                    }
                }
            });

        }*/
        }


    }

    public void getDatabaseCount(){
        rootRef  = FirebaseDatabase.getInstance().getReference();
        final int[] count = {0};
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Log.e(snap.getKey(), snap.getChildrenCount() + " ");
                   // count[0] =(int) snap.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //return count[0];
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 4 && !username.contains(" ");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z]).{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    private boolean isPasswordMatched(String password, String confirmPass){
        if(password.equals(confirmPass)){
            return true;
        }else{
            return false;
        }
    }

    private boolean isFullNameValid(String fullname){
        return fullname.matches("^([A-Za-z]+)(\\s[A-Za-z]+)*\\s?$");
    }

}
