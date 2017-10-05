package com.scrounger.countrycurrencypicker.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;


public class CCFragment extends DialogFragment {
    private final static String logTAG = CCFragment.class.getName() + ".";
    public final String DIALOG_NAME = CCFragment.class.getName();

    //region Member
    private View myView;

    private CCPickerListener mListener;

    private EditText txtSearch;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CCAdapter mAdapter;

    //endregion

    //region Constructor
    public CCFragment() {
    }

    public static CCFragment newInstance(CCPickerListener listener) {
        CCFragment picker = new CCFragment();
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
        mRecyclerView = (RecyclerView) myView.findViewById(R.id.recycler);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        setRecyclerView(CCItem.listAll(getActivity()));

    }
    //endregion

    private void setRecyclerView(ArrayList<CCItem> ccItemArrayList) {
        if (ccItemArrayList == null) {
            mAdapter = new CCAdapter(new ArrayList<CCItem>());
        } else {
            mAdapter = new CCAdapter(new ArrayList<>(ccItemArrayList));
        }
        mRecyclerView.setAdapter(mAdapter);
    }
}
