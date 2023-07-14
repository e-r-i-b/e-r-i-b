package com.rssl.phizic.gate.mbv;

import java.util.Calendar;

/**
 *
 * User: Moshenko
 * Date: 12.09.13
 * Time: 17:12
 * Телефоны, зарегистрированные в МБВ
 */
public interface ClentPhone
{
    //Номер телефона
    public String getPhoneNumber();

    public void setPhoneNumber(String phoneNumber);

    //Дата последнего использования телефона
    public Calendar getLastUsageDateTime();

    public void setLastUsageDateTime(Calendar lastUsageDateTime);
}
