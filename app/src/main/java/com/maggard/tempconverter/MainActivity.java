package com.maggard.tempconverter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
    implements OnEditorActionListener{

    //set global variables for text and edit text boxes that will be changing
    private EditText fahrenheitEditText;
    private TextView celciusResultLabel;

    private String fahrenheitInput;

    //define shared preferences
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference to your widgets
        fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);
        celciusResultLabel = (TextView) findViewById(R.id.celciusResultLabel);

        //when you have editable text you need to have an action listener
        fahrenheitEditText.setOnEditorActionListener((TextView.OnEditorActionListener) this);

        //create the SharedPreference object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    /*
        ON EDITOR ACTION
     */

    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {         //change int i to "int actionId"
        if(actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }

        return false;
    }




    //calculate temperature change
    private void calculateAndDisplay() {
        fahrenheitInput = fahrenheitEditText.getText().toString();
        float fahrenheit = Float.parseFloat(fahrenheitInput);

        float celcius = (fahrenheit-32)*5/9;
        //set the information back to the widget

        NumberFormat number = NumberFormat.getNumberInstance();
        celciusResultLabel.setText(number.format(celcius));
    }

    /*
        ON PAUSE AND RESUME METHODS

     */

    @Override
    protected void onPause() {
        //save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitInput",fahrenheitInput);
        editor.commit();                         //submits the information
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //get instance variables from editor
        fahrenheitInput = savedValues.getString("fahrenheitInput","");

        //call calculate and display
        calculateAndDisplay();
    }
}
