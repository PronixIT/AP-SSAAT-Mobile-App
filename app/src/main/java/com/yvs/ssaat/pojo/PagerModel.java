package com.yvs.ssaat.pojo;

import com.yvs.ssaat.R;

/**
 * Created by NAVEEN KS on 11/6/2017.
 */

public enum PagerModel {

    RED(R.string.app_name, R.layout.view_recordverify),
    BLUE(R.string.app_name, R.layout.view_doortodoor),
    GREEN(R.string.app_name, R.layout.view_worksites);

    private int mTitleResId;
    private int mLayoutResId;

    PagerModel(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}

