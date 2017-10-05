package com.scrounger.countrycurrencypicker.library;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import java.util.Locale;

/**
 * Created by ab on 05.10.2017.
 */

public class Currency implements Parcelable {

    //region Members
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @DrawableRes
    private Integer flagId;

    @DrawableRes
    public Integer getFlagId() {
        return flagId;
    }
    //endregion

    //region Constructor
    public Currency(String code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }
    //endregion

    //region Retrieve
    public static Currency getCurrency(String countryCode) {
        Locale locale = new Locale("", countryCode);
        java.util.Currency currency = java.util.Currency.getInstance(locale);

        if (currency != null) {
            return new Currency(
                    currency.getCurrencyCode(),
                    currency.getDisplayName(),
                    currency.getSymbol());
        }
        return null;
    }
    //endregion

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.symbol);
        dest.writeValue(this.flagId);
    }

    protected Currency(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.symbol = in.readString();
        this.flagId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Currency> CREATOR = new Parcelable.Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel source) {
            return new Currency(source);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };
    //endregion
}
