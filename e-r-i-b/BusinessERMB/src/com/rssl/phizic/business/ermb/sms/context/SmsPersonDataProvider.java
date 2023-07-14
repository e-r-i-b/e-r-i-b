package com.rssl.phizic.business.ermb.sms.context;

import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 17.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class SmsPersonDataProvider implements PersonDataProvider
{
	private static final String SESSION_DATA_KEY = PersonData.class.getName();

	private static final SmsPersonDataProvider instance = new SmsPersonDataProvider();

	private SmsPersonDataProvider() {}

	public static SmsPersonDataProvider getInstance()
	{
		return instance;
	}

	///////////////////////////////////////////////////////////////////////////

	public PersonData getPersonData()
	{
		Store session = StoreManager.getCurrentStore();
		if (session != null)
			return (PersonData) session.restore(SESSION_DATA_KEY);
		return null;
	}

	public void setPersonData(PersonData data)
	{
		Store session = StoreManager.getCurrentStore();
		if (session == null)
			throw new RuntimeException("Нет текущей сессии");
		session.save(SESSION_DATA_KEY, data);
	}

	public void setNextUpdateDate(Calendar nextUpdateDate)
	{
	}

	public Calendar getNextUpdateDate()
	{
		return null;
	}
}
