package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class clickAddAlarm extends AppCompatActivity {

    TimePicker timePicker ;
    Button btn_alarm;
    private AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_add_alarm);

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        // 이전 설정값 보여주기
        // 없으면 디폴트값은 현재시간
        SharedPreferences sharedPreferences = getSharedPreferences("daily alram", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextTime = new GregorianCalendar();
        nextTime.setTimeInMillis(millis);

        Date nextDate = nextTime.getTime();
        String time_text = new SimpleDateFormat("a hh : mm", Locale.getDefault()).format(nextDate);

        Toast.makeText(getApplicationContext(), "다음 알람은" + time_text+ "로 설정되었습니다.",Toast.LENGTH_SHORT).show();

        // 이전 설정값으로 TimePicker 초기화
        Date currentTime = nextTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm",Locale.getDefault());

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));

        if(Build.VERSION.SDK_INT >= 23){
            timePicker.setHour(pre_hour);
            timePicker.setMinute(pre_minute);
        }else{
            timePicker.setCurrentHour(pre_hour);
            timePicker.setCurrentMinute(pre_minute);
        }

        btn_alarm = findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour, hour_24, minute;
//                String am_pm;

//                if(Build.VERSION.SDK_INT >= 23){
//                    hour_24 = timePicker.getHour();
//                    minute = timePicker.getMinute();
//                } else{
//                    hour_24 = timePicker.getCurrentHour();
//                    minute = timePicker.getCurrentMinute();
//                }
//
//                if(hour_24 > 12){
//                    am_pm = "PM";
//                    hour = hour_24 -12;
//                }
//                else{
//                    hour = hour_24;
//                    am_pm = "AM";
//
//                }


                hour_24 = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                hour = hour_24;


                // 현재 지정된 시간으로 알람 시간 설정
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND,0);



                // 이미 지난 시간을 지정한다면 다음 날 같은 시간으로 설정
                if(calendar.before(Calendar.getInstance())){
                    calendar.add(Calendar.DATE,1);
                }

                Date currentTime2 = calendar.getTime();
                String time_text = new SimpleDateFormat("a hh시 mm분",Locale.getDefault()).format(currentTime2);

                Toast.makeText(getApplicationContext(), time_text+"으로 알람이 설정되었습니다.",Toast.LENGTH_SHORT).show();

                // Preference에 설정한 값 저장
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm",MODE_PRIVATE).edit();
                editor.putLong("nextTime",calendar.getTimeInMillis());
                editor.apply();


                diaryNotification(calendar);


                // intent로 값 전달하기
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("hh",hour);
                intent.putExtra("mm",minute);
//              intent.putExtra("a",am_pm);

                startActivity(intent);




                // handiMedResister.class
                Intent intent2 = new Intent(getApplicationContext(),handiMedResister.class );
                intent2.putExtra("hh2",hour);
                intent2.putExtra("mm2",minute);

                startActivity(intent2);
                finish();


            }
        }); // btn_alarm 끝


    }


    void diaryNotification(Calendar calendar) {

        Boolean dailyNotify = true;

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }


            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }


  }

    public void alarmCancel(View v ){
        finish();
    }


}