package com.scrounger.countrycurrencypicker.library;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ab on 05.10.2017.
 */

public class CCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String logTAG = CCAdapter.class.getName() + ".";

    private ViewHolderItem mHolder;
    private ArrayList<CCItem> mItemList;

    public CCAdapter(ArrayList<CCItem> mItemList) {
        this.mItemList = mItemList;
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
        private TextView txtTitle;

        public ViewHolderItem(View parent) {
            super(parent);
            itemView.setOnClickListener(this);

            txtTitle = (TextView) itemView.findViewById(R.id.title);
            flag = (ImageView) itemView.findViewById(R.id.flag);
        }

        public void setItem(CCItem item) {
            myCCItem = item;

            if (myCCItem.getFlagId() != 0) {
                flag.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), myCCItem.getFlagId()));
            }

            txtTitle.setText(item.getCountryName());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
