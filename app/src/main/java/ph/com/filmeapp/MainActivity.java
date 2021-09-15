package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnLogIn, btnSignUp;
    private EditText etemail;
    private EditText etpassword;
    private ProgressBar pblogin;

    private FirebaseAuth mAuth;
    private SharedPreferences sp;
    public static Boolean online;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnLogIn = findViewById(R.id.btn_login);
        this.btnSignUp = findViewById(R.id.btn_signup_lg);
        this.etemail = findViewById(R.id.pt_email_lg);
        this.etpassword = findViewById(R.id.pt_pw_lg);
        this.pblogin = findViewById(R.id.pb_login);


        //HomeActivity.logout = true;


        // button listeners
        this.initFireBase();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {

//put intent to go to mainActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);


            this.finish();

        }






        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etemail.getText().toString().trim();
                String password = etpassword.getText().toString().trim();

                if (!checkEmpty(email, password)){
                    signIn(email, password);
                }

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                MainActivity.online = true;
                finish();
            }
        });

       // retrieveData();
    }

    private boolean checkEmpty (String email, String password) {
        boolean isEmpty = false;
        if(email.isEmpty()) {
            this.etemail.setError("Required");
            this.etemail.requestFocus();
            isEmpty = true;
        } else if (password.isEmpty()) {
            this.etpassword.setError("Required");
            this.etpassword.requestFocus();
            isEmpty = true;
        }

        return isEmpty;
    }

    private void initFireBase() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    private void signIn (String email, String password) {
        this.pblogin.setVisibility(View.VISIBLE);
        this.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        boolean online;

                        if (task.isSuccessful()) {
                            loginSuccessful();

                        }else {
                            loginUnsuccessful ();

                        }
                    }
                });
    }

    private void loginUnsuccessful () {
        this.pblogin.setVisibility(View.GONE);
        Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();

    }

    private void loginSuccessful() {
        this.pblogin.setVisibility(View.GONE);
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        MainActivity.online = true;
        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    /*protected void onResume() {
        super.onResume();
        if (HomeActivity.logout == false)
        {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }*/

    private void retrieveData() {
        this.sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }





}