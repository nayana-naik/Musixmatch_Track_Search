package com.example.inclass07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.util.Date;


public class ThirdActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        Intent intent = getIntent();
        final String url = intent.getExtras().getString("URL");

        // textView.setText(url);
        //  long now            = System.currentTimeMillis();
        //long secondsAgo     = now - 3000;
        //long minutesAgo     = now - 300000;
        //long hoursAgo       = now - 3600000;
        //long monthsAgo      = timeStringtoMilis("2014-01-02 00:00:00");
        //long longTimeAgo    = timeStringtoMilis("2004-05-12 09:33:12");

        // PrettyTime prettyTime = new PrettyTime();
        // textView.setText(prettyTime.format(new Date(minutesAgo)));

//        textView01.setText(prettyTime.format(new Date(secondsAgo)));
//        textView02.setText(prettyTime.format(new Date(minutesAgo)));
//        textView03.setText(prettyTime.format(new Date(hoursAgo)));
//        textView04.setText(prettyTime.format(new Date(monthsAgo)));
//        textView05.setText(prettyTime.format(new Date(longTimeAgo)));
//

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fromThird = new Intent();
                fromThird.putExtra("Confirm", "URL Verified");
                fromThird.putExtra("URL-1", url);
                setResult(RESULT_OK, fromThird);
                finish();


            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> spinnerArray = ArrayAdapter.createFromResource(this, R.array.sample_array, android.R.layout.simple_spinner_item);
        spinnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArray);


    }
    }
