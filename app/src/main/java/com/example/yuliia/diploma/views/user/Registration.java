package com.example.yuliia.diploma.views.user;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.User;
import com.example.yuliia.diploma.navigation.Navigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth; //firebase auth
    private static final String TAG = "GoogleActivity";

    private FirebaseDatabase fDatabase;
    private DatabaseReference users;

    private Button mButton;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mNameField;
    private TextView mLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        users = fDatabase.getReference("users");

        mEmailField = findViewById(R.id.ar_input_email);
        mPasswordField = findViewById(R.id.ar_input_password);
        mNameField = findViewById(R.id.ar_input_name);

        mButton = findViewById(R.id.ar_btn_signup);
        mLink = findViewById(R.id.ar_link_login);

        mLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });
    }

    public void signUp(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            final User us = new User(mEmailField.getText().toString(), mNameField.getText().toString());
                            us.setPassword(password);

                            users.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(user.getUid()).exists()) {
                                        Toast.makeText(Registration.this, "existed", Toast.LENGTH_SHORT).show();

                                    } else {
                                        users.child(user.getUid()).setValue(us);
                                        Toast.makeText(Registration.this, "user is signed up", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Registration.this, Navigation.class);
                                        startActivity(intent);
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }
}

