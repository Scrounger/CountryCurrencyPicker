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
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;

public class CountryCurrencyButton extends AppCompatButton implements CountryCurrencyPickerListener {
    private final String logTAG = CountryCurrencyAdapter.class.getName() + ".";

    private CountryCurrencyPickerListener mClickListener;

    private Country mCountry;

    public Country getCountry() {
        return mCountry;
    }

    public void setCountry(Country mCountry) {
        this.mCountry = mCountry;
        invalidate();
    }

    public void setCountry(String countryCode) {
        if (!isShowCurrency()) {
            this.mCountry = Country.getCountry(countryCode, getContext());
        } else {
            this.mCountry = Country.getCountryWithCurrency(countryCode, getContext());
        }
        invalidate();
    }

    private Boolean mShowCurrency = false;

    public Boolean isShowCurrency() {
        return mShowCurrency;
    }

    public void setShowCurrency(Boolean mShowCurrency) {
        this.mShowCurrency = mShowCurrency;
    }

    public CountryCurrencyButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.countryCurrencyPicker);
        try {
            setCountry(a.getString(R.styleable.countryCurrencyPicker_country_code));
            setShowCurrency(a.getBoolean(R.styleable.countryCurrencyPicker_show_currency, false));
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCountry != null) {
            setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), mCountry.getFlagId()), null, null, null);
            if (isShowCurrency() && mCountry.getCurrency() != null) {
                setText(String.format("%s (%s)", mCountry.getName(), mCountry.getCurrency().getSymbol()));
            } else {
                setText(mCountry.getName());
            }
        }
    }

    @Override
    public boolean performClick() {
        CountryCurrencyPicker.PickerType pickerType;
        if (!isShowCurrency()) {
            pickerType = CountryCurrencyPicker.PickerType.COUNTRY;
        } else {
            pickerType = CountryCurrencyPicker.PickerType.COUNTRYandCURRENCY;
        }

        CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(pickerType, this);

        pickerDialog.setDialogTitle(getContext().getString(R.string.countryCurrencyPicker_select_country));
        pickerDialog.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        return super.performClick();
    }

    public void setOnClickListener(CountryCurrencyPickerListener listener) {
        mClickListener = listener;
    }

    @Override
    public void onSelectCountry(Country country) {
        mClickListener.onSelectCountry(country);
        setCountry(country);
    }

    @Override
    public void onSelectCurrency(Currency currency) {
        mClickListener.onSelectCurrency(currency);
    }
}
