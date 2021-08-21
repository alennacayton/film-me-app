package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnLogIn, btnSignUp;
    private EditText etemail;
    private EditText etpassword;
    private ProgressBar pblogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnLogIn = findViewById(R.id.btn_login);
        this.btnSignUp = findViewById(R.id.btn_signup_lg);
        this.etemail = findViewById(R.id.pt_email_lg);
        this.etpassword = findViewById(R.id.pt_pw_lg);
        this.pblogin = findViewById(R.id.pb_login);


        // button listeners

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etemail.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                signIn(email, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}