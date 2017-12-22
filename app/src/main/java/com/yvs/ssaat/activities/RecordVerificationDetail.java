package com.yvs.ssaat.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yvs.ssaat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordVerificationDetail extends BaseActivity {

    @BindView(R.id.adhar_no)
    RadioButton aadhar_no;

    @BindView(R.id.btn_save)
    Button btn_save;

    @BindView(R.id.edt_denim)
    EditText edt_denim;
    @BindView(R.id.edt_ration)
    EditText edt_ration;

    @BindView(R.id.edt_workidno)
    EditText edt_workidno;
    @BindView(R.id.edt_issue)
    EditText edt_issue;

    @BindView(R.id.adhar_yes)
    RadioButton adhar_yes;

    @BindView(R.id.ration_no)
    RadioButton ration_no;

    @BindView(R.id.ration_yes)
    RadioButton ration_yes;

    @BindView(R.id.work_no)
    RadioButton work_no;

    @BindView(R.id.work_yes)
    RadioButton work_yes;

    @BindView(R.id.issue_no)
    RadioButton issue_no;
    @BindView(R.id.issue_yes)
    RadioButton issue_yes;

    @OnClick(R.id.adhar_no)
    public void click_adhar_no() {
        edt_denim.setVisibility(View.GONE);

    }

    @OnClick(R.id.btn_save)
    public void click_btn_save() {
        Toast.makeText(RecordVerificationDetail.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @OnClick(R.id.adhar_yes)
    public void click_adhar_yes() {
        edt_denim.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.ration_no)
    public void click_ration_no() {
        edt_ration.setVisibility(View.GONE);

    }

    @OnClick(R.id.ration_yes)
    public void click_ration_yes() {
        edt_ration.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.work_no)
    public void click_workid_no() {
        edt_workidno.setVisibility(View.GONE);

    }

    @OnClick(R.id.work_yes)
    public void click_workid_yes() {
        edt_workidno.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.issue_no)
    public void click_issue_no() {
        edt_issue.setVisibility(View.GONE);

    }

    @OnClick(R.id.issue_yes)
    public void click_issue_yes() {
        edt_issue.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_recordverification_detail, frame_base);
        ButterKnife.bind(this);
    }
}
