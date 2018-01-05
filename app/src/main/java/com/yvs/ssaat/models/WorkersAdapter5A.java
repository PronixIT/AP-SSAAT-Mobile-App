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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.Dal.DalDoorToDoorResults;
import com.yvs.ssaat.Dal.DalWorksiteResults;
import com.yvs.ssaat.R;
import com.yvs.ssaat.activities.CSVParsing;
import com.yvs.ssaat.common.Constants;
import com.yvs.ssaat.pojo.WorkSitePOJO;
import com.yvs.ssaat.pojo.Worker;
import com.yvs.ssaat.session.SessionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WorkersAdapter5A extends RecyclerView.Adapter<WorkersAdapter5A.MyViewHolder> {

    private List<WorkSitePOJO> workersList;
    private Context mCon;
    String subCategState="";
    SQLiteDatabase mDatabase;
    private ArrayList<WorkSitePOJO> checkList = new ArrayList<WorkSitePOJO>();
    SessionManager sessionManager;
    DalWorksiteResults dalWorksiteResults;

    public WorkersAdapter5A(Context mCon, List<WorkSitePOJO> workersList) {
        this.workersList = workersList;
        this.mCon = mCon;
        this.sessionManager = new SessionManager(mCon);
        dalWorksiteResults = new DalWorksiteResults();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_format5a, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final WorkSitePOJO worksite = workersList.get(position);
        holder.serialNo.setText(worksite.getSno());
        holder.work_code.setText(worksite.getWork_code());
        holder.allWorkDetails.setText(worksite.getWork_name()+"/"+worksite.getWork_location());
        holder.taskDetails.setText(worksite.getTask_code()+"/"+worksite.getTask_name());
        holder.techType.setText(worksite.getSkill_type());
        holder.ap_measure.setText(worksite.getQty_sanc());
        holder.ap_total.setText(worksite.getAmount_sanc());
        holder.amb_measure.setText(worksite.getQty_done());
        holder.amb_total.setText(worksite.getAmount_spent());
        holder.isworkDoneSpinner.setSelection(worksite.getIs_workdone().toUpperCase().equals("YES") ? 0 : 1, false);
        holder.cv_measure.setText(worksite.getCheckvalues_measurment());
        holder.cv_total.setText(worksite.getCheckvalues_total());
        holder.diff_measure.setText(worksite.getDifference_measurments());
        holder.diff_total.setText(worksite.getDifference_total());
        holder.respPersonName.setText(worksite.getResp_personnam());
        holder.respPersonDesig.setText(worksite.getResp_person_jobdesg());
        holder.imp_of_work.setText(worksite.getImpofwork());
        holder.comments.setText(worksite.getComments());

//        holder.updateWorkerDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    String created_time = "";
//                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
//                    Calendar cal = Calendar.getInstance();
//                    System.out.println("created timestamp"+dateFormat.format(cal.getTime()));
//                    //created_time = dateFormat.format(cal.getTime());
//                    created_time = dateFormat.format(cal.getTime());
//                    mDatabase = mCon.openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
//
////                    String insertSQL = "INSERT INTO format5A \n" +
////                            "(sno, work_code , work_details, task_details,technology_type,approved_ev_measurements,approved_ev_total ," +
////                            "aspermb_report_measurements,aspermb_report_total,is_work_done,chechvalues_measurements," +
////                            "chechvalues_total, difference_measurements, difference_total, respPersonName,respPersonDesig," +
////                            "imp_of_work, comments, created_date, created_by, modified_date,modified_by,isActive)\n" +
////                            "VALUES \n" +
////                            "(?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?,?,?,?,?, ?,?);";
////                    mDatabase.execSQL(insertSQL,
////                            new String[]{
//                    long result = dalWorksiteResults.insertOrUpdateWorksiteResultData(mDatabase,
//                                    holder.serialNo.getText().toString(),
//                                    holder.work_code.getText().toString(),
//                                    holder.allWorkDetails.getText().toString(),
//                                    holder.taskDetails.getText().toString(),
//                                    holder.techType.getText().toString(),
//                                    holder.ap_measure.getText().toString(),
//                                    holder.ap_total.getText().toString(),
//                                    holder.amb_measure.getText().toString(),
//                                    holder.amb_total.getText().toString(),
//                                    holder.isworkDoneSpinner.getSelectedItem().toString(),
//                                    holder.cv_measure.getText().toString(),
//                                    holder.cv_total.getText().toString(),
//                                    holder.diff_measure.getText().toString(),
//                                    holder.diff_total.getText().toString(),
//                                    holder.respPersonName.getText().toString(),
//                                    holder.respPersonDesig.getText().toString(),
//                                    holder.imp_of_work.getText().toString(),
//                                    holder.comments.getText().toString(),
//                                    created_time,
//                            Constants.userMasterDO.userName,new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(new Date()), Constants.userMasterDO.userName,""
//                                    ,worksite.getTask_code());
//
//                    if(result != 0 && result != -1)
//                        Toast.makeText(mCon,"Details saved successfully",Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(mCon,"Failed to save details",Toast.LENGTH_SHORT).show();
//                }catch (SQLiteException error){
//                    Toast.makeText(mCon,"Failed to save details",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return workersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView serialNo, work_code, allWorkDetails,taskDetails,techType,ap_measure,ap_total,amb_measure,amb_total;
        EditText cv_measure,cv_total,diff_measure,diff_total,
                respPersonName,respPersonDesig,imp_of_work,comments;
        Button updateWorkerDetails;
        Spinner isworkDoneSpinner;

        public MyViewHolder(View view) {
            super(view);
            serialNo = view.findViewById(R.id.serialNo);
            work_code = view.findViewById(R.id.work_code);
            allWorkDetails = view.findViewById(R.id.allWorkDetails);
            taskDetails = view.findViewById(R.id.taskDetails);
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
//            updateWorkerDetails = view.findViewById(R.id.updateWorkerDetails);
        }

        public void setFilter(List<WorkSitePOJO> lrModels) {
            workersList = new ArrayList<>();
            workersList.addAll(lrModels);
            notifyDataSetChanged();
        }
    }
}