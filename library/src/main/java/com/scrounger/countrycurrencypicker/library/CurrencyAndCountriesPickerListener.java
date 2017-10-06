package com.scrounger.countrycurrencypicker.library;

import java.util.ArrayList;

public interface CurrencyAndCountriesPickerListener {
    void onSelect(Currency currency, ArrayList<Country> countries, ArrayList<String> countriesNames);
}
