

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
