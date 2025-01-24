package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FullArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);

        TextView textView = findViewById(R.id.textView);
        TextView art = findViewById(R.id.art);

        // Get the article number sent from ArticleActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int articleNumber = extras.getInt("ARTICLE_NUMBER");

            // Set the title and content for the corresponding article number
            switch (articleNumber) {
                case 1:
                    textView.setText("Heart Disease Overview");
                    art.setText("Heart disease, also known as cardiovascular disease, encompasses various conditions that impair the heart's functionality. It includes coronary artery disease, arrhythmias, heart failure, and more. This broad category of diseases affects the heart's structure and function, leading to severe health complications if left unmanaged. Lifestyle modifications play a pivotal role in preventing and managing heart diseases. Regular exercise, a balanced diet, stress management, and avoiding smoking are crucial steps in reducing the risk. Medications and medical interventions are also employed to control symptoms and reduce associated risks. Understanding the multifaceted nature of heart disease empowers individuals to make informed decisions for their heart health."); // Replace R.string.article_1 with your string resource
                    break;
                case 2:
                    textView.setText("Understanding Coronary Artery Disease (CAD)");
                    art.setText("Coronary Artery Disease (CAD) is a prevalent heart condition characterized by the narrowing or blockage of coronary arteries, impairing blood flow to the heart muscle. This restriction in blood flow, often due to the buildup of plaque in the arteries, can lead to angina (chest pain) and increases the risk of heart attacks. Managing CAD involves lifestyle modifications such as a heart-healthy diet, regular exercise, and quitting smoking. Medications like statins to lower cholesterol levels and interventions like angioplasty or stent placement may be necessary to restore adequate blood flow and reduce the risk of complications."); // Replace R.string.article_2 with your string resource
                    break;
                case 3:
                    textView.setText("Arrhythmias: Understanding Heart Rhythm Disorders");
                    art.setText("Arrhythmias encompass irregular heart rhythms that affect the heart's ability to pump blood efficiently. These abnormalities can cause the heart to beat too fast (tachycardia), too slow (bradycardia), or irregularly. Diagnosis involves tests like electrocardiograms (ECGs/EKGs) to identify the specific type of arrhythmia. Treatment options range from medications that regulate heart rhythms to procedures like catheter ablation, which targets abnormal electrical pathways in the heart. Managing lifestyle factors like stress and caffeine intake, along with adhering to prescribed medications, plays a significant role in controlling arrhythmias and minimizing associated risks."); // Replace R.string.article_3 with your string resource
                    break;
                case 4:
                    textView.setText("Understanding Heart Failure");
                    art.setText("Heart failure occurs when the heart cannot pump blood effectively, leading to symptoms like shortness of breath, fatigue, and fluid retention. It is a chronic and progressive condition that requires ongoing management. Treatment includes medications to reduce strain on the heart, lifestyle modifications such as limiting sodium intake, regular exercise within limits, and in advanced cases, devices like pacemakers or defibrillators. Understanding the condition, adhering to treatment plans, and making necessary lifestyle adjustments are crucial for individuals with heart failure to improve their quality of life and reduce complications."); // Replace R.string.article_4 with your string resource
                    break;
                case 5:
                    textView.setText("Valvular Heart Disease: Understanding Heart Valve Problems");
                    art.setText("Valvular heart disease refers to issues with one or more heart valves, disrupting blood flow within the heart. This condition can be due to valve stenosis (narrowing) or regurgitation (leakage), causing symptoms like chest pain, fatigue, or shortness of breath. Treatment options vary from medications to surgical interventions like valve repair or replacement, depending on the severity and type of valve problem. Regular monitoring and adherence to treatment plans are essential for managing valvular heart disease and reducing the risk of complications, ensuring better heart health and improved overall well-being."); // Replace R.string.article_5 with your string resource
                    break;
                case 6:
                    textView.setText("Preventing Heart Disease: Lifestyle and Risk Reduction");
                    art.setText("Preventing heart disease is achievable through proactive lifestyle changes and risk reduction strategies. Adopting a heart-healthy lifestyle significantly reduces the risk factors associated with cardiovascular diseases. A balanced diet rich in fruits, vegetables, whole grains, lean proteins, and healthy fats helps maintain optimal cholesterol levels and blood pressure. Regular physical activity, such as brisk walking, swimming, or cycling, strengthens the heart muscle and improves overall cardiovascular health.\n" +
                            "\n" +
                            "Managing stress through relaxation techniques like meditation, yoga, or deep breathing exercises is vital as stress can elevate blood pressure and strain the heart. Avoiding tobacco and minimizing alcohol consumption contribute to a healthier heart. Monitoring and managing underlying conditions like hypertension, diabetes, and high cholesterol levels are equally important.\n" +
                            "\n" +
                            "Regular medical check-ups aid in early detection of potential heart-related issues, enabling timely interventions and preventive measures. Embracing a heart-conscious lifestyle, along with routine health screenings, empowers individuals to take charge of their cardiovascular health and significantly lowers the risk of heart disease."); // Replace R.string.article_5 with your string resource
                    break;
                default:
                    break;
            }
        }
    }
}
