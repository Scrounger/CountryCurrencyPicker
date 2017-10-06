package com.scrounger.countrycurrencypicker.library;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class Country implements Parcelable {

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

    @DrawableRes
    private Integer flagId;

    @NonNull
    @DrawableRes
    public Integer getFlagId() {
        return flagId;
    }

    public void setFlagId(@DrawableRes Integer flagId) {
        this.flagId = flagId;
    }

    private Currency currency;

    @NonNull
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    //endregion

    //region Constructor
    public Country(String code, String name, Integer flagId) {
        this.code = code;
        this.name = name;
        this.flagId = flagId;
    }
    //endregion

    //region Retrieve
    public static Country getCountry(String countryCode, Context context) {
        Locale locale = new Locale("", countryCode);

        return new Country(countryCode,
                locale.getDisplayName(),
                getFlagDrawableId(countryCode, context));
    }

    @Nullable
    public static Country getCountryWithCurrency(String countryCode, Context context) {
        Country country = getCountry(countryCode, context);

        Currency currency = Currency.getCurrency(countryCode);
        if (currency != null) {
            //z.B. Antarktis is null -> keine Währung
            country.setCurrency(currency);
            return country;
        }
        return null;
    }

    public static ArrayList<Country> listAll(Context context, final String filter) {
        ArrayList<Country> list = new ArrayList<>();

        for (String countryCode : Locale.getISOCountries()) {
            Country country = getCountry(countryCode, context);

            list.add(country);
        }

        sortList(list);

        if (filter != null && filter.length() > 0) {
            return new ArrayList<>(Collections2.filter(list, new Predicate<Country>() {
                @Override
                public boolean apply(Country input) {
                    return input.getName().toLowerCase().contains(filter.toLowerCase());
                }
            }));
        } else {
            return list;
        }
    }

    public static ArrayList<Country> listAllWithCurrencies(Context context, final String filter) {
        ArrayList<Country> list = new ArrayList<>();

        for (String countryCode : Locale.getISOCountries()) {
            Country country = getCountryWithCurrency(countryCode, context);

            if (country != null) {
                //z.B. Antarktis is null -> keine Währung
                list.add(country);
            }
        }

        sortList(list);

        if (filter != null && filter.length() > 0) {
            return new ArrayList<>(Collections2.filter(list, new Predicate<Country>() {
                @Override
                public boolean apply(Country input) {
                    return input.getName().toLowerCase().contains(filter.toLowerCase()) ||
                            input.getCurrency().getName().toLowerCase().contains(filter.toLowerCase()) ||
                            input.getCurrency().getSymbol().toLowerCase().contains(filter.toLowerCase());
                }
            }));
        } else {
            return list;
        }
    }
    //endregion

    //region Functions
    private static void sortList(ArrayList<Country> list) {
        Collections.sort(list, new Comparator<Country>() {
            @Override
            public int compare(Country ccItem, Country ccItem2) {
                return removeAccents(ccItem.getName()).toLowerCase().compareTo(removeAccents(ccItem2.getName()).toLowerCase());
            }
        });
    }

    @NonNull
    @DrawableRes
    private static Integer getFlagDrawableId(String countryCode, Context context) {
        String drawableName = "flag_" + countryCode.toLowerCase();
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
        dest.writeValue(this.flagId);
        dest.writeParcelable(this.currency, flags);
    }

    private Country(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.flagId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.currency = in.readParcelable(Currency.class.getClassLoader());
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
    //endregion
}
