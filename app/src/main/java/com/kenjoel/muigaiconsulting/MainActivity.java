package com.kenjoel.muigaiconsulting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mLoginButton;
    private TextView mCreatetxt;
    private String mEmail, mPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @BindView(R.id.btnlogin) Button Login;
    @BindView(R.id.createnewac) TextView createtxt;
    @BindView(R.id.etemail) EditText etEmail;
    @BindView(R.id.mypass) EditText myPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoginButton = Login;
        mCreatetxt = createtxt;
        mLoginButton.setOnClickListener(this);
        mCreatetxt.setOnClickListener(this);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                if(mUser != null){
                    Intent intent = new Intent();
                    startActivity(intent);
                    finish();
                }

            }
        };



        // Enables Always-on
    }

    @Override
    public void onClick(View v) {
        if(v == mLoginButton){
            mEmail = etEmail.getText().toString().trim();
            mPassword = myPass.getText().toString().trim();
            validateEmail(mEmail);
            validatePassword(mPassword);

            mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Toast.makeText(MainActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Sorry wrong info", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(v == mCreatetxt){
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

    }

    public boolean validateEmail(String email){
        if(email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validatePassword(String Password){
        if(Password.length() < 6){
            return false;
        }else {
            return true;
        }
    }
}