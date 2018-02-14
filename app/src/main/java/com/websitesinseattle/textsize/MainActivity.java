package com.websitesinseattle.textsize;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private EditText editText;
    private Button button;
    private SeekBar seekBar;
    private TextView textView;

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
        seekBar.setOnSeekBarChangeListener(this);

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
        textView.setTextSize(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast. makeText(getApplicationContext(), "seekbar started: ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();

        Toast. makeText(getApplicationContext(), "seekbar touch stopped ", Toast.LENGTH_SHORT).show();
        if(progress >= 10){
            Toast.makeText(getApplicationContext(), "Expert Level", Toast.LENGTH_SHORT).show();
        }
    }
}

