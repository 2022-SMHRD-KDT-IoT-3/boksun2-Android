package com.example.boksun3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragementAdminUseradd extends Fragment {

    private Button btn_address;
    private EditText edt_handi_ad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_useradd, container,false);

        edt_handi_ad = fragement.findViewById(R.id.edt_handi_ad);

        btn_address = fragement.findViewById(R.id.btn_address);
        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), addressSearch.class);

                startActivity(intent);
            }
        });

        // 주소 intent로 가져오기
        Intent intent = new Intent(); // 맞나 ?
        String address = intent.getStringExtra("address");
        edt_handi_ad.setText(address);


        return fragement;


    }
}
