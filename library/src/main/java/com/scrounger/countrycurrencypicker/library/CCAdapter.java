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
    private ViewHolderCurrencyItem mCurrencyHolder;
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
        if (mCountryList != null) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ViewHolderCountryItem(view);
        } else {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
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
        private Country myItem;
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
            myItem = item;

            if (myItem != null) {
                if (myItem.getFlagId() != 0) {
                    flag.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), myItem.getFlagId()));
                } else {
                    flag.setImageDrawable(null);
                }

                txtTitle.setText(myItem.getName());
//                txtTitle.setText(myItem.getName() + " (" + myItem.getCode() + ")");

                if (mListener instanceof CountryPickerListener) {
                    txtCodeOrSymbol.setText(myItem.getCode());
                    txtSubTitle.setVisibility(View.GONE);
                } else if (mListener instanceof CountryAndCurrencyPickerListener) {
                    txtSubTitle.setText(myItem.getCurrency().getName());
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

    public class ViewHolderCurrencyItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        //region Members
        private Currency myItem;
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
            myItem = item;

            if (myItem != null) {
                if (myItem.getFlagId() != 0) {
                    flag.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), myItem.getFlagId()));
                } else {
                    flag.setImageDrawable(null);
                }

                txtTitle.setText(myItem.getName());

                if (mListener instanceof CurrencyPickerListener) {
                    txtCodeOrSymbol.setText(myItem.getCode());
                    txtSubTitle.setVisibility(View.GONE);
                } else if (mListener instanceof CountryAndCurrencyPickerListener) {
//                    txtSubTitle.setText(myItem.getCurrency().getName());
//                    txtCodeOrSymbol.setText(myItem.getCurrency().getSymbol());
                }
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener instanceof CurrencyPickerListener) {
                ((CurrencyPickerListener) mListener).onSelect(myItem);
            } else if (mListener instanceof CountryAndCurrencyPickerListener) {
//                ((CurrencyAndCountryPickerListener) mListener).onSelect(myItem, myItem.getCurrency());
            }
        }
    }
}
