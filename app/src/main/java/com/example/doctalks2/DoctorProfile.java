package com.example.doctalks2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DoctorProfile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextInputLayout Name, Age, Sex, Symptoms, Diagnosis, Prescription, Advice, Buffer;
    private String temp;
    public int status;
    private TextView tv;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private FirebaseAuth mAuth;
    private ImageButton b;
    private ImageButton start;

    private Button GPDF;
    long millis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        checkPermission();

        Name = (TextInputLayout) findViewById(R.id.name);
        Age = (TextInputLayout) findViewById(R.id.age);
        Sex = (TextInputLayout) findViewById(R.id.sex);
        Symptoms = (TextInputLayout) findViewById(R.id.symptoms);
        Diagnosis = (TextInputLayout) findViewById(R.id.diagnosis);
        Prescription = (TextInputLayout) findViewById(R.id.prescription);
        Advice = (TextInputLayout) findViewById(R.id.advice);
        Buffer = (TextInputLayout) findViewById(R.id.buffer);
        linearLayout1 = (LinearLayout)findViewById(R.id.ll);
        linearLayout2 = (LinearLayout)findViewById(R.id.ll1);
        start = (ImageButton)findViewById(R.id.speak);
        b = (ImageButton)findViewById(R.id.stop);
        tv = (TextView)findViewById(R.id.dpb);
        GPDF = (Button)findViewById(R.id.GPDF);

        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                //mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    Buffer.getEditText().setText(matches.get(0));
                //mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                Buffer.getEditText().setText("");
                Buffer.getEditText().setHint("Listening...");
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpeechRecognizer.stopListening();
                Buffer.getEditText().setHint("You will see input here");
            }
        });



        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        GPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf(("Reference Number: " + Long.toString(millis)), ("Name: " + Name.getEditText().getText().toString()), ("Age: " + Age.getEditText().getText().toString()), ("Gender: " + Sex.getEditText().getText().toString()), ("Symptoms: " + Symptoms.getEditText().getText().toString()), ("Diagnosis: " + Diagnosis.getEditText().getText().toString()), ("Prescription: " + Prescription.getEditText().getText().toString()), ("Advice: " + Advice.getEditText().getText().toString()));
            }
        });
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Create an English-German translator:
        String n = Name.getEditText().getText().toString();
        String a = Age.getEditText().getText().toString();
        String s = Sex.getEditText().getText().toString();
        String sym = Symptoms.getEditText().getText().toString();
        String d = Diagnosis.getEditText().getText().toString();
        String p = Prescription.getEditText().getText().toString();
        String ad = Advice.getEditText().getText().toString();



        int id = item.getItemId();

        if(id == R.id.save){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            millis = new Date().getTime();
            temp = Buffer.getEditText().getText().toString();
            tv.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
            linearLayout1.setVisibility(View.VISIBLE);
            Name.getEditText().setText(temp.substring((temp.lastIndexOf("name") + 5),(temp.indexOf("age") - 1)));
            Age.getEditText().setText(temp.substring((temp.lastIndexOf("age") + 4), (temp.indexOf("gender") - 1)));
            Sex.getEditText().setText(temp.substring((temp.lastIndexOf("gender") + 7), (temp.indexOf("symptoms") - 1)));
            Symptoms.getEditText().setText(temp.substring((temp.lastIndexOf("symptoms") + 9), (temp.indexOf("diagnosis") - 1)));
            Diagnosis.getEditText().setText(temp.substring((temp.lastIndexOf("diagnosis") + 10), (temp.indexOf("prescription") - 1)));
            Prescription.getEditText().setText(temp.substring((temp.lastIndexOf("prescription") + 13), (temp.indexOf("advice") - 1)));
            Advice.getEditText().setText(temp.substring(temp.lastIndexOf("advice") + 7));

            Map<String, String> user = new HashMap<>();
            user.put("name", n);
            user.put("age", a);
            user.put("sex", s);
            user.put("symptoms", sym);
            user.put("diagnosis", d);
            user.put("prescription", p);
            user.put("advice", ad);
            db.collection("prescriptions").document(Long.toString(millis))
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DoctorProfile.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DoctorProfile.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                        }
                    });
            /*String last = "Name: " + Name.getText().toString() + "|" + "Age: " + Age.getText().toString() +  "|";
            last = last + "Gender: " + Sex.getText().toString() + "|" + "Symptoms: " + Symptoms.getText().toString() + "|";
            last = last + "Diagnosis: " + Diagnosis.getText().toString() + "|" + "Prescription: " + Prescription.getText().toString();
            last = last + "|" + "Advice: " + Advice.getText().toString();*/


        }

        else if(id==R.id.h_lc)
        {
          Name.getEditText().setText("सौरव बेहुरा");
          //Age.getEditText().setText("");
          Sex.getEditText().setText("पुरुष");
          Symptoms.getEditText().setText("बुखार");
          Diagnosis.getEditText().setText("मलेरिया");
          Prescription.getEditText().setText("पैरासिटामोल");
          Advice.getEditText().setText("ज्यादा पानी पियो");
        }


        else if(id==R.id.b_lc)
        {
            Name.getEditText().setText("সৌরভ বেহুরা");
            //Age.getEditText().setText("");
            Sex.getEditText().setText("পুরুষ");
            Symptoms.getEditText().setText("জ্বর");
            Diagnosis.getEditText().setText("ম্যালেরিয়া");
            Prescription.getEditText().setText("প্যারাসিটামল");
            Advice.getEditText().setText("আমার স্নাতকের");

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new) {
            linearLayout1.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            Buffer.getEditText().setText(" ");
        } else if (id == R.id.nav_open) {

        } else if (id == R.id.nav_pl) {

        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            startActivity(new Intent(DoctorProfile.this,MainActivity.class));
            finish();

        } else if (id == R.id.nav_share) {
            String n1 = Name.getEditText().getText().toString();
            String a1 = Age.getEditText().getText().toString();
            String s1 = Sex.getEditText().getText().toString();
            String sym1 = Symptoms.getEditText().getText().toString();
            String d1 = Diagnosis.getEditText().getText().toString();
            String p1 = Prescription.getEditText().getText().toString();
            String ad1 = Advice.getEditText().getText().toString();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Name: "+n1+"\nAge: "+a1+"\nGender: "+s1+"\nSymptoms: "+sym1+"\nDiagnosis: "+d1+"\nPrescription: "+p1+"\nAdvice: "+ad1);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createPdf(String s0,String s1,String s2, String s3, String s4, String s5, String s6, String s7){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        //paint.setColor(Color.RED);
        //canvas.drawCircle(50, 50, 30, paint);
        paint.setColor(Color.BLACK);
        /*int x,y,pointer = 0;
        for(y=20; y<590 ;y=y+10)
        {
            for(x=20; x<280; x = x+10)
            {
                if(sometext.substring(pointer, (pointer + 1)).compareTo("|") == 1)
                {
                    y = y+10;
                    x = 20;
                }

                pointer++;
                if(pointer >= sometext.length()) break;
            }
        }*/

        //canvas.drawt
        // finish the page
        canvas.drawText(s0, 20, 20, paint);
        canvas.drawText(s1, 20, 50, paint);
        canvas.drawText(s2, 20, 80, paint);
        canvas.drawText(s3, 20, 110, paint);
        canvas.drawText(s4, 20, 140, paint);
        canvas.drawText(s5, 20, 170, paint);
        canvas.drawText(s6, 20, 200, paint);
        canvas.drawText(s7, 20, 230, paint);
        document.finishPage(page);
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"prescription.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something went wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
}