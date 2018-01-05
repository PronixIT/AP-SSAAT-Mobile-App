package com.yvs.ssaat.models;

/**
 * Created by NAVEEN KS on 12/20/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yvs.ssaat.R;
import com.yvs.ssaat.pojo.Format4Apojo;
import com.yvs.ssaat.session.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class ReportsAdapter4A extends RecyclerView.Adapter<ReportsAdapter4A.MyViewHolder> {


    private List<Format4Apojo> workersList;
    private Context mCon;
    String subCategState="";
    SQLiteDatabase mDatabase;
    private ArrayList<Format4Apojo> checkList = new ArrayList<Format4Apojo>();
    SessionManager sessionManager;

    public ReportsAdapter4A(Context mCon, List<Format4Apojo> workersList) {
        this.workersList = workersList;
        this.mCon = mCon;
        this.sessionManager = new SessionManager(mCon);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_format4a_report, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Format4Apojo worker = workersList.get(position);
        holder.serialNo.setText(worker.getSno());
        holder.wageSeekerId.setText(worker.getWageSeekerId());
        holder.fullname.setText(worker.getFullname());
        holder.postOfficeBankAccDetails.setText(worker.getPostBank());
        holder.allWorkDetails.setText(worker.getWork_details());
        holder.workDuration.setText(worker.getWork_duration());
        holder.payOrderReleaseDate.setText(worker.getPayOrderRelDate());
        holder.musterId.setText(worker.getMusterId());
        holder.noOfWorkingDays.setText(worker.getWorkedDays());
        holder.amtToBePaid.setText(worker.getAmtToBePaid());
        holder.actualWorkedDays.setText(worker.getActualWorkedDays());
        holder.actualAmtPaid.setText(worker.getActualAmtPaid());
        holder.diffInAmtPaid.setText(worker.getDifferenceInAmt());
        holder.respPersonDesig.setText(worker.getRespPersonDesig());
        holder.comments.setText(worker.getComments());
        holder.isJobCardAvailSpinner.setText(worker.getIsJobCardAvail());
        holder.isPassbookAvailSpinner.setText(worker.getIsPassbookAvail());
        holder.isPaylipIssuedSpinner.setText(worker.getIsPayslipIssued());
        holder.mainCategorySpinner.setText(worker.getCategoryone());
        holder.subCategorySpinner1.setText(worker.getCategorytwo());
        holder.subCategorySpinner2.setText(worker.getCategorythree());
    }

    @Override
    public int getItemCount() {
        return workersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView serialNo, wageSeekerId,fullname,postOfficeBankAccDetails,allWorkDetails,workDuration,
                payOrderReleaseDate,musterId,noOfWorkingDays,amtToBePaid,
         actualWorkedDays,actualAmtPaid,diffInAmtPaid,respPersonName,respPersonDesig,comments,
        isJobCardAvailSpinner,isPassbookAvailSpinner,isPaylipIssuedSpinner,mainCategorySpinner,subCategorySpinner1,subCategorySpinner2;


        public MyViewHolder(View view) {
            super(view);
            serialNo = view.findViewById(R.id.serialNo);
            wageSeekerId = view.findViewById(R.id.wageSeekerId);
            fullname = view.findViewById(R.id.fullname);
            postOfficeBankAccDetails = view.findViewById(R.id.postOfficeBankAccDetails);
            allWorkDetails = view.findViewById(R.id.allWorkDetails);
            workDuration = view.findViewById(R.id.workDuration);
            payOrderReleaseDate = view.findViewById(R.id.payOrderReleaseDate);
            musterId = view.findViewById(R.id.musterId);
            noOfWorkingDays = view.findViewById(R.id.noOfWorkingDays);
            amtToBePaid = view.findViewById(R.id.amtToBePaid);
            actualWorkedDays = view.findViewById(R.id.actualWorkedDays);
            actualAmtPaid = view.findViewById(R.id.actualAmtPaid);
            diffInAmtPaid = view.findViewById(R.id.diffInAmtPaid);
            isJobCardAvailSpinner = view.findViewById(R.id.isJobCardAvailSpinner);
            isPassbookAvailSpinner = view.findViewById(R.id.isPassbookAvailSpinner);
            isPaylipIssuedSpinner = view.findViewById(R.id.isPaylipIssuedSpinner);
            respPersonName = view.findViewById(R.id.respPersonName);
            respPersonDesig = view.findViewById(R.id.respPersonDesig);
            mainCategorySpinner = view.findViewById(R.id.mainCategorySpinner);
            subCategorySpinner1 = view.findViewById(R.id.subCategorySpinner1);
            subCategorySpinner2 = view.findViewById(R.id.subCategorySpinner2);
            comments = view.findViewById(R.id.comments);
        }

        public void setFilter(List<Format4Apojo> lrModels) {
            workersList = new ArrayList<>();
            workersList.addAll(lrModels);
            notifyDataSetChanged();
        }
    }
}