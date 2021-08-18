package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SignupActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText etName;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignup;
    private ProgressBar pbSignup;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.initFirebase();
        this.initComponents();

    }

    private void initFirebase() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    private void initComponents() {
        this.tvLogin = findViewById(R.id.tv_signup_login);
        this.etName = findViewById(R.id.pt_name_su);
        this.etUsername = findViewById(R.id.pt_us_su);
        this.etEmail = findViewById(R.id.pt_email);
        this.etPassword = findViewById(R.id.pt_pw_su);
        this.btnSignup = findViewById(R.id.btn_signup_su);
        this.pbSignup = findViewById(R.id.pb_signup);

        this.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent (SignupActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        this.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                String email = etEmail.getText().toString().trim();
                String password =  etPassword.getText().toString().trim();
                String name =  etName.getText().toString().trim();
                String username =  etUsername.getText().toString().trim();

                if(!checkEmpty(email, password, name,username)) {
                    // do something
                    //add user to db

                    User user = new User(email, password, name, username);
                    storeUser(user);
                }



            }
        });
    }


    private boolean checkEmpty (String email, String password, String name, String username) {
        boolean isEmpty = false;
        if(email.isEmpty()) {
            this.etEmail.setError("Required");
            this.etEmail.requestFocus();
            isEmpty = true;
        } else if (password.isEmpty()) {
            this.etPassword.setError("Required");
            this.etPassword.requestFocus();
            isEmpty = true;
        }  else if (name.isEmpty()) {
            this.etName.setError("Required");
            this.etName.requestFocus();
            isEmpty = true;
        } else if (username.isEmpty()) {
            this.etUsername.setError("Required");
            this.etUsername.requestFocus();
            isEmpty = true;
        }

        return isEmpty;
    }

    public void storeUser (User user) {
        this.pbSignup.setVisibility(View.VISIBLE);
        this.mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        signupSuccessful();
                    } else {
                        signupUnsuccessful();
                    }
                }
        });
    }

    private void signupUnsuccessful () {
        this.pbSignup.setVisibility(View.GONE);
        Toast.makeText(this, "Signup Unsuccessful", Toast.LENGTH_SHORT).show();
    }

    private void signupSuccessful() {
        this.pbSignup.setVisibility(View.GONE);
        Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}