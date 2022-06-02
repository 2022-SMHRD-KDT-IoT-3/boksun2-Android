package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

public class nfcApp extends AppCompatActivity {

    private TextView txtType;
    private TextView txtSize;
    private TextView txtWrite;
    private TextView txtRead;
    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtType = findViewById(R.id.txtType);
        txtSize = findViewById(R.id.txtSize);
        txtWrite = findViewById(R.id.txtWrite);
        txtRead = findViewById(R.id.txtRead);
        nfcAdapter =  NfcAdapter.getDefaultAdapter(this) ;

        if (nfcAdapter == null) {
            //NFC 미지원단말
            Toast.makeText(getApplicationContext(), "NFC를 지원하지 않는 단말기입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        readFromTag(intent);
    }


    public void readFromTag(Intent intent){
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        try{
            ndef.connect();

            txtType.setText(ndef.getType().toString());
            txtSize.setText(String.valueOf(ndef.getMaxSize()));
            txtWrite.setText(ndef.isWritable() ? "True" : "False");
            Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (messages != null) {
                NdefMessage[] ndefMessages = new NdefMessage[messages.length];
                for (int i = 0; i < messages.length; i++) {
                    ndefMessages[i] = (NdefMessage) messages[i];
                }
                NdefRecord record = ndefMessages[0].getRecords()[0];

                byte[] payload = record.getPayload();
                String text = new String(payload);
                txtRead.setText(text);


                ndef.close();

            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot Read From Tag.", Toast.LENGTH_LONG).show();
        }
    }

//    인코딩 부분필요없음 없어도 자체적으로 한글 저장된 값 잘 뽑아와줌. 데이터 가공 후 삭제여부 결정
//    public void setReadTagData(NdefMessage ndefmsg) {
//        if(ndefmsg == null ) {
//            return ;
//        }
//
//        String msgs = "";
//
//        msgs += ndefmsg.toString() + "\n";
//
//        NdefRecord [] records = ndefmsg.getRecords() ;
//
//        for(NdefRecord rec : records) {
//
//            byte [] payload = rec.getPayload() ;
//
//            String textEncoding = "UTF-8" ;
//
//            if(payload.length > 0)
//
//                textEncoding = ( payload[0] & 0200 ) == 0 ? "UTF-8" : "UTF-16";
//
//
//
//            Short tnf = rec.getTnf();
//
//            String type = String.valueOf(rec.getType());
//
//            String payloadStr = new String(rec.getPayload(), Charset.forName(textEncoding));
//
//        }
//
//    }

}