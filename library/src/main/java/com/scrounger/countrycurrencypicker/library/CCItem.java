package com.scrounger.countrycurrencypicker.library;

import android.os.Parcel;
import android.os.Parcelable;

public class CCItem implements Parcelable {

    //region Members
    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    private String currencyName;

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    private String currencySymbol;

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
    //endregion

    public CCItem(String countryCode, String countryName, String currencyCode, String currencyName, String currencySymbol) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.countryCode);
        dest.writeString(this.countryName);
        dest.writeString(this.currencyCode);
        dest.writeString(this.currencyName);
        dest.writeString(this.currencySymbol);
    }

    public CCItem() {
    }

    protected CCItem(Parcel in) {
        this.countryCode = in.readString();
        this.countryName = in.readString();
        this.currencyCode = in.readString();
        this.currencyName = in.readString();
        this.currencySymbol = in.readString();
    }

    public static final Creator<CCItem> CREATOR = new Creator<CCItem>() {
        @Override
        public CCItem createFromParcel(Parcel source) {
            return new CCItem(source);
        }

        @Override
        public CCItem[] newArray(int size) {
            return new CCItem[size];
        }
    };
    //endregion
}
