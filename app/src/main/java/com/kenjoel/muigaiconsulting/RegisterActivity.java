package com.kenjoel.muigaiconsulting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button mCreateButton;

    private String mEmail, mPassword, mPassConfirm;

    @BindView(R.id.createnewac)
    Button create;

    @BindView(R.id.editName)
    EditText name;

    @BindView(R.id.editEmail)
    EditText editEmail;

    @BindView(R.id.editPass)
    EditText editPassword;

    @BindView(R.id.confirmPass)
    EditText confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mCreateButton = create;


    }

    @Override
    public void onClick(View v) {

        if(v == mCreateButton ){

            mEmail = editEmail.getText().toString().trim();
            mPassword = editPassword.getText().toString().trim();
            mPassConfirm = confirm.getText().toString().trim();

            validateEmail(mEmail);
            validatePassword(mPassword);
            passwordMatch(mPassword, mPassConfirm);
        }

        mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                    mAuth.signOut();
                }
            }
        });

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

    public boolean passwordMatch(String s, String y){
        if (s.equals(y)){
            return true;
        }else {
            return false;
        }
    }
}