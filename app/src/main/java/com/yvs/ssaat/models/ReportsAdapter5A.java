package com.yvs.ssaat.models;

/**
 * Created by NAVEEN KS on 12/20/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.yvs.ssaat.R;
import com.yvs.ssaat.pojo.Format4Apojo;
import com.yvs.ssaat.pojo.Format5Apojo;
import com.yvs.ssaat.session.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class ReportsAdapter5A extends RecyclerView.Adapter<ReportsAdapter5A.MyViewHolder> {


    private List<Format5Apojo> workersList;
    private Context mCon;
    String subCategState="";
    SQLiteDatabase mDatabase;
    private ArrayList<Format5Apojo> checkList = new ArrayList<Format5Apojo>();
    SessionManager sessionManager;

    public ReportsAdapter5A(Context mCon, List<Format5Apojo> workersList) {
        this.workersList = workersList;
        this.mCon = mCon;
        this.sessionManager = new SessionManager(mCon);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_format5a_report, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Format5Apojo worker = workersList.get(position);
        holder.serialNo.setText(worker.getSno());
        holder.work_code.setText(worker.getWork_code());
        holder.work_details.setText(worker.getWork_details());
        holder.taskDetails.setText(worker.getTask_details());
        holder.techType.setText(worker.getTechType());
        holder.ap_measure.setText(worker.getAp_measure());
        holder.ap_total.setText(worker.getAp_total());
        holder.amb_measure.setText(worker.getAmb_measure());
        holder.amb_total.setText(worker.getAmb_total());
        holder.cv_measure.setText(worker.getCv_measure());
        holder.cv_total.setText(worker.getCv_total());
        holder.diff_measure.setText(worker.getDiff_measure());
        holder.diff_total.setText(worker.getDiff_total());
        holder.respPersonName.setText(worker.getRespPersonName());
        holder.respPersonDesig.setText(worker.getRespPersonDesig());
        holder.imp_of_work.setText(worker.getImp_of_work());
        holder.isworkDoneSpinner.setText(worker.getIs_work_done());
        holder.comments.setText(worker.getComments());
    }

    @Override
    public int getItemCount() {
        return workersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView serialNo, work_code, work_details,taskDetails,techType,
           ap_measure,ap_total,amb_measure,amb_total,cv_measure,cv_total,diff_measure,diff_total,
                respPersonName,respPersonDesig,imp_of_work,comments,isworkDoneSpinner;


        public MyViewHolder(View view) {
            super(view);
            serialNo = view.findViewById(R.id.serialNo);
            work_code = view.findViewById(R.id.work_code);
            work_details = view.findViewById(R.id.work_details);
            taskDetails = view.findViewById(R.id.task_details);
            techType = view.findViewById(R.id.techType);
            ap_measure = view.findViewById(R.id.ap_measure);
            ap_total = view.findViewById(R.id.ap_total);
            amb_measure = view.findViewById(R.id.amb_measure);
            amb_total = view.findViewById(R.id.amb_total);
            isworkDoneSpinner = view.findViewById(R.id.isworkDoneSpinner);
            cv_measure = view.findViewById(R.id.cv_measure);
            cv_total = view.findViewById(R.id.cv_total);
            diff_measure = view.findViewById(R.id.diff_measure);
            diff_total = view.findViewById(R.id.diff_total);
            respPersonName = view.findViewById(R.id.respPersonName);
            respPersonDesig = view.findViewById(R.id.respPersonDesig);
            imp_of_work = view.findViewById(R.id.imp_of_work);
            comments = view.findViewById(R.id.comments);

        }

        public void setFilter(List<Format5Apojo> lrModels) {
            workersList = new ArrayList<>();
            workersList.addAll(lrModels);
            notifyDataSetChanged();
        }
    }
}