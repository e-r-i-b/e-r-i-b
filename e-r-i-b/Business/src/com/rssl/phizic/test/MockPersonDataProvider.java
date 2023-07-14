package com.rssl.phizic.test;

import com.rssl.phizic.context.*;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.10.2005
 * Time: 19:51:37
 */
public class MockPersonDataProvider implements PersonDataProvider
{
    private PersonData personData;

    public MockPersonDataProvider(PersonData personData)
    {
        this.personData = personData;
    }

    public PersonData getPersonData()
    {
        return personData;
    }

	/** @param data данные пользователя */
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
