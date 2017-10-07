

# Country and Currency Picker for Android

 [![](https://img.shields.io/badge/paypal-donate-yellow.svg)](https://www.paypal.com/de/cgi-bin/webscr?cmd=_flow&SESSION=vfCLofSSbAU08KR-bMwVH5WSVwk9UICGuNJ45B0OEdO01OZQ9n7YkHltGOe&dispatch=5885d80a13c0db1f8e263663d3faee8d795bb2096d7a7643a72ab88842aa1f54&rapidsState=Donation__DonationFlow___StateDonationBilling&rapidsStateSignature=048cfdb0bf67100c270029a5bd3a966a3006f889)

CountryCurrencyPicker is an android picker library for country and / or currency. You can implement it as fragment or dialog. It offers the option to search for country values and / or currency values.
<br />Inspired by [country-picker-android](https://github.com/mukeshsolanki/country-picker-android) and [currency-picker-android](https://github.com/midorikocak/currency-picker-android)


<p align="center">
  <img src="https://raw.githubusercontent.com/Scrounger/CountryCurrencyPicker/master/Screenshots/Country.png" width="350"/>
  <img src="https://raw.githubusercontent.com/Scrounger/CountryCurrencyPicker/master/Screenshots/Country%26Currency.png" width="350"/>
</p>

![](https://raw.githubusercontent.com/Scrounger/CountryCurrencyPicker/master/Screenshots/Country.png)![](https://raw.githubusercontent.com/Scrounger/CountryCurrencyPicker/master/Screenshots/Country%26Currency.png)![](https://raw.githubusercontent.com/Scrounger/CountryCurrencyPicker/master/Screenshots/Currency.png)![](https://raw.githubusercontent.com/Scrounger/CountryCurrencyPicker/master/Screenshots/Currency%26Country.png)

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
