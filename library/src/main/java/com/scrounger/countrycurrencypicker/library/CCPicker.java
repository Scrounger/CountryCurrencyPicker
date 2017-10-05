package com.scrounger.countrycurrencypicker.library;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;


public class CCPicker extends DialogFragment {
    private final static String logTAG = CCPicker.class.getName() + ".";
    public static final String DIALOG_NAME = CCPicker.class.getName();

    //region Member
    private View myView;

    private CCPickerListener mListener;

    private EditText txtSearch;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CCAdapter mAdapter;

    private FilterListAsync filterListAsync;
    //endregion

    //region Constructor
    public CCPicker() {
    }

    public static CCPicker newInstance(CCPickerListener listener) {
        CCPicker picker = new CCPicker();
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

        EventsListener();
    }
    //endregion

    //region Events
    @Override
    public void onStart() {
        super.onStart();
        getData(null);
    }

    private void EventsListener() {
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);
                return false;
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtSearch.hasFocus() && txtSearch.length() > 0) {
                    getData(editable.toString());
                }
            }
        });
    }
    //endregion

    //region Functions
    private void getData(String filterString) {
        if (filterListAsync == null) {
            filterListAsync = (FilterListAsync) new FilterListAsync().execute(filterString);
        } else {
            filterListAsync.cancel(true);
            filterListAsync = (FilterListAsync) new FilterListAsync().execute(filterString);
        }
    }

    private void setRecyclerView(ArrayList<CCItem> ccItemArrayList) {
        if (ccItemArrayList == null) {
            mAdapter = new CCAdapter(new ArrayList<CCItem>(), mListener);
        } else {
            mAdapter = new CCAdapter(new ArrayList<>(ccItemArrayList), mListener);
        }
        mRecyclerView.setAdapter(mAdapter);
    }
    //endregion

    private class FilterListAsync extends AsyncTask<String, Void, ArrayList<CCItem>> {

        @Override
        protected ArrayList<CCItem> doInBackground(String... strings) {
            ArrayList<CCItem> list = null;
            for (String filterString : strings) {
                list = CCItem.getFilteredList(getActivity(), filterString);
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<CCItem> result) {
            super.onPostExecute(result);
            setRecyclerView(result);
        }
    }
}
