#DocTalks app by team Code Pirates


The Android app we have developed is called Doc Talks. It will have the option to log in as a Doctor or as a Patient. 
The app will write formatted prescriptions based on dictation from doctors. The formatted prescription in the app will 
include some of the main keywords that are included in an original prescription such as the name of the patient, symptoms 
of the disease, diagnosis report, list of prescribed medicines and any other beneficial advice. The doctor can address 
each of the fields by saying one of these keywords so that data can be recorded/captured discreetly under each of the various 
fields. The doctor will also be able to edit/delete manually if required by the help of a keyboard at the bottom of the screen.
After completion of the dictation, the doctor will be able to preview the prescription and make any changes if required. 
The formatted prescription will then be stamped with date and time by the app itself and will be sent directly to the patient 
via SMS or e-mail or Whatsapp etc.

Now talking about the backend of our app. The authentication of doctors and patients is done by integrating Firebase Authentication.
Then in the doctor profile in order to allow the doctor to record voice clearly we have integrated a machine learning algorithm to
remove the excess noise and then pass this output as input to the Google speech-to-text API(which we have modified as per our requirement
to perform continuous voice recognition of the doctor). Then after successful generation of the prescription, the doctor can either
press the share or save button. The share button will allow to share the prescription via SMS, email etc. The save button first saves
the prescription in Cloud Firestore and also saves the pdf form of the prescription in the phone memory. Each prescription is stored in the 
Cloud under an unique id/reference no.(which is a combination of the doctors's license number and the date and time at which the 
prescription was generated.) We have also provided language change feature so that the patient can get the prescription in the 
required local language. Then in the patients profile the patient can view/refer to previous prescription by giving the apt. reference no.

We have also integrated Actions on Google assistant with our app with the help of DialogFlow and JavaScript. This acts as an interactive
interface between the doctor and Google Assistant. This can also be used to save prescriptions in Cloud FireStore.

