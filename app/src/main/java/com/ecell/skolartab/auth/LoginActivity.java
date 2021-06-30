package com.ecell.skolartab.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ecell.skolartab.Dashboard;
import com.ecell.skolartab.R;
import com.ecell.skolartab.SplashActivity;
import com.ecell.skolartab.customized.CustomSnacks;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout tilEmail,tilPassword;
    Button loginBtn;
    TextView signupToggle,forgotPassBtn;
    CustomSnacks customSnacks;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login_btn);
        signupToggle = findViewById(R.id.signup_user_toggle);
        forgotPassBtn = findViewById(R.id.forgot_pass);
        customSnacks = new CustomSnacks();

        firebaseAuth = FirebaseAuth.getInstance();

        tilEmail = findViewById(R.id.mail_input);
        tilPassword = findViewById(R.id.password_input);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()){
                    final String email = tilEmail.getEditText().getText().toString().trim();
                    final String password = tilPassword.getEditText().getText().toString().trim();
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.i("LOGIN :: ","SUCCESS");
                                Intent dashboard_intent  = new Intent(LoginActivity.this, Dashboard.class);
                                startActivity(dashboard_intent);
                                finish();
                            }
                            else {
                                Log.i("LOGIN :: ","FAILED");
                                customSnacks.failSnack(view,"Wrong Credentials!");
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validateData() {
        final String email,password;
        View view = findViewById(android.R.id.content);

        email = tilEmail.getEditText().getText().toString().trim();
        password = tilPassword.getEditText().getText().toString().trim();

        if (email.isEmpty()){
            customSnacks.warnSnack(view,"Email can't be Empty!");
            tilEmail.getEditText().requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            customSnacks.warnSnack(view,"Provide a Valid Email!");
            tilEmail.getEditText().requestFocus();
            return false;
        }
        else if (password.isEmpty()){
            customSnacks.warnSnack(view,"Password can't be Empty!");
            tilPassword.getEditText().requestFocus();
            return false;
        }
        else if (password.length()<5){
            customSnacks.warnSnack(view,"Provide a Valid Password!");
            tilPassword.getEditText().requestFocus();
            return false;
        }
        else return true;
    }
}