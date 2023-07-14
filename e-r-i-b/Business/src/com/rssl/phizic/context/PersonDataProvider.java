package com.rssl.phizic.context;

import java.util.Calendar;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:48:30 */
public interface PersonDataProvider
{
	/**
	 * @return ������ ������������
	 */
    PersonData getPersonData();

	/**
	 * @param data ������ ������������
	 */
	void setPersonData(PersonData data);

	/**
	 * @param nextUpdateDate ���������� ���� ���������� ������ �������.
	 */
    public void setNextUpdateDate(Calendar nextUpdateDate);

	/**
	 * @return ���������� ���� ���������� ������ �������.
	 */
    public Calendar getNextUpdateDate();
}
