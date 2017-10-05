package com.scrounger.countrycurrencypicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.scrounger.countrycurrencypicker.library.CCPicker;
import com.scrounger.countrycurrencypicker.library.CCPickerListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,
                        CCPicker.newInstance(new CCPickerListener() {
                            @Override
                            public void onSelect(String countryName, String countryCode, String currencyCode, String currencyName, String currencySymbol) {
                                Toast.makeText(MainActivity.this,
                                        String.format("name: %s\ncurrencySymbol: %s", countryName, currencySymbol)
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }))
                .commit();

//        CCPicker picker = CCPicker.newInstance(new CCPickerListener() {
//            @Override
//            public void onSelect(String countryName, String countryCode, String currencyCode, String currencyName, String currencySymbol) {
//                Toast.makeText(MainActivity.this,
//                        String.format("name: %s\ncurrencySymbol: %s", countryName, currencySymbol)
//                        , Toast.LENGTH_SHORT).show();
//
//                DialogFragment dialogFragment =
//                        (DialogFragment) getSupportFragmentManager().findFragmentByTag(CCPicker.DIALOG_NAME);
//                dialogFragment.dismiss();
//            }
//
//        });
//        picker.show(getSupportFragmentManager(), CCPicker.DIALOG_NAME);
    }
}
