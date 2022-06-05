package com.example.boksun3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchLifeAdapter extends BaseAdapter {

    private UserVO vo;
    private final List<UserVO> LifeData;
    private Map<String, Integer> LifeImageMap;


    //condition에 따라 이미 매칭
    public SearchLifeAdapter(List<UserVO> lifedata) {
        LifeData = lifedata;
        LifeImageMap = new HashMap<>();
        //받아오는 값대로 사진파일 생성
        LifeImageMap.put("위험",R.drawable.life_danger);
        LifeImageMap.put("안전",R.drawable.life_safe);
    }


    @Override
    public int getCount() {
        return LifeData.size();
    }

    @Override
    public Object getItem(int position) {
        return LifeData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view == null){
            //row_life_item ->list뷰에 들어가는. xml
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_life_item, viewGroup, false);
        }
        ImageView img = view.findViewById(R.id.img_life);
        TextView name = view.findViewById(R.id.tv_name);
        TextView address = view.findViewById(R.id.tv_lifeaddress);
        TextView date = view.findViewById(R.id.tv_currentdate);
        TextView time = view.findViewById(R.id.tv_currenttime);

        vo = LifeData.get(position);

        Log.v("test", vo.getUser_name());

        name.setText(vo.getUser_name());
        address.setText(vo.getUser_addr());
        date.setText(vo.getDate());
        time.setText(vo.getTime());
        img.setImageResource(LifeImageMap.get(vo.getCondition()));

        return view;
    }

}
