package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class OptimalWeightActivity extends AppCompatActivity {

    RadioGroup radioGroup1;
    EditText input_feet,input_inch;
    Button button;
    TextView showresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimal_weight);


        radioGroup1 = findViewById(R.id.radioGroup1);
        input_feet = findViewById(R.id.input_feet);
        input_inch = findViewById(R.id.input_inch);
        button = findViewById(R.id.buttonBMI);
        showresult = findViewById(R.id.showresult);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioId1 = radioGroup1.getCheckedRadioButtonId();
                RadioButton selectedRadio1 = findViewById(selectedRadioId1);
                String gender = (selectedRadio1 != null) ? selectedRadio1.getText().toString() : "";
                String sfeet = input_feet.getText().toString();
                String sinch = input_inch.getText().toString();


                if (gender.isEmpty() || sfeet.isEmpty() || sinch.isEmpty()) {
                    // Show a message indicating which fields are empty

                    StringBuilder errorMessage = new StringBuilder("");
                    if (gender.isEmpty()) {
                        errorMessage.append("Please enter gender" + gender);
                    }
                    else if (sfeet.isEmpty() || sinch.isEmpty()){
                        errorMessage.append("Please enter ");
                        if (sfeet.isEmpty()) {
                            if(sinch.isEmpty()){
                                errorMessage.append("feet & ");
                            }
                            else{
                                errorMessage.append("feet");
                            }

                        }
                        if (sinch.isEmpty()) {
                            errorMessage.append("inch");
                        }
                    }

                    errorMessage.append(".");

                    showresult.setText(errorMessage.toString());
                    return;
                }


                float feet = Float.parseFloat(sfeet);
                float inch = Float.parseFloat(sinch);
                float bmi = 0;
                float height = (float) (feet*0.3048 + inch * 0.0254)*100;
                if(gender.equals("Male")){
                    bmi = (float) (48 + (2.72*(height-152.4)));
                }
                else if(gender.equals("Female")){
                    bmi = (float) (45.36 + (2.26*(height-152.4)));
                }

                String resultMessage = String.format("Optimal Weight: %.2f", bmi);
                showresult.setText(resultMessage);


            }
        });
    }
}