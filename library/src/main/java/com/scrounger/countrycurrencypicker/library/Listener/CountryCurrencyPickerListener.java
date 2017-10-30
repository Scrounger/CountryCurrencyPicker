package com.scrounger.countrycurrencypicker.library.Listener;

import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.Currency;

public interface CountryCurrencyPickerListener {
    void onSelectCountry(Country country);

    void onSelectCurrency(Currency currency);
}
