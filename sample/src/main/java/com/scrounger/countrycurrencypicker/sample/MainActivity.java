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
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.scrounger.countrycurrencypicker.library.CountryCurrencyPicker;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.CountryAndCurrenciesPickerListener;
import com.scrounger.countrycurrencypicker.library.CountryPickerListener;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.CurrencyAndCountriesPickerListener;
import com.scrounger.countrycurrencypicker.library.CurrencyPickerListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,
                            CountryCurrencyPicker.newInstance(new CountryPickerListener() {
                                @Override
                                public void onSelect(Country country) {
                                    Toast.makeText(MainActivity.this,
                                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }))
                    .commit();

        } else if (id == R.id.action_country_dialog) {
            CountryCurrencyPicker picker = CountryCurrencyPicker.newInstance(new CountryPickerListener() {
                @Override
                public void onSelect(Country country) {
                    Toast.makeText(MainActivity.this,
                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                            , Toast.LENGTH_SHORT).show();

                    DialogFragment dialogFragment =
                            (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                    dialogFragment.dismiss();
                }
            });
            picker.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_country_currency) {
            getSupportActionBar().setTitle(R.string.menu_country_currency);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,
                            CountryCurrencyPicker.newInstance(new CountryAndCurrenciesPickerListener() {
                                @Override
                                public void onSelect(Country country, Currency currency) {
                                    Toast.makeText(MainActivity.this,
                                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), currency.getSymbol())
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }))
                    .commit();

        } else if (id == R.id.action_country_currency_dialog) {
            CountryCurrencyPicker picker = CountryCurrencyPicker.newInstance(new CountryAndCurrenciesPickerListener() {
                @Override
                public void onSelect(Country country, Currency currency) {
                    Toast.makeText(MainActivity.this,
                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), currency.getSymbol())
                            , Toast.LENGTH_SHORT).show();

                    DialogFragment dialogFragment =
                            (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                    dialogFragment.dismiss();
                }
            });
            picker.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_currency) {
            getSupportActionBar().setTitle(R.string.menu_currency);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,
                            CountryCurrencyPicker.newInstance(new CurrencyPickerListener() {
                                @Override
                                public void onSelect(Currency currency) {
                                    Toast.makeText(MainActivity.this,
                                            String.format("name: %s\nsymbol: %s", currency.getName(), currency.getName())
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }))
                    .commit();

        } else if (id == R.id.action_currency_dialog) {
            CountryCurrencyPicker picker = CountryCurrencyPicker.newInstance(new CurrencyPickerListener() {
                @Override
                public void onSelect(Currency currency) {
                    Toast.makeText(MainActivity.this,
                            String.format("name: %s\nsymbol: %s", currency.getName(), currency.getName())
                            , Toast.LENGTH_SHORT).show();

                    DialogFragment dialogFragment =
                            (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                    dialogFragment.dismiss();
                }
            });
            picker.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

        } else if (id == R.id.action_currency_countries) {
            getSupportActionBar().setTitle(R.string.menu_currency_countries);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,
                            CountryCurrencyPicker.newInstance(new CurrencyAndCountriesPickerListener() {
                                @Override
                                public void onSelect(Currency currency, ArrayList<Country> countries, ArrayList<String> countriesNames) {
                                    Toast.makeText(MainActivity.this,
                                            String.format("name: %s\ncurrencySymbol: %s\ncountries: %s", currency.getName(), currency.getSymbol(), TextUtils.join(", ", countriesNames))
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }))
                    .commit();

        } else if (id == R.id.action_currency_countries_dialog) {
            CountryCurrencyPicker picker = CountryCurrencyPicker.newInstance(new CurrencyAndCountriesPickerListener() {
                @Override
                public void onSelect(Currency currency, ArrayList<Country> countries, ArrayList<String> countriesNames) {
                    Toast.makeText(MainActivity.this,
                            String.format("name: %s\ncurrencySymbol: %s\ncountries: %s", currency.getName(), currency.getSymbol(), TextUtils.join(", ", countriesNames))
                            , Toast.LENGTH_SHORT).show();

                    DialogFragment dialogFragment =
                            (DialogFragment) getSupportFragmentManager().findFragmentByTag(CountryCurrencyPicker.DIALOG_NAME);
                    dialogFragment.dismiss();
                }
            });
            picker.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);
        }

        return super.onOptionsItemSelected(item);
    }
}
