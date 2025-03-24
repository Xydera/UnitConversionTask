package com.richo.unitconversiontask;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText inputValue;
    private Button convertButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // List of units
        String[] units = {
                "Centimeter", "Meter", "Kilometer",
                "Inch", "Foot", "Yard", "Mile",
                "Gram", "Kilogram",
                "Pound", "Ounce", "Ton",
                "Celsius", "Fahrenheit", "Kelvin"
        };

        // Set up Spinners with unit options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Set up Convert Button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();
                String inputText = inputValue.getText().toString();

                if (!inputText.isEmpty()) {
                    double inputAmount = Double.parseDouble(inputText);
                    double result = convertUnit(fromUnit, toUnit, inputAmount);
                    resultText.setText("Result: " + result);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Function to handle conversions
    private double convertUnit(String from, String to, double value) {
        Map<String, Double> lengthConversions = new HashMap<>();
        lengthConversions.put("Centimeter", 1.0);
        lengthConversions.put("Meter", 100.0);
        lengthConversions.put("Kilometer", 100000.0);
        lengthConversions.put("Inch", 2.54);
        lengthConversions.put("Foot", 30.48);
        lengthConversions.put("Yard", 91.44);
        lengthConversions.put("Mile", 160934.0);

        Map<String, Double> weightConversions = new HashMap<>();
        weightConversions.put("Gram", 0.001);
        weightConversions.put("Kilogram", 1.0);
        weightConversions.put("Pound", 0.453592);
        weightConversions.put("Ounce", 28.3495);
        weightConversions.put("Ton", 907.185);

            switch (from) {
                case "Celsius":
                    switch (to) {
                        case "Fahrenheit":
                            return (value * 1.8) + 32;
                        case "Kelvin":
                            return value + 273.15;
                        case "Celsius":
                            return value;
                    }
                    break;

                case "Fahrenheit":
                    switch (to) {
                        case "Celsius":
                            return (value - 32) / 1.8;
                        case "Kelvin":
                            return ((value - 32) / 1.8) + 273.15;
                        case "Fahrenheit":
                            return value;
                    }
                    break;

                case "Kelvin":
                    switch (to) {
                        case "Celsius":
                            return value - 273.15;
                        case "Fahrenheit":
                            return ((value - 273.15) * 1.8) + 32;
                        case "Kelvin":
                            return value;
                    }
                    break;

                default:
                    // Handle length and weight conversions using HashMaps
                    if (lengthConversions.containsKey(from) && lengthConversions.containsKey(to)) {
                        return (value * lengthConversions.get(from)) / lengthConversions.get(to);
                    } else if (weightConversions.containsKey(from) && weightConversions.containsKey(to)) {
                        return (value * weightConversions.get(from)) / weightConversions.get(to);
                    }
            }

            return 0.0; // Invalid conversion
    }
}
