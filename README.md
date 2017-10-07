

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
##### 1\. Country

Showing all available countries in local language, searching only for country names is possible

```java
new CountryPickerListener()
```
##### 2\. Country and Currency

Showing all available countries with their currency in local language, searching for country names, currency names and currency symbols is possible

```java
new CountryAndCurrenciesPickerListener()
```

##### 3\. Currency

Showing all available currencies in local language, searching only for currency names and currency symbols is possible

```java
new CurrencyPickerListener()
```

##### 4\. Currency and Country

Showing all available currencies with their countries in local language, searching for currency names, currency symbols and countries is possible

```java
new CurrencyAndCountriesPickerListener()
```