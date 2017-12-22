package com.yvs.ssaat.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yvs.ssaat.R;
import com.yvs.ssaat.pojo.TableCnsts;

import java.util.ArrayList;

/**
 * Created by surya on 11/1/2017.
 */

public class TicketDetailsAdapter extends BaseAdapter {
    LayoutInflater inflter;
    ArrayList<TableCnsts> tableCnstsArrayList;

    public TicketDetailsAdapter(Context applicationContext, ArrayList<TableCnsts> tableCnstsArrayList1) {
        this.tableCnstsArrayList = tableCnstsArrayList1;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return tableCnstsArrayList.size();
    }

    @Override
    public TableCnsts getItem(int i) {
        return tableCnstsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.adapter_ticketdetails, null);
        TextView txt_sno = (TextView) view.findViewById(R.id.txt_sno);
        TextView txt_ticketnumber = (TextView) view.findViewById(R.id.txt_ticketnumber);
        TextView end_line = (TextView) view.findViewById(R.id.end_line);
        LinearLayout ll_header = (LinearLayout) view.findViewById(R.id.ll_header);
        if (i == 0) {
            end_line.setVisibility(View.VISIBLE);
            ll_header.setVisibility(View.VISIBLE);
        } else {
            end_line.setVisibility(View.GONE);
            ll_header.setVisibility(View.GONE);
        }
        TableCnsts tableCnsts = (TableCnsts) tableCnstsArrayList.get(i);
        txt_sno.setText(tableCnsts.getId());
        txt_ticketnumber.setText(tableCnsts.getTicknumber());

        return view;
    }
}

