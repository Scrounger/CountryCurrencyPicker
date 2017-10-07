

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

## How to use

### Requirements
min. Android API 19 (KitKat)

### Integration

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
        compile 'com.github.scrounger:CountryCurrencyPicker:1.0.1'
}
```

### How to use
The library provides 4 different listener that defines which picker will start
#### 1\. Country

Showing all available countries in local language, searching only for country names is possible

```java
new CountryPickerListener()
```
#### 2\. Country and Currency

Showing all available countries with their currency in local language, searching for country names, currency names and currency symbols is possible

```java
new CountryAndCurrenciesPickerListener()
```

#### 3\. Currency

Showing all available currencies in local language, searching only for currency names and currency symbols is possible

```java
new CurrencyPickerListener()
```

#### 4\. Currency and Country

Showing all available currencies with their countries in local language, searching for currency names, currency symbols and countries is possible

```java
new CurrencyAndCountriesPickerListener()
```

#### use as fragment (embed in your own activity)
```java
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
```

#### use as dialog
```java
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
```

For more examples take a look into [MainActivity.java](/sample/src/main/java/com/scrounger/countrycurrencypicker/sample/MainActivity.java#L52)

### Customization
To customize the style you can override the [layout files](/library/src/main/res/layout) or just override the [styles of library](/library/src/main/res/values/styles.xml) in your project
```java
<resources>

    <!-- Styles for recyclerView countrycurrencypicker_row-->
    <style name="ccPicker_row_item_container">
        <item name="android:paddingBottom">2dp</item>
        <item name="android:paddingTop">2dp</item>
        <item name="android:paddingStart">8dp</item>
        <item name="android:paddingEnd">8dp</item>
        <item name="android:foreground">?attr/selectableItemBackground</item>

    </style>

    <style name="ccPicker_row_item_icon_flag">
        <item name="android:layout_width">48dp</item>
        <item name="android:layout_height">48dp</item>
    </style>

    <style name="ccPicker_row_item_txt_container">
        <!--Style for container with title and subTitle-->
        <item name="android:layout_marginStart">10dp</item>
        <item name="android:layout_marginEnd">10dp</item>
    </style>

    <style name="ccPicker_row_item_txt_title">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:maxLines">1</item>
        <item name="android:ellipsize">end</item>
        <item name="android:textColor">#0277bd</item>
        <item name="android:textStyle">bold</item>
        <item name="android:textAppearance">?android:attr/textAppearanceSmall</item>
    </style>

    <style name="ccPicker_row_item_txt_subtitle">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:ellipsize">end</item>
        <item name="android:textColor">@android:color/secondary_text_dark</item>
        <item name="android:textStyle">italic</item>
        <item name="android:textAppearance">?android:attr/textAppearanceSmall</item>
    </style>

    <style name="ccPicker_row_item_txt_code_or_symbol">
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
