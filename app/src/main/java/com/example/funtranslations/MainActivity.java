package com.example.funtranslations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String inputText, translatedString, selectedLanguage = "yoda", reqURL;
    EditText inputBlock;
    Button translateButton;
    Spinner languageSelector;
    TextView displayBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputBlock = findViewById(R.id.userInput);
        displayBlock = findViewById(R.id.showTranslation);

        languageSelector = findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("Yoda");
        list.add("Dothraki");
        list.add("Valyrian");
        list.add("Minion");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        languageSelector.setAdapter(adapter);
        languageSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = parent.getItemAtPosition(position).toString();

                displayBlock.setText("Nothing to display yet...");

                String urlLang;
                switch (selectedLanguage) {
                    case "Dothraki":
                        urlLang = "dothraki";
                        break;
                    case "Valyrian":
                        urlLang = "valyrian";
                        break;
                    case "Minion":
                        urlLang = "minion";
                        break;
                    default:
                        urlLang = "yoda";
                        break;
                }
                reqURL = "https://api.funtranslations.com/translate/" + urlLang;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final RequestQueue queue = Volley.newRequestQueue(this);

        translateButton = findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = inputBlock.getText().toString();

                String localURL = reqURL + ".json?text="+ URLEncoder.encode(inputText);
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
                        localURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
//                                textView.setText("Response is: " + response.substring(0, 500));
                                String json = response.toString();
                                Log.e("Rest Response", json);

                                JsonParser parser = new JsonParser();
                                JsonElement jsonTree = parser.parse(json);
                                JsonObject jsonObject = jsonTree.getAsJsonObject();
                                translatedString = jsonObject.get("contents").getAsJsonObject().get("translated").getAsString();

                                displayBlock.setText(translatedString);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response", error.toString());
                                String errorMsg = "Can not translate the text.";
                                displayBlock.setText(errorMsg);
                            }
                        }
                );

                queue.add(stringRequest);
            }
        });

    }
}
