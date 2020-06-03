package com.example.taskapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskapp.App;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.github.pinball83.maskededittext.MaskedEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    private MaskedEditText maskedEditText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String sms ;
    private String verificationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        maskedEditText = findViewById(R.id.editPhone);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone","onVerificationCompleted");
                String code = phoneAuthCredential.getSmsCode();
                sms = code;
                if(code!= null){
                    verifyCode(code);
                }

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone","onVerificationFailed"+e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationID = s;
                Log.e("Phone","onCodeSent");

            }
        };
    }
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signIn(credential);
    }

    private void showAlerts(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PhoneActivity.this);
        builder.setTitle("code ");
        final EditText editText = new EditText(PhoneActivity.this);
        builder.setView(editText);
        editText.setText(sms);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    String code = editText.getText().toString().trim();
                    if (code.isEmpty() || code.length() < 6) {
                        editText.requestFocus();
                        return;
                    }
                    verifyCode(code);
                startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                    finish();

            }
        });
        builder.show();
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(PhoneActivity.this,"Добро пожаловать", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e("Phone", "Error = "+ task.getException().getMessage());
                    Toast.makeText(PhoneActivity.this,"Ошибка авторизации", Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });
    }

    public void onClick(View view) {
        String phone = maskedEditText.getText().toString().trim();
        showAlerts();
        PhoneAuthProvider.getInstance().verifyPhoneNumber
                (phone,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks);


    }
}
