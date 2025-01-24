package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    faq_adapter adapter;
    List<faq_item> items = new ArrayList<faq_item>();
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqactivity);

        recyclerView = findViewById(R.id.recyclerViewFAQ);
        adapter = new faq_adapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return true;
            }
        });

        items.add(new faq_item("What is the purpose of the app?","Our app is designed to help individuals manage their heart health effectively by providing tools for monitoring, tracking, and accessing relevant information related to heart conditions."));
        items.add(new faq_item("Is my data secure?","Yes, your data's security is a priority. We employ encryption and follow strict privacy protocols to safeguard your personal health information."));
        items.add(new faq_item("What features does the app offer for heart health management?","Our app provides features like realtime prescription, appointment, ML model to predict heart condition, emergency ambulance & hotline and doctors number at your hand."));
        items.add(new faq_item("Tell me some key principles that can help me to a balanced diet effectively?","To maintain a balanced diet, include a variety of nutrient-dense foods, practice portion control, \n" +
                "enjoy moderation, and aim for regular meal timing. Stay hydrated by drinking enough water throughout the day, \n" +
                "practice mindful eating, limit added sugars and processed foods, plan meals in advance, read food labels, and \n" +
                "seek professional guidance. By incorporating these principles into daily routines, individuals can develop \n" +
                "habits that support a balanced diet, leading to improved overall health and well-being. By incorporating these \n" +
                "strategies, individuals can develop habits that support a balanced diet and improved overall health."));
        items.add(new faq_item("Can you Suggest me balanced diet routine for me?","Breakfast includes whole grain cereal with fresh fruits, low-fat yogurt, nuts, and water. \n" +
                "Mid-morning snacks include apple slices, carrot sticks with hummus, and green tea. Lunch \n" +
                "includes grilled chicken salad with mixed greens, vegetables, and beans. Afternoon snacks \n" +
                "include Greek yogurt parfait, cheese crackers, and whole grain crackers. Dinner includes \n" +
                "baked salmon with roasted vegetables, Quinoa or brown rice, and mixed salad. Evening snacks \n" +
                "include mixed berries, popcorn, and cottage cheese.\n"));
        items.add(new faq_item("My BMI index is tooh high what should I do now?","To lower your BMI and improve overall health, consult a healthcare professional, focus on a balanced diet, \n" +
                "control portion sizes, incorporate regular physical activity, make lifestyle changes, monitor progress, \n" +
                "get enough sleep, and manage stress. Focus on gradual, healthy changes over time, avoid crash diets, \n" +
                "and maintain a healthy weight for better overall well-being. Aim for 7-8 hours of quality sleep per night.\n"));
        items.add(new faq_item("Can you give me some meditation technique to refresh my mind?","Start with short sessions (5-10 minutes) and gradually increase the duration as you become more comfortable.\n" +
                "Mindfulness Meditation involves sitting comfortably, closing eyes, and focusing on breath. Pay attention to breath \n" +
                "sensations, and gently return focus when mind wanders without judgment. Engage in a slow, mindful walk, paying \n" +
                "attention to each step and foot sensation, indoors or outdoors, focusing on the movements involved. \n"));
        items.add(new faq_item("Is the app available on multiple platforms?","No, our app is currently available on Android. We're focusing on providing the best experience and functionalities for this platform to ensure a seamless user experience."));

        adapter.notifyDataSetChanged();
    }

    private void filterText(String text) {
        ArrayList<faq_item> filteredList = new ArrayList<>();

        for (faq_item faqItem : items) {
            if (faqItem.getQues().toLowerCase().contains(text.toLowerCase()) || faqItem.getAns().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(faqItem);
            }
        }

        adapter.setSearchList(filteredList); // Set the filtered list to the adapter
    }

}