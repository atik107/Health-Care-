package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MedCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_card);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        List<medicine_item> items = new ArrayList<medicine_item>();
        items.add(new medicine_item("Aspirin","Acetylsalicylic acid (100mg)","Pain relief, anti-inflammatory, and anti-fever."));
        items.add(new medicine_item("Paracetamol","Paracetamol (500mg)","Pain relief and fever reduction"));
        items.add(new medicine_item("Zantac","Ranitidine (150mg)","Heartburn and acid indigestion"));
        items.add(new medicine_item("Metformin","Metformin (500mg)","Type 2 diabetes management"));
        items.add(new medicine_item("Effexor","Venlafaxine (75mg)","Treatment of depression and anxiety disorders"));
        items.add(new medicine_item("Cipro","Ciprofloxacin (500mg)","Antibiotic for bacterial infections"));
        items.add(new medicine_item("Toprol XL","Metoprolol (50mg)","High blood pressure and angina"));
        items.add(new medicine_item("Nexium","Esomeprazole (20mg)","Gastroesophageal reflux disease (GERD)"));
        items.add(new medicine_item("Loratadine","Loratadine (10mg)","Allergy relief"));
        items.add(new medicine_item("Zyrtec", "Cetirizine (10mg)", "Allergy relief"));
        items.add(new medicine_item("Celebrex", "Celecoxib (200mg)", "Pain relief and anti-inflammatory"));
        items.add(new medicine_item("Singulair", "Montelukast (10mg)", "Asthma and allergy control"));
        items.add(new medicine_item("Effient", "Prasugrel (10mg)", "Antiplatelet agent to prevent blood clots"));
        items.add(new medicine_item("Zocor", "Simvastatin (20mg)", "Lowering cholesterol levels"));
        items.add(new medicine_item("Propecia", "Finasteride (1mg)", "Treatment of male pattern baldness"));
        items.add(new medicine_item("Zoloft", "Sertraline (50mg)", "Treatment of depression and anxiety disorders"));
        items.add(new medicine_item("Tramadol", "Tramadol (50mg)", "Moderate to severe pain relief"));
        items.add(new medicine_item("Metoprolol", "Metoprolol (50mg)", "High blood pressure and angina"));
        items.add(new medicine_item("Lisinopril", "Lisinopril (10mg)", "High blood pressure"));
        items.add(new medicine_item("Advil", "Ibuprofen (200mg)", "Pain relief, anti-inflammatory, and fever reduction"));
        items.add(new medicine_item("Benadryl", "Diphenhydramine (25mg)", "Allergy relief and sleep aid"));
        items.add(new medicine_item("Prilosec", "Omeprazole (20mg)", "Treatment of acid-related gastrointestinal conditions"));
        items.add(new medicine_item("Amoxicillin", "Amoxicillin (500mg)", "Antibiotic for bacterial infections"));
        items.add(new medicine_item("Lipitor", "Atorvastatin (20mg)", "Lowering cholesterol levels"));
        items.add(new medicine_item("Synthroid", "Levothyroxine (50mcg)", "Hypothyroidism treatment"));
        items.add(new medicine_item("Warfarin", "Warfarin (2mg)", "Anticoagulant to prevent blood clots"));
        items.add(new medicine_item("Morphine", "Morphine (10mg)", "Severe pain relief"));
        items.add(new medicine_item("Prednisone", "Prednisone (5mg)", "Anti-inflammatory and immune system suppression"));
        items.add(new medicine_item("Albuterol", "Albuterol (100mcg)", "Bronchodilation in asthma and COPD"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new medicinie_adapter(getApplicationContext(),items));
    }
}