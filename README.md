# DocTalks app by team Code Pirates

## Impact:
The Poor Handwriting of the Doctor's is not a Countries but Worldwide concern. The high incidence of Medical Errors in Britain is estimated to cause the Death of upto 30,000 people each year and the figure rises to 1,00,000 deaths per year in USA. 
In a densely populated Country like India where there is one Government Doctor for every 14,089 people we can barely expect them to spend appropriate time with every patient and write proper prescriptions without any mistakes and in a Readable format and Handwriting.

Besides this the other Problem that attracted our attention was that when patients receive paper-based prescriptions, the prescriptions sometimes are forgotten and never filed, or are lost by the patient and must be re-written or called into the pharmacy, besides the Patient also suffers due to the lose.

The Doctors generally don't know about certain allergies in Certain Patients and prescribe them Medicines which instead of acting as an Antidote becomes a Toxin for them.


## Technology+Implementation:
The Android app we have developed is called Doc Talks. The app has the option to log in as a Doctor or as a Patient. The app will write formatted prescriptions based on dictation from doctors. The formatted prescription in the app will include some of the main keywords that are included in an original prescription such as the name of the patient, symptoms of the disease, diagnosis report, list of prescribed medicines and any other beneficial advice. The doctor can address each of the fields by saying one of these keywords so that data can be recorded/captured discreetly under each of the various fields. The doctor will also be able to edit/delete manually if required by the help of a keyboard at the bottom of the screen. After completion of the dictation, the doctor will be able to preview the prescription and make any changes if required. The formatted prescription will then be stamped with date and time by the app itself and will be sent directly to the patient via SMS or e-mail or Whatsapp etc, the patients record will also get saved in the app and the doctor can view it later whenever required.Now coming on to the Patients login section- here the patient can view all his previous records of prescription to whichever doctor he went to previously.

Now talking about the backend of our app. The authentication of doctors and patients is done by integrating Firebase Authentication. Then in the doctor profile in order to allow the doctor to record voice clearly we have integrated a machine learning algorithm to remove the excess noise and then pass this output as input to the Google speech-to-text API(which we have modified as per our requirement to perform continuous voice recognition of the doctor). Then after successful generation of the prescription, the doctor can either press the share or save button. The share button will allow to share the prescription via SMS, email etc. The save button first saves the prescription in Cloud Firestore and also saves the pdf form of the prescription in the phone memory. Each prescription is stored in the Cloud under an unique id/reference no.(which is a combination of the doctors's license number and the date and time at which the prescription was generated.) We have also provided language change feature so that the patient can get the prescription in the required local language. Then in the patients profile the patient can view/refer to previous prescription by giving the apt. reference no.

We have also integrated Actions on Google assistant with our app with the help of DialogFlow and JavaScript. This acts as an interactive interface between the doctor and Google Assistant. This can also be used to save prescriptions in Cloud FireStore.

## Main Features:
1. *Continuous Voice Recognition*-The Voice Recognition goes on Continuously, unless the Doctor presses the Record Button again.

2. *Separate Login for Doctors and Patients*-The App verifies and has separate Login for the Doctor and Patient

3. *Language Conversion*-The Doctor can select multiple languages and the entire text will get converted.

4. *Manual Keyboard*-If by mistake the App reads a wrong data, the Doctor can correct it using 
   the Keyboard at the screen.
   
5. *Duplicate Words Removal*-If same words get repeated continuously, the App automatically removes the other one.

6. *Print/Send Option*-The Doctor can choose to either Print the Prescription or send it to the Patient diretly through SMS or any other App(Ex. WhatsApp).

## Scalability:
The idea is highly scalable and can also be used to earn a decent amount of revenue from it.
### Business Model-
1. *Paid Version of App*-Revenue can be made when some one buys the Paid version of the App which will have no Ad's and additional features like Online Doctors appointment and E-Pharmacy.

2. *Playstore Revenue*-A decent amount can be earned from Playstore when the number of downloads will exceed the particular limit.

3. *Doctors Appointment*-We can contact the Doctors and set an agreement with them to give us a small percentage of money for every Appointment Patients make through our App.

4. *Online Medicine*-We can also collaborate with online medicine selling companies(Netmeds,1mg etc)and allow them to sell medicine through our app and give us some percentage.

## Future Improvements:
1. *Online Doctors Appointment*-The App can be further Improved by adding an Online Doctors Appointment section where Patients can easily get the Appointment of whichever Doctor they want without an Hustle.

2. *E- Pharmacy*-Another splendid feature can be an E- Pharmacy section for the Patients. They can get the Medicines directly at there door steps at a considerably lower cost.

3. *Commercialization*-The App can offer two different Modes,the Free and the Paid Mode. The free version will be for everyone and others can pay a minimal fee to get the Premium version of the App where they will get access to special options like Online Doctors Appointment and E-Pharmacy as well as Ad free experience.

4. *Rating Doctors*-Patients can Rate the Doctors they have visited and hence others can see the Reviews and decide which Doctor to choose according to there prefernces.


## Video Links:
This video shows the working of Actions on Google Assistant:
https://www.youtube.com/watch?v=5prA3CpuFkI&feature=youtu.be

This video shows the working of our app:
https://www.youtube.com/watch?v=wrsTgJnVk_8&feature=youtu.be




