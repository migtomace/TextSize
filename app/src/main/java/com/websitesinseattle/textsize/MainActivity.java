package com.websitesinseattle.textsize;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, TextView.OnEditorActionListener {

    private EditText editText;
    private Button button;
    private SeekBar seekBar;
    private TextView textView;

    //define the shared preferences object
    private SharedPreferences savedValues;

    //string
    private String fontString = "";

    //define instant variables
    private final int NONE = 0;
    private final int HALF = 1;

    private SharedPreferences prefs;
    private boolean rememberFontSizePrefs = true;
    private int fontChange = NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create references
        editText = findViewById(R.id.editText);
        button  = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.display_textView);

        //create listener
        button.setOnClickListener(this);
        editText.setOnEditorActionListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //set the default values for the prefs
        PreferenceManager.setDefaultValues(this,R.xml.preferences, false);

        //get the default shared prefs object
        prefs = PreferenceManager.getDefaultSharedPreferences(this);



    }

    @Override
    public void onPause() {
        // save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fontString", fontString);
        editor.commit();

        super.onPause();

        Log.d(TAG, "onPause executed");

        //create a toast
        Toast t = Toast.makeText(this, "onPause method", Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get preferences
        rememberFontSizePrefs = prefs.getBoolean("pref_remember_input", true);
        fontChange = Integer.parseInt(prefs.getString("pref_fontSize", "0"));

        // get the instance variables
        fontString = savedValues.getString("fontString", "");

        // set the saved text input on its widget
        editText.setText(fontString);

        // display the saved text
        displayText();

        Log.d(TAG, "onResume executed");

        //create a toast
        Toast t = Toast.makeText(this, "onResume method", Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu:
                startActivity(new Intent(getApplicationContext(),
                        SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            displayText();
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        displayText();
    }

    public void displayText(){
        String text = editText.getText().toString();


        textView.setText(text);



    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Toast.makeText(getApplicationContext(), "seekbar started: ", Toast.LENGTH_SHORT).show();


        fontString = editText.getText().toString();

        int divide = i/2;

        if(fontChange == NONE){
            textView.setTextSize(i);
            Toast.makeText(getApplicationContext(), "no pref", Toast.LENGTH_SHORT).show();
        } else if (fontChange == HALF) {
            textView.setTextSize(divide);
            Toast.makeText(getApplicationContext(),"divide",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(getApplicationContext(), "seekbar started: ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();

        Toast.makeText(getApplicationContext(), "seekbar touch stopped ", Toast.LENGTH_SHORT).show();
        if(progress >= 10){
            Toast.makeText(getApplicationContext(), "Expert Level", Toast.LENGTH_SHORT).show();
        }
    }
}