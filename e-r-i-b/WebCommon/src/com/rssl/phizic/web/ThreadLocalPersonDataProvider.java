package com.rssl.phizic.web;

import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;

import java.util.Calendar;

/**
 * Поставщик данных о клиенте, работающий в текущей нити.
 *
 * @author bogdanov
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

public class ThreadLocalPersonDataProvider implements PersonDataProvider
{
	private ThreadLocal<PersonData> personDatas = new ThreadLocal<PersonData>();

	public PersonData getPersonData()
	{
		return personDatas.get();
	}

	public void setPersonData(PersonData data)
	{
		if (data != null)
			personDatas.set(data);
		else
			personDatas.remove();
	}

	public void setNextUpdateDate(Calendar nextUpdateDate)
	{
	}

	public Calendar getNextUpdateDate()
	{
		return null;
	}
}
