package com.example.boksun3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class adminJoin extends AppCompatActivity {

    TextView tv_address;
    EditText edt_id, edt_pw, edt_name, edt_phone, edt_license, edt_com, edt_comTel;
    Button btn_idCheck, btn_submit;

    // volley
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_bar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setContentView(R.layout.activity_admin_join);


        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        edt_name = findViewById(R.id.tv_info_name);
        edt_phone = findViewById(R.id.tv_info_phone);
        edt_license = findViewById(R.id.tv_info_emer);
        edt_com = findViewById(R.id.tv_info_date);
        edt_comTel = findViewById(R.id.edt_comTel);

        btn_idCheck = findViewById(R.id.btn_idCheck);
        btn_submit = findViewById(R.id.btn_info_ok);

        edt_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        edt_comTel.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        btn_idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestIdCheck();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestJoinWorker();
            }
        });



        // ???????????? intent??? ????????????
        Spinner s1 = findViewById(R.id.spinner1); // ??? ?????? spinner
        Spinner s2 = findViewById(R.id.spinner2); // ?????? '???' spinner
        Spinner s3 = findViewById(R.id.spinner3); // ?????? '???' spinner

        tv_address = findViewById(R.id.tv_info_address);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (adapterView.getSelectedItem().equals("???????????????")) {
                        s2.setVisibility(View.VISIBLE);
                        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView2, View view, int k, long l) {
                                tv_address.setText(adapterView.getItemAtPosition(i).toString()
                                        + " " + adapterView2.getItemAtPosition(k).toString());
                                s2.setVisibility(View.INVISIBLE);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else if (adapterView.getSelectedItem().equals("???????????????")) {
                        s3.setVisibility(View.VISIBLE);
                        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView3, View view, int m, long l) {
                                tv_address.setText(adapterView.getItemAtPosition(i).toString()
                                        + " " + adapterView3.getItemAtPosition(m).toString());
                                s3.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void sendRequestIdCheck() {
        // RequestQueue ?????? ??????
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // ????????? ????????? ??????
        String url = "http://220.80.88.88:8081/controller/idCheckWorker.do"; // ??????????????? ????????????????????? ??? IPv4 ??????

        // ?????? ??? ????????? ????????? ??????(????????????, url, ?????????)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // ?????????????????? ???????????? ???
            @Override
            public void onResponse(String response) {
                Log.v("idCheck", response);

                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        // json array ????????? ?????? ??????, ?????? ??????
                        e.printStackTrace();
                    } finally {
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            // ???????????? ?????? ????????? ??????
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override // response??? UTF8??? ??????????????? ?????? ??????(??????????????? ????????? ?????????)
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            // ?????? ???????????? ???????????? ???
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String worker_id = edt_id.getText().toString();

                params.put("worker_id", worker_id);

                return params;
            }
        };
        stringRequest.setTag("idCheck");
        requestQueue.add(stringRequest);
    }

    public void sendRequestJoinWorker() {
        // RequestQueue ?????? ??????
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // ????????? ????????? ??????
        String url = "http://220.80.88.88:8081/controller/joinWorkerInsert.do"; // ??????????????? ????????????????????? ??? IPv4 ??????

        // ?????? ??? ????????? ????????? ??????(????????????, url, ?????????)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // ?????????????????? ???????????? ???
            @Override
            public void onResponse(String response) {
                Log.v("joinWorker", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), adminLogin.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            // ???????????? ?????? ????????? ??????
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override // response??? UTF8??? ??????????????? ?????? ??????(??????????????? ????????? ?????????)
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            // ?????? ???????????? ???????????? ???
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String worker_id = edt_id.getText().toString();
                String worker_pw = edt_pw.getText().toString();
                String worker_name = edt_name.getText().toString();
                String worker_phone = edt_phone.getText().toString();
                String worker_license = edt_license.getText().toString();
                String worker_organization = edt_com.getText().toString();
                String worker_tel = edt_comTel.getText().toString();
                String worker_area = tv_address.getText().toString();

                params.put("worker_id", worker_id);
                params.put("worker_pw", worker_pw);
                params.put("worker_name", worker_name);
                params.put("worker_phone", worker_phone);
                params.put("worker_license", worker_license);
                params.put("worker_organization", worker_organization);
                params.put("worker_tel", worker_tel);
                params.put("worker_area", worker_area);

                return params;
            }
        };
        stringRequest.setTag("joinWorker");
        requestQueue.add(stringRequest);
    }
}