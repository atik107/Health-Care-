package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    EditText input_feet,input_inch,input_weight;
    Button buttonBMI;
    TextView showresult;
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);

        input_weight = findViewById(R.id.input_weight);
        input_feet = findViewById(R.id.input_feet);
        input_inch = findViewById(R.id.input_inch);
        buttonBMI = findViewById(R.id.buttonBMI);
        showresult = findViewById(R.id.showresult);

        buttonBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sweight = input_weight.getText().toString();
                String sfeet = input_feet.getText().toString();
                String sinch = input_inch.getText().toString();


                if (sweight.isEmpty() || sfeet.isEmpty() || sinch.isEmpty()) {
                    // Show a message indicating which fields are empty
                    StringBuilder errorMessage = new StringBuilder("Please enter ");
                    if (sweight.isEmpty()) {
                        errorMessage.append("weight");
                    }
                    if (sfeet.isEmpty()) {
                        if(sinch.isEmpty()){
                            errorMessage.append(sweight.isEmpty() ? ", " : "").append("feet");
                        }
                        else{
                            errorMessage.append(sweight.isEmpty() ? " & " : "").append("feet");
                        }

                    }
                    if (sinch.isEmpty()) {
                        errorMessage.append((sweight.isEmpty() || sfeet.isEmpty()) ? " & " : "").append("inch");
                    }
                    errorMessage.append(".");

                    showresult.setText(errorMessage.toString());
                    return;
                }


                float weight = Float.parseFloat(sweight);
                float feet = Float.parseFloat(sfeet);
                float inch = Float.parseFloat(sinch);

                float height = (float) (feet*0.3048 + inch * 0.0254);
                float bmi = weight/ (height*height);

                String category;
                if (bmi < 18.5) {
                    category = "Underweight";
                } else if (bmi >= 18.5 && bmi <= 24.9) {
                    category = "Healthy weight";
                } else if (bmi >= 25 && bmi <= 29.9) {
                    category = "Overweight";
                } else {
                    category = "Obese";
                }

                String resultMessage = String.format("Your BMI: %.2f\nCategory: %s", bmi, category);
                showresult.setText(resultMessage);


            }
        });
    }
}