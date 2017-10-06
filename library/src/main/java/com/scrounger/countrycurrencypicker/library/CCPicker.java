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
import android.widget.ProgressBar;

import java.util.ArrayList;


public class CCPicker extends DialogFragment {
    private final static String logTAG = CCPicker.class.getName() + ".";
    public static final String DIALOG_NAME = CCPicker.class.getName();

    //region Member
    private View myView;

    private Object mListener;

    private EditText txtSearch;
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CCAdapter mAdapter;

    private FilterListAsync filterListAsync;
    //endregion

    //region Constructor
    public CCPicker() {
    }

    public static CCPicker newInstance(CountryPickerListener listener) {
        CCPicker picker = new CCPicker();
        picker.mListener = listener;
        return picker;
    }

    public static CCPicker newInstance(CountryAndCurrenciesPickerListener listener) {
        CCPicker picker = new CCPicker();
        picker.mListener = listener;
        return picker;
    }

    public static CCPicker newInstance(CurrencyPickerListener listener) {
        CCPicker picker = new CCPicker();
        picker.mListener = listener;
        return picker;
    }

    public static CCPicker newInstance(CurrencyAndCountriesPickerListener listener) {
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
        progressBar = (ProgressBar) myView.findViewById(R.id.progressbar);
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
                if (txtSearch.hasFocus()) {
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

    private void setRecyclerView(ArrayList<Country> countryList, ArrayList<Currency> currencyList) {
        if (countryList == null && currencyList == null) {
            mAdapter = new CCAdapter(new ArrayList<Country>(), new ArrayList<Currency>(), mListener);
        } else {
            mAdapter = new CCAdapter(countryList, currencyList, mListener);
        }
        mRecyclerView.setAdapter(mAdapter);
    }
    //endregion

    private class FilterListAsync extends AsyncTask<String, Void, Void> {
        private ArrayList<Country> mCountryList = null;
        private ArrayList<Currency> mCurrencyList = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            for (String filterString : strings) {

                if (mListener instanceof CountryPickerListener) {
                    mCountryList = Country.listAll(getActivity(), filterString);
                } else if (mListener instanceof CountryAndCurrenciesPickerListener) {
                    mCountryList = Country.listAllWithCurrencies(getActivity(), filterString);
                } else if (mListener instanceof CurrencyPickerListener) {
                    mCurrencyList = Currency.listAll(getActivity(), filterString);
                } else if (mListener instanceof CurrencyAndCountriesPickerListener) {
                    mCurrencyList = Currency.listAllWithCountries(getActivity(), filterString);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setRecyclerView(mCountryList, mCurrencyList);
            progressBar.setVisibility(View.GONE);
        }
    }
}
