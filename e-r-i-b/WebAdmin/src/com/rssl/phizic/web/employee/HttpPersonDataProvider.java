package com.rssl.phizic.web.employee;

import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.web.WebContext;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

/**
 * @author niculichev
 * @ created 26.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class HttpPersonDataProvider implements PersonDataProvider
{
	public HttpPersonDataProvider()
	{

	}

	public HttpPersonDataProvider(PersonData personData)
	{
		setPersonData(personData);
	}

	public PersonData getPersonData()
    {
        HttpServletRequest request = WebContext.getCurrentRequest();
        return request == null ? null : PersonWebContext.get(request);
    }

	/** @param data данные пользовател€ */
	public void setPersonData(PersonData data)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		if(request == null)
			throw new RuntimeException("Ќет текущего запроса");
		PersonWebContext.set(request, data);
	}

	public void setNextUpdateDate(Calendar nextUpdateDate)
	{
	}

	public Calendar getNextUpdateDate()
	{
		return null;
	}
}
