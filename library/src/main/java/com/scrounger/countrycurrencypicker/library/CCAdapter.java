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

    private ViewHolderItem mHolder;
    private ArrayList<CCItem> mItemList;
    private CCPickerListener mListener;

    public CCAdapter(ArrayList<CCItem> mItemList, CCPickerListener listener) {
        this.mItemList = mItemList;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mHolder = (ViewHolderItem) holder;
        mHolder.setItem(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final String logTAG = ViewHolderItem.class.getName() + ".";

        //region Members
        private CCItem myCCItem;
        private ImageView flag;
        private TextView txtCountry;
        private TextView txtCurrency;
        private TextView txtCurrencySymbol;
        //endregion

        public ViewHolderItem(View parent) {
            super(parent);
            itemView.setOnClickListener(this);

            flag = (ImageView) itemView.findViewById(R.id.flag);
            txtCountry = (TextView) itemView.findViewById(R.id.countryName);
            txtCurrency = (TextView) itemView.findViewById(R.id.currencyName);
            txtCurrencySymbol = (TextView) itemView.findViewById(R.id.currencySymbol);
        }

        public void setItem(CCItem item) {
            myCCItem = item;

            if (myCCItem.getFlagId() != 0) {
                flag.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), myCCItem.getFlagId()));
            }

            txtCountry.setText(myCCItem.getCountryName());
            txtCurrency.setText(myCCItem.getCurrencyName());
            txtCurrencySymbol.setText(myCCItem.getCurrencySymbol());
        }

        @Override
        public void onClick(View view) {
            mListener.onSelect(myCCItem.getCountryName(), myCCItem.getCountryCode(), myCCItem.getCurrencyCode(), myCCItem.getCurrencyName(), myCCItem.getCurrencySymbol());
        }
    }
}