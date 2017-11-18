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
package com.scrounger.countrycurrencypicker.library.Buttons;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class CountryCurrencyButtonSaveState extends View.BaseSavedState {
    private String countryCode; //this will store the current value from ValueBar

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public CountryCurrencyButtonSaveState(Parcel source) {
        super(source);
        countryCode = source.readString();
    }

    public CountryCurrencyButtonSaveState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(countryCode);
    }

    public static final Parcelable.Creator<CountryCurrencyButtonSaveState> CREATOR
            = new Parcelable.Creator<CountryCurrencyButtonSaveState>() {
        public CountryCurrencyButtonSaveState createFromParcel(Parcel in) {
            return new CountryCurrencyButtonSaveState(in);
        }

        public CountryCurrencyButtonSaveState[] newArray(int size) {
            return new CountryCurrencyButtonSaveState[size];
        }
    };
}