package com.example.doctalks2;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PatientSignUp extends AppCompatActivity{

    private TextInputLayout patient_name;
    private TextInputLayout patient_email;
    private TextInputLayout patient_pass;
    private TextInputLayout patient_cpass;
    private Button pSU;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

        patient_name = (TextInputLayout)findViewById(R.id.patient_name);
        patient_email = (TextInputLayout)findViewById(R.id.p_email);
        patient_pass = (TextInputLayout)findViewById(R.id.p_password);
        patient_cpass = (TextInputLayout)findViewById(R.id.pc_password);
        pSU = (Button)findViewById(R.id.PatientSU);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        pSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id = patient_email.getEditText().getText().toString();
                String password = patient_pass.getEditText().getText().toString();
                String c_password = patient_cpass.getEditText().getText().toString();
                final String Name = patient_name.getEditText().getText().toString();
                if(TextUtils.isEmpty(email_id))
                {
                    Toast.makeText(PatientSignUp.this, "Please enter a email id...", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(PatientSignUp.this, "Please set a password...", Toast.LENGTH_LONG).show();
                }

                else if(TextUtils.isEmpty(Name)||TextUtils.isEmpty(c_password))
                {
                    Toast.makeText(PatientSignUp.this, "Please enter all the empty fields...", Toast.LENGTH_LONG).show();
                }


                else if(!(password.equals(c_password)))
                {
                    Toast.makeText(PatientSignUp.this, "Passwords do not match...",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressDialog.setMessage("Registering User. Please wait......");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email_id,password).addOnCompleteListener(PatientSignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(PatientSignUp.this, "Not Registered. Please try again..."+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Patients").child(user_id);
                                Map data = new HashMap();

                                data.put("Name",Name);

                                current_user_db.setValue(data);
                                Toast.makeText(PatientSignUp.this, "Registered Sucessfully. Redirecting you to Login page.....", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(PatientSignUp.this,PatientLogin.class);
                                startActivity(i);
                                finish();
                            }

                        }
                    });
                }

            }
        });
    }


}
