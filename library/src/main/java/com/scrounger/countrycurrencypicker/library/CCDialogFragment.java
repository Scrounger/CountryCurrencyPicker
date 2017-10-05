package com.scrounger.countrycurrencypicker.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class CCDialogFragment extends DialogFragment {
    private final static String logTAG = CCDialogFragment.class.getName() + ".";
    public final String DIALOG_NAME = CCDialogFragment.class.getName();

    //region Member
    private View myView;

    private CCPickerListener mListener;

    private EditText txtSearch;

    //endregion

    //region Constructor
    public CCDialogFragment() {
    }

    public static CCDialogFragment newInstance(CCPickerListener listener) {
        CCDialogFragment picker = new CCDialogFragment();
        picker.mListener = listener;
        return picker;
    }
    //endregion

    //region onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment, container, false);

        return myView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtSearch = (EditText) myView.findViewById(R.id.txt_search);

    }
    //endregion
}
