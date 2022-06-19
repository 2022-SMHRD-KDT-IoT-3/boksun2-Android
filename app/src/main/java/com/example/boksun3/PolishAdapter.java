package com.example.boksun3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PolishAdapter extends BaseAdapter {
    private PolishVO vo;
    private final List<PolishVO> PolishData;
    private ArrayList<PolishVO> items = new ArrayList<>();

    public PolishAdapter(List<PolishVO> polishdata) {
        PolishData = polishdata;
    }

    public void addItem(String notice_type, String worker_id, String notice_title,
                        String notice_content, String notice_date, int notice_cnt){
        PolishVO vo = new PolishVO(notice_type, worker_id, notice_title, notice_content, notice_date, notice_cnt);
        items.add(vo);
    }
    @Override
    public int getCount() {
        return PolishData.size();
    }

    @Override
    public Object getItem(int position) {
        return PolishData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_polish_items, viewGroup, false);
        }
        CheckBox chk_check = view.findViewById(R.id.chk_check);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_contents = view.findViewById(R.id.tv_contents);
        TextView tv_polishDate = view.findViewById(R.id.tv_polishDate);
        TextView tv_cnt = view.findViewById(R.id.tv_cnt);

        vo = PolishData.get(position);
        tv_title.setText(vo.getNotice_title());
        tv_contents.setText(vo.getNotice_contents());
        tv_polishDate.setText(vo.getNotice_date());
//        tv_cnt.setText(vo.getNotice_cnt());

        return view;
    }
}
