package com.yvs.ssaat.models;

/**
 * Created by NAVEEN KS on 12/20/2017.
 */

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.yvs.ssaat.R;
import com.yvs.ssaat.pojo.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkersAdapter extends RecyclerView.Adapter<WorkersAdapter.MyViewHolder> {

    private List<Worker> workersList;
    private Context mCon;
    String subCategState="";

    public WorkersAdapter(Context mCon, List<Worker> workersList) {
        this.workersList = workersList;
        this.mCon = mCon;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_format4a, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Worker worker = workersList.get(position);
        holder.workCardId.setText(worker.getWork_code());

        String[] maincategories = mCon.getResources().getStringArray(R.array.maincategories);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(mCon,android.R.layout.simple_list_item_1, maincategories);
        holder.mainCategorySpinner.setAdapter(adapter);

        holder.mainCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //loadsecondspinner
                String[] subcategories1;
                if(i==0){
                    subCategState = "Category1";
                    subcategories1 = mCon.getResources().getStringArray(R.array.Category1);
                } else  if(i==1){
                    subCategState = "Category2";
                    subcategories1 = mCon.getResources().getStringArray(R.array.Category2);
                } else  if(i==2){
                    subCategState = "Category3";
                    subcategories1 = mCon.getResources().getStringArray(R.array.Category3);
                } else{
                    subCategState = "Category4";
                    subcategories1 = mCon.getResources().getStringArray(R.array.Category4);
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(mCon,android.R.layout.simple_list_item_1, subcategories1);
                holder.subCategorySpinner1.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.subCategorySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //loadsecondspinner
                String[] subcategories2 = new String[0];
                if(subCategState.equalsIgnoreCase("Category1")){
                    if(i==0){
                        subcategories2 = mCon.getResources().getStringArray(R.array.IssueCategory11);
                    } else  if(i==1){
                        subcategories2 = mCon.getResources().getStringArray(R.array.IssueCategory12);
                    } else if(i==2){
                        subcategories2 = mCon.getResources().getStringArray(R.array.IssueCategory13);
                    }
                }else if(subCategState.equalsIgnoreCase("Category2")){
                    if(i==0){
                        subcategories2 = mCon.getResources().getStringArray(R.array.procedural1);
                    } else  if(i==1){
                        subcategories2 = mCon.getResources().getStringArray(R.array.procedural2);
                    } else  if(i==2){
                        subcategories2 = mCon.getResources().getStringArray(R.array.procedural3);
                    } else if(i==3){
                        subcategories2 = mCon.getResources().getStringArray(R.array.procedural4);
                    }
                }else if(subCategState.equalsIgnoreCase("Category3")){
                     subcategories2 = mCon.getResources().getStringArray(R.array.information1);
                }else if(subCategState.equalsIgnoreCase("Category4")){
                    if(i==0){
                        subcategories2 = mCon.getResources().getStringArray(R.array.grievances1);
                    } else if(i==1){
                        subcategories2 = mCon.getResources().getStringArray(R.array.grievances2);
                    }
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(mCon,android.R.layout.simple_list_item_1, subcategories2);
                holder.subCategorySpinner2.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.updateWorkerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Selected Values: "+holder.mainCategorySpinner.getSelectedItem().toString()+"---"+holder.subCategorySpinner1.getSelectedItem().toString()+
                "-------"+holder.subCategorySpinner2.getSelectedItem().toString());
                String sql = "UPDATE employees \n" +
                        "SET name = ?, \n" +
                        "department = ?, \n" +
                        "salary = ? \n" +
                        "WHERE id = ?;\n";
//
//                mDatabase.execSQL(sql, new String[]{name, dept, salary, String.valueOf(employee.getId())});

            }
        });

    }

    @Override
    public int getItemCount() {
        return workersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView workCardId;
        Spinner mainCategorySpinner,subCategorySpinner1,subCategorySpinner2;
        Button updateWorkerDetails;

        public MyViewHolder(View view) {
            super(view);
            workCardId = (TextView) view.findViewById(R.id.workCardId);
            mainCategorySpinner = view.findViewById(R.id.mainCategorySpinner);
            subCategorySpinner1 = view.findViewById(R.id.subCategorySpinner1);
            subCategorySpinner2 = view.findViewById(R.id.subCategorySpinner2);
            updateWorkerDetails = view.findViewById(R.id.updateWorkerDetails);
        }

        public void setFilter(List<Worker> lrModels) {
            workersList = new ArrayList<>();
            workersList.addAll(lrModels);
            notifyDataSetChanged();
        }
    }
}