package com.rssl.phizic.gate.mbv;

import java.util.Calendar;

/**
 *
 * User: Moshenko
 * Date: 12.09.13
 * Time: 17:12
 * ��������, ������������������ � ���
 */
public interface ClentPhone
{
    //����� ��������
    public String getPhoneNumber();

    public void setPhoneNumber(String phoneNumber);

    //���� ���������� ������������� ��������
    public Calendar getLastUsageDateTime();

    public void setLastUsageDateTime(Calendar lastUsageDateTime);
}
