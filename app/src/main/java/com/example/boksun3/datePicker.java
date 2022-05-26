package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class datePicker extends AppCompatActivity {

     Button btn_ok;
     int mYear ,mMonth , mDay;

     TextView tv_Date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);


        tv_Date = findViewById(R.id.handi_date);
        btn_ok = findViewById(R.id.btn_ok);





        Calendar calendar = new GregorianCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePicker datePicker = findViewById(R.id.vDatePicker);
        datePicker.init(mYear,mMonth,mDay,mOnDateChangedListener);


    }

    public void mOnClick(View v){
        Intent intent2 = new Intent(datePicker.this,handiJoin.class);
        intent2.putExtra("mYear",mYear);
        intent2.putExtra("mMonth",mMonth);
        intent2.putExtra("mDay",mDay);



        startActivity(intent2);
        finish();
    }

    DatePicker.OnDateChangedListener mOnDateChangedListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker datePicker, int yyyy, int mm, int dd) {
            mYear =yyyy;
            mMonth = mm;
            mDay = dd;



        }
    };


}

