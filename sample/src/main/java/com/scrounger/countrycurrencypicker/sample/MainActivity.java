package com.scrounger.countrycurrencypicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scrounger.countrycurrencypicker.library.CCDialogFragment;
import com.scrounger.countrycurrencypicker.library.CCPickerListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,
                        CCDialogFragment.newInstance(new CCPickerListener() {
                            @Override
                            public void onSelect(String countryName, String countryCode, String currencyCode, String currencyName, String currencySymbol) {

                            }
                        }))
                .commit();
    }
}
