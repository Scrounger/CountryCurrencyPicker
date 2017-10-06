package com.scrounger.countrycurrencypicker.library;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String logTAG = CCAdapter.class.getName() + ".";

    private ViewHolderCountryItem mCountryHolder;
    private ArrayList<Country> mCountryList;
    private ArrayList<Currency> mCurrencyList;
    private Object mListener;

    public CCAdapter(ArrayList<Country> countryList, ArrayList<Currency> currencyList, Object listener) {
        this.mCountryList = countryList;
        this.mCurrencyList = currencyList;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolderCountryItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mCountryHolder = (ViewHolderCountryItem) holder;
        mCountryHolder.setItem(mCountryList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    public class ViewHolderCountryItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final String logTAG = ViewHolderCountryItem.class.getName() + ".";

        //region Members
        private Country myItem;
        private ImageView flag;
        private TextView txtCountry;
        private TextView txtCurrency;
        private TextView txtCodeOrSymbol;
        //endregion

        public ViewHolderCountryItem(View parent) {
            super(parent);
            itemView.setOnClickListener(this);

            flag = (ImageView) itemView.findViewById(R.id.flag);
            txtCountry = (TextView) itemView.findViewById(R.id.countryName);
            txtCurrency = (TextView) itemView.findViewById(R.id.currencyName);
            txtCodeOrSymbol = (TextView) itemView.findViewById(R.id.code_or_symbol);
        }

        public void setItem(Country item) {
            myItem = item;

            if (myItem != null) {
                if (myItem.getFlagId() != 0) {
                    flag.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), myItem.getFlagId()));
                }

                txtCountry.setText(myItem.getName());

                if (mListener instanceof CountryPickerListener) {
                    txtCodeOrSymbol.setText(myItem.getCode());
                    txtCurrency.setVisibility(View.GONE);
                } else if (mListener instanceof CountryAndCurrencyPickerListener) {
                    txtCurrency.setText(myItem.getCurrency().getName());
                    txtCodeOrSymbol.setText(myItem.getCurrency().getSymbol());
                }
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener instanceof CountryPickerListener) {
                ((CountryPickerListener) mListener).onSelect(myItem);
            } else if (mListener instanceof CountryAndCurrencyPickerListener) {
                ((CountryAndCurrencyPickerListener) mListener).onSelect(myItem, myItem.getCurrency());
            }
        }
    }
}
