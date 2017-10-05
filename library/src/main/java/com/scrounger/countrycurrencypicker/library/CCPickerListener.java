package com.scrounger.countrycurrencypicker.library;


public interface CCPickerListener {
    void onSelect(String countryName, String countryCode, String currencyCode, String currencyName, String currencySymbol);
}
