package com.rssl.phizic.sms.banking.persons;

import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonData;

import java.util.Calendar;

/**
 * @author eMakarov
 * @ created 07.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsPersonDataProvider implements PersonDataProvider
{
	private PersonData personData;

	/**
	 * @return данные пользователя
	 */
	public PersonData getPersonData()
	{
		return personData;
	}

	/**
	 * @param data данные пользователя
	 */
	public void setPersonData(PersonData data)
	{
		personData = data;
	}

	public void setNextUpdateDate(Calendar nextUpdateDate)
	{
	}

	public Calendar getNextUpdateDate()
	{
		return null;
	}
}
