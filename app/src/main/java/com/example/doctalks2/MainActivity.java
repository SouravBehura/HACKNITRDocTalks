package com.example.doctalks2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private Button sign_up;
    private Button login;

    private RadioButton doctor;
    private RadioButton patient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctor = (RadioButton)findViewById(R.id.doctor_r);
        patient = (RadioButton)findViewById(R.id.patient_r);
        sign_up = (Button) findViewById(R.id.SU);
        login = (Button) findViewById(R.id.LI);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doctor.isChecked())
                {
                    Intent i = new Intent(MainActivity.this,DoctorSignUp.class);
                    startActivity(i);
                }
                else if(patient.isChecked())
                {
                    //Log.d("MainActivity.java","Heloo");
                    Intent intent1 = new Intent(MainActivity.this,PatientSignUp.class);
                    startActivity(intent1);
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doctor.isChecked())
                {
                    Intent i = new Intent(MainActivity.this,DoctorLogin.class);
                    startActivity(i);
                }
                else if(patient.isChecked())
                {
                    Intent i = new Intent(MainActivity.this,PatientLogin.class);
                    startActivity(i);
                }

            }
        });
    }
}
