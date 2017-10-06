package com.scrounger.countrycurrencypicker.library;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

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

    @NonNull
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

    public Currency(String code, String name, String symbol, @DrawableRes Integer flagId) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        this.flagId = flagId;
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

    public static Currency getCurrency(java.util.Currency currency, Context context) {
        return new Currency(
                currency.getCurrencyCode(),
                currency.getDisplayName(),
                currency.getSymbol(),
                getFlagDrawableId(currency.getCurrencyCode(), context));
    }

    public static ArrayList<Currency> listAll(Context context, final String filter) {
        ArrayList<Currency> list = new ArrayList<>();

        for (java.util.Currency currency : java.util.Currency.getAvailableCurrencies()) {
            list.add(getCurrency(currency, context));
        }

        sortList(list);

        if (filter != null && filter.length() > 0) {
            return new ArrayList<>(Collections2.filter(list, new Predicate<Currency>() {
                @Override
                public boolean apply(Currency input) {
                    return input.getName().toLowerCase().contains(filter.toLowerCase());
                }
            }));
        } else {
            return list;
        }
    }

    //endregion

    //region Functions
    private static void sortList(ArrayList<Currency> list) {
        Collections.sort(list, new Comparator<Currency>() {
            @Override
            public int compare(Currency ccItem, Currency ccItem2) {
                return removeAccents(ccItem.getName()).toLowerCase().compareTo(removeAccents(ccItem2.getName()).toLowerCase());
            }
        });
    }

    @NonNull
    @DrawableRes
    private static Integer getFlagDrawableId(String currencyCode, Context context) {
        String drawableName = "flag_" + currencyCode.toLowerCase();
        return context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName());
    }

    private static String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
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

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
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
