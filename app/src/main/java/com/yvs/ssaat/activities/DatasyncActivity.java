package com.yvs.ssaat.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yvs.ssaat.R;
import com.yvs.ssaat.db.DatabaseHandler;

import com.yvs.ssaat.models.TicketDetailsAdapter;
import com.yvs.ssaat.pojo.TableCnsts;
import com.yvs.ssaat.pojo.TicketsCnsts;

import java.util.ArrayList;

public class DatasyncActivity extends BaseActivity {
    private RelativeLayout rl_scanner;
    private TextView txt_ticketNumber, txt_submit;
    private DatabaseHandler db;
    private LinearLayout ll_submit;
    private ListView recycler_view;
    private ArrayList<TableCnsts> tableCnstsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_datasync, frame_base);

        txt_submit = (TextView) findViewById(R.id.txt_submit);
        ll_submit = (LinearLayout) findViewById(R.id.ll_submit);
        recycler_view = (ListView) findViewById(R.id.recycler_view);
        db = new DatabaseHandler(this);
        db = new DatabaseHandler(this);

        ArrayList<TicketsCnsts> contacts = db.getAllContacts();
        if (contacts.size() != 0) {
            tableCnstsArrayList = new ArrayList<>();
            for (TicketsCnsts cn : contacts) {
                TableCnsts tableCnsts = new TableCnsts();
                tableCnsts.setId(cn.getId());
                tableCnsts.setTicknumber(cn.getTicketnumber());
                tableCnstsArrayList.add(tableCnsts);
            }
            recycler_view.setAdapter(new TicketDetailsAdapter(DatasyncActivity.this, tableCnstsArrayList));
        }

        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TicketsCnsts ticketsCnsts = new TicketsCnsts();
                ticketsCnsts.setTicketnumber("1");
                ticketsCnsts.setName("name");
                db.addContact(ticketsCnsts);

                ArrayList<TicketsCnsts> contacts = db.getAllContacts();
                if (contacts.size() != 0) {
                    tableCnstsArrayList = new ArrayList<>();
                    for (TicketsCnsts cn : contacts) {
                        TableCnsts tableCnsts = new TableCnsts();
                        tableCnsts.setId(cn.getId());
                        tableCnsts.setTicknumber(cn.getTicketnumber());
                        tableCnstsArrayList.add(tableCnsts);
                    }
                    recycler_view.setAdapter(new TicketDetailsAdapter(DatasyncActivity.this, tableCnstsArrayList));
                }

            }
        });


    }
}
