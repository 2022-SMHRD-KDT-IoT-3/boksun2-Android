package com.example.boksun3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragementAdminSet extends Fragment {

    private Button btn_logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_set, container,false);

        // 자동로그인 기능
        btn_logout = fragement.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 자동로그인 해제
                SharedPreferencesManager.clearPreferences(getContext());

                // 메인으로 이동
                Intent intent = new Intent(getContext(), adminMainActivity.class);
                intent.putExtra("logout","logout");
                startActivity(intent);


            }
        });
        
        return fragement;
    }
}
