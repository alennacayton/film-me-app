package ph.com.filmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etname;
    private EditText etusername;
    private EditText etdescription;
    private EditText etpassword;
    private EditText etemail;
    private Button btupdate;
    private Button btcancel;
    private ProgressBar pbUpdate;


    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String userId;


    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Collections.users.name());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        this.initComponents();
        this.initFirebase();
    }

    private void initComponents(){
        this.etname = findViewById(R.id.et_name);
        this.etusername = findViewById(R.id.et_username);
        this.etdescription = findViewById(R.id.et_description);
        this.etpassword = findViewById(R.id.et_password);
        this.btupdate = findViewById(R.id.bt_update);
        this.pbUpdate = findViewById(R.id.pb_update);
        this.btcancel = findViewById(R.id.bt_cancel);
        this.etemail = findViewById(R.id.et_email);

        this.initFirebase();







        btupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v ) {
                String name = etname.getText().toString();
                String username = etusername.getText().toString();
                String description = etdescription.getText().toString();
                String password = etpassword.getText().toString();
                String email = etemail.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("name", name);
                hashMap.put("username", username);
                hashMap.put("description", description);
                hashMap.put("password", password);
                hashMap.put("email", email);


                etname.setText(name);

                reference.child(getuid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        pbUpdate.setVisibility(View.GONE);
                    }
                });
            }
        });




        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initFirebase(){
        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();
        this.userId = this.user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Collections.users.name());

        this.pbUpdate.setVisibility(View.VISIBLE);
        reference.child(this.userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                etname.setText(name);
                String username = snapshot.child("username").getValue().toString();
                etusername.setText(username);
                String description = snapshot.child("description").getValue().toString();
                etdescription.setText(description);
                String password = snapshot.child("password").getValue().toString();
                etpassword.setText(password);
                String email = snapshot.child("email").getValue().toString();
                etemail.setText(email);

                pbUpdate.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private String getuid() {
        return this.user.getUid();
    }



}