

# Country and Currency Picker for Android

 [![](https://img.shields.io/badge/paypal-donate-yellow.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=VT4V9FULB93JW)

CountryCurrencyPicker is an android picker library for country and / or currency. You can implement it as fragment or dialog. It offers the option to search for country values and / or currency values.
<br />Inspired by [country-picker-android](https://github.com/mukeshsolanki/country-picker-android) and [currency-picker-android](https://github.com/midorikocak/currency-picker-android)

<p align="center">
  <img src="/Screenshots/country.png" width="250"/>
  <img src="/Screenshots/Country%26Currency.png" width="250"/>
  <img src="/Screenshots/Currency.png" width="250"/>
  <img src="/Screenshots/Currency%26Country.png" width="250"/>
  <img src="/Screenshots/dialog.png" width="250"/>
  <img src="/Screenshots/search.png" width="250"/>
</p>

## Requirements
min. Android API 19 (KitKat)

## Integration

Step 1\. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```java
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```

Step 2\. Add the dependency

```java
dependencies {
        compile 'com.github.scrounger:countrycurrencypicker:1.1.1'
}
```

## How to use
The library provides 4 different listener that defines which picker will start
#### 1\. Country

Showing all available countries in local language, searching only for country names is possible

```java
PickerType.COUNTRY
```
#### 2\. Country and Currency

Showing all available countries with their currency in local language, searching for country names, currency names and currency symbols is possible

```java
PickerType.COUNTRYandCURRENCY
```

#### 3\. Currency

Showing all available currencies in local language, searching only for currency names and currency symbols is possible

```java
PickerType.CURRENCY
```

#### 4\. Currency and Country

Showing all available currencies with their countries in local language, searching for currency names, currency symbols and countries is possible

```java
PickerType.CURRENCYandCOUNTRY
```

#### use as fragment (embed in your own activity)
```java
            CountryCurrencyPicker pickerFragment = CountryCurrencyPicker.newInstance(PickerType.COUNTRY, new CountryCurrencyPickerListener() {
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

                }
            });

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, pickerFragment).commit();
```

#### use as dialog
```java
            CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(PickerType.COUNTRYandCURRENCY, new CountryCurrencyPickerListener() {
                @Override
                public void onSelectCountry(Country country) {

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
            });

            pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);
```
#### dialog title
Show and set up a dialog title. Dialog style can be changed by overriding the style attribute ([see Customization](#customization))
```java
            pickerDialog.setDialogTitle(getString(R.string.country_currency_dialog_title));
```

For more examples take a look into [MainActivity.java](/sample/src/main/java/com/scrounger/countrycurrencypicker/sample/MainActivity.java#L52)

## Customization
To customize the style you can override the [layout files](/library/src/main/res/layout) or just override the [styles of library](/library/src/main/res/values/styles.xml) in your project
```java
<resources>

    <!-- Style for use as Dialog-->
    <style name="countryCurrencyPicker_dialog" parent="@style/Theme.AppCompat.Light.Dialog">
        <item name="android:windowNoTitle">false</item>
    </style>


    <!-- Styles for recyclerView row-->
    <style name="countryCurrencyPicker_row_item_container">
        <item name="android:paddingBottom">2dp</item>
        <item name="android:paddingTop">2dp</item>
        <item name="android:paddingStart">8dp</item>
        <item name="android:paddingEnd">8dp</item>
        <item name="android:foreground">?attr/selectableItemBackground</item>
    </style>

    <style name="countryCurrencyPicker_row_item_icon_flag">
        <item name="android:layout_width">48dp</item>
        <item name="android:layout_height">48dp</item>
    </style>

    <style name="countryCurrencyPicker_row_item_txt_container">
        <!--Style for container with title and subTitle-->
        <item name="android:layout_marginStart">10dp</item>
        <item name="android:layout_marginEnd">10dp</item>
    </style>

    <style name="countryCurrencyPicker_row_item_txt_title">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:maxLines">1</item>
        <item name="android:ellipsize">end</item>
        <item name="android:textColor">#0277bd</item>
        <item name="android:textStyle">bold</item>
        <item name="android:textAppearance">?android:attr/textAppearanceSmall</item>
    </style>

    <style name="countryCurrencyPicker_row_item_txt_subtitle">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:ellipsize">end</item>
        <item name="android:textColor">@android:color/secondary_text_dark</item>
        <item name="android:textStyle">italic</item>
        <item name="android:textAppearance">?android:attr/textAppearanceSmall</item>
    </style>

    <style name="countryCurrencyPicker_row_item_txt_code_or_symbol">
        <item name="android:layout_width">wrap_content</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:maxLines">1</item>
        <item name="android:textStyle">bold</item>
        <item name="android:minWidth">30dp</item>
        <item name="android:gravity">center</item>
        <item name="android:textAppearance">?android:attr/textAppearanceSmall</item>
    </style>

</resources>
```

## Button
Since version 1.1.0 the library provides Buttons that can be used directly in xml layout files. Button shows the countryName and the countryFlag. On click it opens the picker dialog.

<p align="center">
  <img src="/Screenshots/button.png" width="250"/>
</p>

```java
    <com.scrounger.countrycurrencypicker.library.Buttons.CountryCurrencyButton
        android:id="@+id/button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:country_code="US"
        app:show_currency="true" />
```

You can set <i>country_code</i> and <i>show_currency</i> directly in the xml layout file. Or define it befor you initialize the button, for example in the create method:
```java
        CountryCurrencyButton button = (CountryCurrencyButton) findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setCountry("DE");
        button.setShowCurrency(false);
```

To receive the selected country, just use the <i>CountryCurrencyPickerListener</i> as <i>button.setOnClickListener()</i> listener:
```java
        CountryCurrencyButton button = (CountryCurrencyButton) findViewById(R.id.button);
        button.setOnClickListener(new CountryCurrencyPickerListener() {
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

            }
        });
```

License
=======
Copyright (C) 2017 Scrounger

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.