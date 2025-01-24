package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ml.HeartModel2;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class PredictActivity extends AppCompatActivity {

    private EditText answer1EditText, answer4EditText, answer5EditText, answer7EditText;
    TextView result69;
    private RadioGroup radioGroup2, radioGroup3, radioGroup6, radioGroup8;
    private Button submitButton;

    int a1,a2,a3,a4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        PredictActivity context = this;

        // Initialize Views
        answer1EditText = findViewById(R.id.answer1EditText);
        answer4EditText = findViewById(R.id.answer4EditText);
        answer5EditText = findViewById(R.id.answer5EditText);
        answer7EditText = findViewById(R.id.answer7EditText);
        result69 = findViewById(R.id.result69);

        radioGroup2 = findViewById(R.id.radioGroup1);
        radioGroup3 = findViewById(R.id.radioGroup2);
        radioGroup6 = findViewById(R.id.radioGroup6);
        radioGroup8 = findViewById(R.id.radioGroup8);

        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get answers
                String answer1 = answer1EditText.getText().toString();
                String answer4 = answer4EditText.getText().toString();
                String answer5 = answer5EditText.getText().toString();
                String answer7 = answer7EditText.getText().toString();

                int selectedRadioId1 = radioGroup2.getCheckedRadioButtonId();
                RadioButton selectedRadio1 = findViewById(selectedRadioId1);
                String answer2 = (selectedRadio1 != null) ? selectedRadio1.getText().toString() : "";
                if(answer2.equals("Male")){
                    a1 = 1;
                }
                else if (answer2.equals("Female")) {
                    a1 = 0;
                }

                int selectedRadioId2 = radioGroup3.getCheckedRadioButtonId();
                RadioButton selectedRadio2 = findViewById(selectedRadioId2);
                String answer3 = (selectedRadio2 != null) ? selectedRadio2.getText().toString() : "";
                if(answer3.equals("Typical Angina")){
                    a2 = 0;
                }
                else if (answer3.equals("Atypical Angina")) {
                    a2 = 1;
                }
                else if (answer3.equals("Non-anginal Pain")) {
                    a2 = 2;
                }
                else if (answer3.equals("Asymptomatic")) {
                    a2 = 3;
                }

                int selectedRadioId3 = radioGroup6.getCheckedRadioButtonId();
                RadioButton selectedRadio3 = findViewById(selectedRadioId3);
                String answer6 = (selectedRadio3 != null) ? selectedRadio3.getText().toString() : "";
                if(answer6.equals("True")){
                    a3 = 1;
                }
                else if (answer6.equals("False")) {
                    a3 = 0;
                }

                int selectedRadioId4 = radioGroup8.getCheckedRadioButtonId();
                RadioButton selectedRadio4 = findViewById(selectedRadioId4);
                String answer8 = (selectedRadio4 != null) ? selectedRadio4.getText().toString() : "";
                if(answer8.equals("Yes")){
                    a4 = 1;
                }
                else if (answer8.equals("No")) {
                    a4 = 0;
                }

                // Check if any answer is empty
                if (answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty() ||
                        answer4.isEmpty() || answer5.isEmpty() ||
                        answer6.isEmpty() || answer7.isEmpty() || answer8.isEmpty()) {

                    // Display alert dialog as Toast
                    Toast.makeText(PredictActivity.this, "Please answer all questions.", Toast.LENGTH_SHORT).show();

                } else {
                    // Pass answers to ResultsActivity
                    String[] ans = new String[8];
                    ans[0] = answer1;
                    ans[1] = Integer.toString(a1);
                    ans[2] = Integer.toString(a2);
                    ans[3] = answer4;
                    ans[4] = answer5;
                    ans[5] = Integer.toString(a3);
                    ans[6] = answer7;
                    ans[7] = Integer.toString(a4);

                    try {

                        HeartModel2 model = HeartModel2.newInstance(context);

                        // Prepare input data from EditText fields
                        float[] inputData = new float[8];
                        for (int i = 0; i < 8; i++) {
                            String inputValue = ans[i];
                            inputData[i] = Float.parseFloat(inputValue);
                        }

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 8}, DataType.FLOAT32);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * 8);
                        inputFeature0.loadArray(inputData, new int[]{1, 8});

                        // Runs model inference and gets result.
                        HeartModel2.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        // Display the output in the editText field
                        result69.setText(String.valueOf(outputFeature0.getFloatValue(0)));


                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }

                    Intent intent = new Intent(PredictActivity.this, ResultActivity.class);
                    intent.putExtra("res",result69.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}
