package com.rssl.phizic.test.mbvmock;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 11.09.13
 * Time: 16:31
 * Телефоны  для ClinetAcc
 */
public class ClinetPhone
{

    private Long id;
    /**
     * Номер телефона
     */
    private String phoneNumber;

    /**
     * Дата последнего использования телефона
     */
    private Calendar lastUsageDateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
