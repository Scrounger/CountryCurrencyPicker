package com.scrounger.countrycurrencypicker.sample;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.scrounger.countrycurrencypicker.library.CCPicker;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.CountryAndCurrencyPickerListener;
import com.scrounger.countrycurrencypicker.library.CountryPickerListener;
import com.scrounger.countrycurrencypicker.library.Currency;

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
                            CCPicker.newInstance(new CountryPickerListener() {
                                @Override
                                public void onSelect(Country country) {
                                    Toast.makeText(MainActivity.this,
                                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }))
                    .commit();

        } else if (id == R.id.action_country_dialog) {
            CCPicker picker = CCPicker.newInstance(new CountryPickerListener() {
                @Override
                public void onSelect(Country country) {
                    Toast.makeText(MainActivity.this,
                            String.format("name: %s\ncode: %s", country.getName(), country.getCode())
                            , Toast.LENGTH_SHORT).show();

                    DialogFragment dialogFragment =
                            (DialogFragment) getSupportFragmentManager().findFragmentByTag(CCPicker.DIALOG_NAME);
                    dialogFragment.dismiss();
                }
            });
            picker.show(getSupportFragmentManager(), CCPicker.DIALOG_NAME);

        } else if (id == R.id.action_country_currency) {
            getSupportActionBar().setTitle(R.string.menu_country_currency);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,
                            CCPicker.newInstance(new CountryAndCurrencyPickerListener() {
                                @Override
                                public void onSelect(Country country, Currency currency) {
                                    Toast.makeText(MainActivity.this,
                                            String.format("name: %s\ncurrencySymbol: %s", country.getName(), currency.getSymbol())
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }))
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
