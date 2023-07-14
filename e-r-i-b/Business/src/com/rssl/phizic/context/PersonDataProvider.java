package com.rssl.phizic.context;

import java.util.Calendar;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:48:30 */
public interface PersonDataProvider
{
	/**
	 * @return данные пользователя
	 */
    PersonData getPersonData();

	/**
	 * @param data данные пользователя
	 */
	void setPersonData(PersonData data);

	/**
	 * @param nextUpdateDate следуюущая дата обновления данных клиента.
	 */
    public void setNextUpdateDate(Calendar nextUpdateDate);

	/**
	 * @return следуюущую дату обновления данных клиента.
	 */
    public Calendar getNextUpdateDate();
}
