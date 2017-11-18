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

import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;

import java.util.ArrayList;


public class CountryCurrencyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String logTAG = CountryCurrencyAdapter.class.getName() + ".";

    private ViewHolderCountryItem mCountryHolder;
    private ViewHolderCurrencyItem mCurrencyHolder;
    private ArrayList<Country> mCountryList;
    private ArrayList<Currency> mCurrencyList;
    private PickerType mPickerType;
    private CountryCurrencyPickerListener mListener;
    private Boolean mShowSubTitle;
    private Boolean mShowCodeOrCurrency;
    private Dialog mDialog;

    public CountryCurrencyAdapter(ArrayList<Country> countryList, ArrayList<Currency> currencyList, Boolean showSubtitle, Boolean showCodeOrCurrency, PickerType pickerType, CountryCurrencyPickerListener listener, Dialog dialog) {
        this.mCountryList = countryList;
        this.mCurrencyList = currencyList;
        this.mShowSubTitle = showSubtitle;
        this.mShowCodeOrCurrency = showCodeOrCurrency;
        this.mPickerType = pickerType;
        this.mListener = listener;
        this.mDialog = dialog;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mCountryList != null) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countrycurrencypicker_row, parent, false);
            return new ViewHolderCountryItem(view);
        } else {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countrycurrencypicker_row, parent, false);
            return new ViewHolderCurrencyItem(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mCountryList != null) {
            mCountryHolder = (ViewHolderCountryItem) holder;
            mCountryHolder.setItem(mCountryList.get(position));
        } else {
            mCurrencyHolder = (ViewHolderCurrencyItem) holder;
            mCurrencyHolder.setItem(mCurrencyList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mCountryList != null) {
            return mCountryList.size();
        } else {
            return mCurrencyList.size();
        }
    }

    public class ViewHolderCountryItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final String logTAG = ViewHolderCountryItem.class.getName() + ".";

        //region Members
        private Country myCountry;
        private ImageView flag;
        private TextView txtTitle;
        private TextView txtSubTitle;
        private TextView txtCodeOrSymbol;
        //endregion

        public ViewHolderCountryItem(View parent) {
            super(parent);
            itemView.setOnClickListener(this);

            flag = (ImageView) itemView.findViewById(R.id.flag);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtSubTitle = (TextView) itemView.findViewById(R.id.subtitle);
            txtCodeOrSymbol = (TextView) itemView.findViewById(R.id.code_or_symbol);
        }

        public void setItem(Country item) {
            myCountry = item;

            if (myCountry != null) {
                if (myCountry.getFlagId() != 0) {
                    flag.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), myCountry.getFlagId()));
                } else {
                    flag.setImageDrawable(null);
                }

                txtTitle.setText(myCountry.getName());
//                txtTitle.setText(myCurrency.getName() + " (" + myCurrency.getCode() + ")");

                if (!mShowSubTitle) {
                    txtSubTitle.setVisibility(View.GONE);
                }

                if (!mShowCodeOrCurrency) {
                    txtCodeOrSymbol.setVisibility(View.GONE);
                }

                if (mPickerType == PickerType.COUNTRY) {
                    txtCodeOrSymbol.setText(myCountry.getCode());
                    txtSubTitle.setVisibility(View.GONE);
                } else if (mPickerType == PickerType.COUNTRYandCURRENCY) {
                    txtSubTitle.setText(myCountry.getCurrency().getName());
                    txtCodeOrSymbol.setText(myCountry.getCurrency().getSymbol());
                }
            }
        }

        @Override
        public void onClick(View view) {
            mListener.onSelectCountry(myCountry);

            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
    }

    public class ViewHolderCurrencyItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        //region Members
        private Currency myCurrency;
        private ImageView flag;
        private TextView txtTitle;
        private TextView txtSubTitle;
        private TextView txtCodeOrSymbol;
        //endregion

        public ViewHolderCurrencyItem(View parent) {
            super(parent);
            itemView.setOnClickListener(this);

            flag = (ImageView) itemView.findViewById(R.id.flag);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtSubTitle = (TextView) itemView.findViewById(R.id.subtitle);
            txtCodeOrSymbol = (TextView) itemView.findViewById(R.id.code_or_symbol);
        }

        public void setItem(Currency item) {
            myCurrency = item;

            if (myCurrency != null) {
                if (myCurrency.getFlagId() != 0) {
                    flag.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), myCurrency.getFlagId()));
                } else {
                    flag.setImageDrawable(null);
                }

                txtTitle.setText(myCurrency.getName());
                txtCodeOrSymbol.setText(myCurrency.getSymbol());

                if (!mShowSubTitle) {
                    txtSubTitle.setVisibility(View.GONE);
                }

                if (!mShowCodeOrCurrency) {
                    txtCodeOrSymbol.setVisibility(View.GONE);
                }

                if (mPickerType == PickerType.CURRENCY) {
                    txtSubTitle.setVisibility(View.GONE);
                } else if (mPickerType == PickerType.CURRENCYandCOUNTRY) {
                    txtSubTitle.setText(TextUtils.join(", ", myCurrency.getCountriesNames()));
                }
            }
        }

        @Override
        public void onClick(View view) {
            mListener.onSelectCurrency(myCurrency);

            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
    }
}
