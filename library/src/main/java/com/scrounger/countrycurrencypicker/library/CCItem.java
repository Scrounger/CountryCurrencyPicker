package com.scrounger.countrycurrencypicker.library;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;

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

    @DrawableRes
    private Integer flagId;

    public Integer getFlagId() {
        return flagId;
    }

    public void setFlagId(Integer flagId) {
        this.flagId = flagId;
    }
    //endregion

    public CCItem(String countryCode, String countryName, String currencyCode, String currencyName, String currencySymbol, @DrawableRes Integer flagId) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.flagId = flagId;
    }

    public static ArrayList<CCItem> listAll(Context context) {
        ArrayList<CCItem> list = new ArrayList<>();

        for (String countryCode : Locale.getISOCountries()) {
            Locale locale = new Locale("", countryCode);
            Currency currency = Currency.getInstance(locale);

            if (currency != null) {
                //z.B. Antarktis is null -> keine Währung
                CCItem country = new CCItem(
                        countryCode,
                        locale.getDisplayCountry(),
                        currency.getCurrencyCode(),
                        currency.getDisplayName(),
                        currency.getSymbol(),
                        getFlagDrawableId(countryCode, context));

                list.add(country);
            }
        }

        Collections.sort(list, new Comparator<CCItem>() {
            @Override
            public int compare(CCItem ccItem, CCItem ccItem2) {
                return ccItem.getCountryName().compareTo(ccItem2.getCountryName());
            }
        });

        return list;
    }

    public static ArrayList<CCItem> filtered(Context context, final String filter) {
        ArrayList<CCItem> list = listAll(context);

        if (filter != null && filter.length() > 0) {
            return new ArrayList<>(Collections2.filter(list, new Predicate<CCItem>() {
                @Override
                public boolean apply(CCItem input) {
                    return input.getCountryName().toLowerCase().contains(filter.toLowerCase()) ||
                            input.getCurrencyName().toLowerCase().contains(filter.toLowerCase());
                }
            }));
        } else {
            return list;
        }
    }

    @DrawableRes
    private static Integer getFlagDrawableId(String countryCode, Context context) {
        String drawableName = "flag_" + countryCode.toLowerCase(Locale.ENGLISH);
        return context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName());
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
