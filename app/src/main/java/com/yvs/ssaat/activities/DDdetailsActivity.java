package com.yvs.ssaat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yvs.ssaat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DDdetailsActivity extends BaseActivity {

    @BindView(R.id.adhar_no)
    RadioButton aadhar_no;
    @BindView(R.id.issues)
    AppCompatTextView issues;

 @BindView(R.id.exit)
    AppCompatTextView exit;

    @BindView(R.id.noissue)
    AppCompatTextView noissue;


    @BindView(R.id.adhar_yes)
    RadioButton adhar_yes;

    @BindView(R.id.ll_no)
    LinearLayout ll_no;

    @BindView(R.id.ll_yes)
    LinearLayout ll_yes;

    @OnClick(R.id.issues)
    public void click_issues() {
        Intent intent = new Intent(DDdetailsActivity.this, IssueDetailsActivity.class);
        startActivity(intent);
        DDdetailsActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @OnClick(R.id.exit)
    public void click_exit() {
       finish();
    }

    @OnClick(R.id.adhar_no)
    public void click_adhar_no() {
        ll_no.setVisibility(View.VISIBLE);
        ll_yes.setVisibility(View.GONE);

    }

    @OnClick(R.id.adhar_yes)
    public void click_adhar_yes() {

        ll_yes.setVisibility(View.VISIBLE);
        ll_no.setVisibility(View.GONE);
    }
   @OnClick(R.id.noissue)
    public void click_noissue() {

       Toast.makeText(this, "Thank you", Toast.LENGTH_SHORT).show();
       finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_dddetails, frame_base);
        ButterKnife.bind(this);
    }
}
