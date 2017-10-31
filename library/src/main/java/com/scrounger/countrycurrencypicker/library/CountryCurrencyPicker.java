/*
 * Copyright (C) 2017 Scrounger
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scrounger.countrycurrencypicker.library;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;

import java.util.ArrayList;


public class CountryCurrencyPicker extends DialogFragment {
    private final static String logTAG = CountryCurrencyPicker.class.getName() + ".";
    public static final String DIALOG_NAME = CountryCurrencyPicker.class.getName();

    //region Member

    private static final String Bundle_PickerType = "com.scrounger.countrycurrencypicker.library.pickerType";
    private PickerType mPickerType;

    public enum PickerType {
        COUNTRY, COUNTRYandCURRENCY, CURRENCY, CURRENCYandCOUNTRY;
    }

    private View myView;

    private static final String Bundle_Listener = "com.scrounger.countrycurrencypicker.library.listener";
    private CountryCurrencyPickerListener mListener;

    private EditText txtSearch;
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CountryCurrencyAdapter mAdapter;

    private Boolean mShowSubTitle = true;
    private Boolean mShowCodeOrCurrency = true;

    private FilterListAsync filterListAsync;

    private String dialogTitle;

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }
    //endregion

    //region Constructor
    public CountryCurrencyPicker() {
    }

    public static CountryCurrencyPicker newInstance(@NonNull PickerType pickerType, @NonNull CountryCurrencyPickerListener countryCurrencyPickerListener) {
        CountryCurrencyPicker picker = new CountryCurrencyPicker();

        Bundle bdl = new Bundle();
        bdl.putSerializable(Bundle_PickerType, pickerType);
        bdl.putSerializable(Bundle_Listener, countryCurrencyPickerListener);
        picker.setArguments(bdl);

        return picker;
    }

    public static CountryCurrencyPicker newInstance(@NonNull PickerType pickerType, @NonNull Boolean showSubtitle, @NonNull Boolean showCodeOrCurrency, @NonNull CountryCurrencyPickerListener countryCurrencyPickerListener) {
        CountryCurrencyPicker picker = CountryCurrencyPicker.newInstance(pickerType, countryCurrencyPickerListener);

        picker.mShowSubTitle = showSubtitle;
        picker.mShowCodeOrCurrency = showCodeOrCurrency;

        return picker;
    }
    //endregion

    //region onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (dialogTitle != null) {
            this.setStyle(STYLE_NORMAL, R.style.countryCurrencyPicker_dialog);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        myView = inflater.inflate(R.layout.countrycurrencypicker_fragment, container, false);

        mListener = (CountryCurrencyPickerListener) getArguments().getSerializable(Bundle_Listener);
        mPickerType = (PickerType) getArguments().getSerializable(Bundle_PickerType);

        if (getDialog() != null && dialogTitle != null) {
            this.getDialog().setTitle(dialogTitle);
        }

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
            mAdapter = new CountryCurrencyAdapter(new ArrayList<Country>(), new ArrayList<Currency>(), mShowSubTitle, mShowCodeOrCurrency, mPickerType, mListener, getDialog());
        } else {
            mAdapter = new CountryCurrencyAdapter(countryList, currencyList, mShowSubTitle, mShowCodeOrCurrency, mPickerType, mListener, getDialog());
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

                if (mPickerType == PickerType.COUNTRY) {
                    mCountryList = Country.listAll(getActivity(), filterString);
                } else if (mPickerType == PickerType.COUNTRYandCURRENCY) {
                    mCountryList = Country.listAllWithCurrencies(getActivity(), filterString);
                } else if (mPickerType == PickerType.CURRENCY) {
                    mCurrencyList = Currency.listAll(getActivity(), filterString);
                } else if (mPickerType == PickerType.CURRENCYandCOUNTRY) {
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
