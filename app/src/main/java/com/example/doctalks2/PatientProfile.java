package com.example.doctalks2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientProfile extends AppCompatActivity {

    private TextInputLayout pp_name;
    private TextInputLayout pp_age;
    private TextInputLayout pp_sex;
    private TextInputLayout pp_symptoms;
    private TextInputLayout pp_diagnosis;
    private TextInputLayout pp_prescription;
    private TextInputLayout pp_advice;
    private Button btn_sp;
    private TextInputLayout ref_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        pp_name = findViewById(R.id.pp_name);
        pp_age = findViewById(R.id.pp_age);
        pp_sex = findViewById(R.id.pp_sex);
        pp_symptoms = findViewById(R.id.pp_symptoms);
        pp_diagnosis = findViewById(R.id.pp_diagnosis);
        pp_prescription = findViewById(R.id.pp_prescription);
        pp_advice = findViewById(R.id.pp_advice);
        ref_no = findViewById(R.id.ref_no);

        btn_sp = (Button)findViewById(R.id.btn_sp);

        btn_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ref = ref_no.getEditText().getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("prescriptions").document(ref);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            pp_name.getEditText().setText(documentSnapshot.getString("name"));
                            pp_age.getEditText().setText(documentSnapshot.getString("age"));
                            pp_sex.getEditText().setText(documentSnapshot.getString("sex"));
                            pp_symptoms.getEditText().setText(documentSnapshot.getString("symptoms"));
                            pp_diagnosis.getEditText().setText(documentSnapshot.getString("diagnosis"));
                            pp_prescription.getEditText().setText(documentSnapshot.getString("prescription"));
                            pp_advice.getEditText().setText(documentSnapshot.getString("advice"));


                        }else{
                            Toast.makeText(PatientProfile.this, "Prescription not found. Make sure you enter a valid reference no.", Toast.LENGTH_SHORT).show();

                        }

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });



    }
}
