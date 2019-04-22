package com.example.funtranslations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String inputText, translatedString;
    EditText inputBlock;
    Button translateButton;
    Spinner languageSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languageSelector = findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("Yoda");
        list.add("Dothraki");
        list.add("Valerian");
        list.add("Minion");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        languageSelector.setAdapter(adapter);


        inputBlock = (EditText) findViewById(R.id.userInput);

        translateButton = (Button) findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = inputBlock.getText().toString();
            }
        });

    }
}
