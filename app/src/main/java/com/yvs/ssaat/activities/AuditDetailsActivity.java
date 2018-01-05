package com.yvs.ssaat.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yvs.ssaat.R;
import com.yvs.ssaat.models.AuditItemsAdapter;
import com.yvs.ssaat.pojo.AuditItems;

import java.util.ArrayList;

public class AuditDetailsActivity extends BaseActivity implements View.OnClickListener{
    private ListView list_items;
    private String[] values = { "Door to Door Audit", "Work Site","Record Verification",  "Data Sync"};
    private ArrayList<AuditItems> auditItemsArrayList;
    private Intent intent;
    RelativeLayout rl_D2DAudit, rl_WorksiteAudit, rl_RecordVerification, rl_Sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_auditdashboard, frame_base);
        initializeConrols();
//        list_items = (ListView) findViewById(R.id.list_items);
//        auditItemsArrayList = new ArrayList<>();
//        for (int i = 0; i < values.length; i++) {
//            AuditItems auditItems = new AuditItems();
//            auditItems.setId(String.valueOf(i));
//            auditItems.setLoc_name(values[i]);
//            auditItemsArrayList.add(auditItems);
//
//        }
//        list_items.setAdapter(new AuditItemsAdapter(AuditDetailsActivity.this, auditItemsArrayList));
//
//        list_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i) {
//                    case 0:
//                        intent = new Intent(AuditDetailsActivity.this, DoortoDoorAtivity.class);
//                        startActivity(intent);
//                        AuditDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                        break;
//
//                    case 1:
//                        intent = new Intent(AuditDetailsActivity.this, DoortoDoorF5Ativity.class);
//                        startActivity(intent);
//                        AuditDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                        break;
//
//                    case 2:
//                        intent = new Intent(AuditDetailsActivity.this, RecordVerificationActivity.class);
//                        startActivity(intent);
//                        AuditDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                        break;
//
//                    case 3:
//
//                      /*  intent = new Intent(AuditDetailsActivity.this, CSVParsing.class);
//                        startActivity(intent);
//                        AuditDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//*/
//
//                    /*    intent = new Intent(AuditDetailsActivity.this, DatasyncActivity.class);
//                        startActivity(intent);
//                        AuditDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//*/
//                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AuditDetailsActivity.this);
//                        alertDialog.setTitle("SSAAT");
//
//                        alertDialog.setMessage("DataSync Process Completed Successfully?");
//
//                        alertDialog.setIcon(R.mipmap.ic_launcher);
//
//                        alertDialog.setPositiveButton("OK.Gotit", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dialog.dismiss();
//                            }
//                        });
//
//                        alertDialog.show();
//                        break;
//                }
//            }
//        });

    }

    public void initializeConrols()
    {
        rl_D2DAudit = (RelativeLayout) findViewById(R.id.rl_D2DAudit);
        rl_D2DAudit.setOnClickListener(this);
        rl_WorksiteAudit = (RelativeLayout) findViewById(R.id.rl_WorksiteAudit);
        rl_WorksiteAudit.setOnClickListener(this);
        rl_RecordVerification = (RelativeLayout) findViewById(R.id.rl_RecordVerification);
        rl_RecordVerification.setOnClickListener(this);
        rl_Sync = (RelativeLayout) findViewById(R.id.rl_Sync);
        rl_Sync.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_D2DAudit:
                intent = new Intent(AuditDetailsActivity.this, DoortoDoorAtivity.class);
                startActivity(intent);
                AuditDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.rl_WorksiteAudit:
                intent = new Intent(AuditDetailsActivity.this, DoortoDoorF5Ativity.class);
                startActivity(intent);
                AuditDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.rl_RecordVerification:
                intent = new Intent(AuditDetailsActivity.this, RecordVerificationActivity.class);
                startActivity(intent);
                AuditDetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.rl_Sync:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AuditDetailsActivity.this);
                alertDialog.setTitle("SSAAT");

                alertDialog.setMessage("DataSync Process Completed Successfully?");

                alertDialog.setIcon(R.mipmap.ic_launcher);

                alertDialog.setPositiveButton("OK.Gotit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alertDialog.show();
                break;
        }
    }
}
