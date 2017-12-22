package com.yvs.ssaat.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.yvs.ssaat.R;
import com.yvs.ssaat.appcnsts.Helper;
import com.yvs.ssaat.pojo.AuditItems;

import java.util.ArrayList;

/**
 * Created by surya on 4/10/2017.
 */

public class AuditItemsAdapter extends BaseAdapter {
    private ArrayList<AuditItems> catCnstsArrayList;
    private Context context;
    private LayoutInflater inflate;

    public AuditItemsAdapter(Context context, ArrayList<AuditItems> catCnstsArrayList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.catCnstsArrayList = catCnstsArrayList;
        inflate = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return catCnstsArrayList.size();
    }

    @Override
    public AuditItems getItem(int position) {
        // TODO Auto-generated method stub
        return catCnstsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.adapter_audititems_item, null, true);
        }

        viewHolder.title_category = (TextView) convertView.findViewById(R.id.title_category);
        viewHolder.txt_rightarrow = (TextView) convertView.findViewById(R.id.txt_rightarrow);
        viewHolder.txt_rightarrow.setText(R.string.rightarrow);
        Helper.getTypeFace(context, context.getString(R.string.contentfontname), viewHolder.title_category);
        Helper.getTypeFace(context, context.getString(R.string.fontawsome), viewHolder.txt_rightarrow);

        AuditItems productCnsts = (AuditItems) catCnstsArrayList.get(position);
        viewHolder.title_category.setText(productCnsts.getLoc_name());


        return convertView;
    }

    public class ViewHolder {
        private TextView title_category, txt_rightarrow;
    }

}
