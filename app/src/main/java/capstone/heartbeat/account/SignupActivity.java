package capstone.heartbeat.account;

import android.app.Dialog;
import android.content.Intent;
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

import capstone.heartbeat.R;
import capstone.heartbeat.controllers.HeartBeatDB;

/**
 * Created by Lenevo on 11/2/2017.
 */

public class SignupActivity extends AppCompatActivity {

    private EditText txtName,txtEmail,txtPassword,txtPassConfirm;
    private Button btnSignup;
    private CheckBox cbCondition;
    private AutoCompleteTextView txtUsername;
    private FirebaseAuth mAuth;
    private String username,name,pass,passconfirm;
    private DatabaseReference rootRef, userRef,account;
    private int counter;
    private TextView login;

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
        cbCondition =(CheckBox)findViewById(R.id.check);

        btnSignup.setEnabled(false);



        cbCondition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    btnSignup.setEnabled(true);
                }else btnSignup.setEnabled(false);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = txtUsername.getText().toString();
                name = txtName.getText().toString();
                pass = txtPassword.getText().toString();
                passconfirm = txtPassConfirm.getText().toString();


                if(TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(),"Enter username!",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),"Enter password!",Toast.LENGTH_SHORT).show();
                }

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
                db.close();
                final Dialog dialog2 = new Dialog(SignupActivity.this);
                dialog2.setContentView(R.layout.coins_dialog);

                Button btn_thank = (Button) dialog2.findViewById(R.id.btn_thank);
                TextView txt_coin = (TextView) dialog2.findViewById(R.id.txt_coin);
                TextView txt_coin_desc = (TextView) dialog2.findViewById(R.id.txt_coin_desc);

                txt_coin.setText(String.format("%d", 20));
                txt_coin_desc.setText("You have earned 20 coins for signing up!");
                dialog2.show();

                btn_thank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                });
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

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

   public void attemptSignup(){

   }


}
