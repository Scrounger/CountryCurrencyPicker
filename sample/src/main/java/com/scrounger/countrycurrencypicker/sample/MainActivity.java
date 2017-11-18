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

package com.scrounger.countrycurrencypicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.scrounger.countrycurrencypicker.library.Buttons.CountryCurrencyButton;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.CountryCurrencyPicker;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;
import com.scrounger.countrycurrencypicker.library.PickerType;

public class MainActivity extends AppCompatActivity implements CountryCurrencyPickerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountryCurrencyButton button = (CountryCurrencyButton) findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == R.id.action_country) {
            getSupportActionBar().setTitle(R.string.menu_country);

            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance(PickerType.COUNTRY, this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, pickerFragment).commit();

        } else if (id == R.id.action_country_dialog) {
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(PickerType.COUNTRY, this);

            pickerDialog.setDialogTitle(getString(R.string.country_dialog_title));
            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_country_currency) {
            getSupportActionBar().setTitle(R.string.menu_country_currency);

            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance(PickerType.COUNTRYandCURRENCY, this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, pickerFragment).commit();

        } else if (id == R.id.action_country_currency_dialog) {
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(PickerType.COUNTRYandCURRENCY, this);

            pickerDialog.setDialogTitle(getString(R.string.country_currency_dialog_title));
            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_currency) {
            getSupportActionBar().setTitle(R.string.menu_currency);

            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance(PickerType.CURRENCY, this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, pickerFragment).commit();

        } else if (id == R.id.action_currency_dialog) {
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(PickerType.CURRENCY, this);

            pickerDialog.setDialogTitle(getString(R.string.currency_dialog_title));
            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_currency_countries) {
            getSupportActionBar().setTitle(R.string.menu_currency_countries);

            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance(PickerType.CURRENCYandCOUNTRY, this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, pickerFragment).commit();

        } else if (id == R.id.action_currency_countries_dialog) {
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(PickerType.CURRENCYandCOUNTRY, this);

            pickerDialog.setDialogTitle(getString(R.string.currency_countries_dialog_title));
            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectCountry(Country country) {
        if (country.getCurrency() == null) {
            Toast.makeText(MainActivity.this,
                    String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                    , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    String.format("name: %s\ncurrencySymbol: %s", country.getName(), country.getCurrency().getSymbol())
                    , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSelectCurrency(Currency currency) {
        if (currency.getCountries() == null) {
            Toast.makeText(MainActivity.this,
                    String.format("name: %s\nsymbol: %s", currency.getName(), currency.getSymbol())
                    , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    String.format("name: %s\ncurrencySymbol: %s\ncountries: %s", currency.getName(), currency.getSymbol(), TextUtils.join(", ", currency.getCountriesNames()))
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
