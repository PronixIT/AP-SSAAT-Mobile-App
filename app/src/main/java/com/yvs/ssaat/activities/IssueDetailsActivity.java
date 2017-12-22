package com.yvs.ssaat.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.yvs.ssaat.R;

public class IssueDetailsActivity extends BaseActivity {
    private LinearLayout one_ll, two_ll, three_ll;
    private Spinner spin_ll;
    private AppCompatButton save, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_issue_detail, frame_base);

        one_ll = (LinearLayout) findViewById(R.id.one_ll);
        two_ll = (LinearLayout) findViewById(R.id.two_ll);
        three_ll = (LinearLayout) findViewById(R.id.three_ll);
        spin_ll = (Spinner) findViewById(R.id.spin_ll);
        save = (AppCompatButton) findViewById(R.id.save);
        submit = (AppCompatButton) findViewById(R.id.submit);
        spin_ll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spin_ll.getSelectedItem().toString().equals("Muster Related")) {

                    one_ll.setVisibility(View.VISIBLE);
                    two_ll.setVisibility(View.GONE);
                    three_ll.setVisibility(View.GONE);

                } else if (spin_ll.getSelectedItem().toString().equals("Work Related")) {

                    one_ll.setVisibility(View.GONE);
                    two_ll.setVisibility(View.VISIBLE);
                    three_ll.setVisibility(View.GONE);

                } else if (spin_ll.getSelectedItem().toString().equals("Payment Related")) {
                    one_ll.setVisibility(View.GONE);
                    two_ll.setVisibility(View.GONE);
                    three_ll.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(IssueDetailsActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(IssueDetailsActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
