package com.rssl.phizic.gate.depomobilebank;

import com.rssl.phizic.gate.mbv.ClentPhone;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 12.09.13
 * Time: 17:07
 *
 */
public class MBVClentPhone implements ClentPhone
{

    private String phoneNumber;

    private Calendar lastUsageDateTime;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Calendar getLastUsageDateTime() {
        return lastUsageDateTime;
    }

    public void setLastUsageDateTime(Calendar lastUsageDateTime) {
        this.lastUsageDateTime = lastUsageDateTime;
    }
}
