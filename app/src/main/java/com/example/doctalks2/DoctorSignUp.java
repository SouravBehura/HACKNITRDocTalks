package com.example.doctalks2;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class DoctorSignUp extends AppCompatActivity {

    private TextInputLayout doctor_name;
    private TextInputLayout doctor_license;
    private TextInputLayout doctor_email;
    private TextInputLayout doctor_pass;
    private TextInputLayout doctor_cpass;
    private Button dSU;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);

        doctor_name = (TextInputLayout)findViewById(R.id.doctor_name);
        doctor_license = (TextInputLayout)findViewById(R.id.dl_number);
        doctor_email = (TextInputLayout)findViewById(R.id.d_email);
        doctor_pass = (TextInputLayout)findViewById(R.id.d_password);
        doctor_cpass = (TextInputLayout)findViewById(R.id.dc_password);
        dSU = (Button)findViewById(R.id.DSU);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        dSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id = doctor_email.getEditText().getText().toString();
                String password = doctor_pass.getEditText().getText().toString();
                String c_password = doctor_cpass.getEditText().getText().toString();
                final String license_No = doctor_license.getEditText().getText().toString();
                final String Name = doctor_name.getEditText().getText().toString();
                if(TextUtils.isEmpty(email_id))
                {
                    Toast.makeText(DoctorSignUp.this, "Please enter a email id...", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(DoctorSignUp.this, "Please set a password...", Toast.LENGTH_LONG).show();
                }

                else if(TextUtils.isEmpty(license_No)||TextUtils.isEmpty(Name)||TextUtils.isEmpty(c_password))
                {
                    Toast.makeText(DoctorSignUp.this, "Please enter all the empty fields...", Toast.LENGTH_LONG).show();
                }


                else if(!(password.equals(c_password)))
                {
                    Toast.makeText(DoctorSignUp.this, "Passwords do not match...",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressDialog.setMessage("Registering User. Please wait......");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email_id,password).addOnCompleteListener(DoctorSignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(DoctorSignUp.this, "Not Registered. Please try again..."+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Doctors").child(user_id);
                                Map data = new HashMap();

                                data.put("Name",Name);
                                data.put("License_no",license_No);

                                current_user_db.setValue(data);
                                Toast.makeText(DoctorSignUp.this, "Registered Sucessfully. Redirecting you to Login page.....", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(DoctorSignUp.this,DoctorLogin.class);
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
