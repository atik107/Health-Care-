package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class DisCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_card);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        List<medicine_item> items = new ArrayList<medicine_item>();

        items.add(new medicine_item(
                "Hypertension (High Blood Pressure)",
                "Hypertension is a condition characterized by elevated blood pressure levels, which can strain the heart and blood vessels.",
                "Amlodipine (Norvasc)"
        ));

        items.add(new medicine_item(
                "Coronary Artery Disease (CAD)",
                "CAD occurs when the coronary arteries become narrowed or blocked, reducing blood flow to the heart muscle.",
                "Atorvastatin (Lipitor)"
        ));

        items.add(new medicine_item(
                "Arrhythmia",
                "Arrhythmia refers to irregular heart rhythms, which can cause the heart to beat too fast, too slow, or irregularly.",
                "Metoprolol (Lopressor)"
        ));

        items.add(new medicine_item(
                "Heart Failure",
                "Heart failure occurs when the heart is unable to pump blood effectively, leading to fatigue, shortness of breath, and fluid buildup.",
                "Furosemide (Lasix)"
        ));

        items.add(new medicine_item(
                "Myocardial Infarction (Heart Attack)",
                "A heart attack happens when there is a blockage in the blood supply to part of the heart, leading to damage or death of heart muscle tissue.",
                "Clopidogrel (Plavix)"
        ));

        items.add(new medicine_item(
                "Heart Valve Disease",
                "Heart valve disease occurs when one or more heart valves do not function properly, affecting blood flow within the heart.",
                "Warfarin (Coumadin)"
        ));

        items.add(new medicine_item(
                "Cardiomyopathy",
                "Cardiomyopathy is a disease of the heart muscle that makes it harder for the heart to pump blood to the rest of the body.",
                "Carvedilol (Coreg)"
        ));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new medicinie_adapter(getApplicationContext(),items));
    }
}