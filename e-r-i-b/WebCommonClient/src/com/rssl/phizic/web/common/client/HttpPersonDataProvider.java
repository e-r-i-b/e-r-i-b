package com.rssl.phizic.web.common.client;

import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.web.WebContext;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:49:46 */
public class HttpPersonDataProvider implements PersonDataProvider
{
	/**
	 * —ледующа€ дата обновлени€ данных клиентв.
	 */
	private Calendar nextUpdateDate;

	public PersonData getPersonData()
    {
        HttpServletRequest request = WebContext.getCurrentRequest();
	    if (request == null)
		    return null;

        PersonData data = ClientWebContext.get(request);
        if (data != null && nextUpdateDate != null)
        {
	        data.reset(nextUpdateDate);
        }
        return request == null ? null : data;
    }

	/** @param data данные пользовател€ */
	public void setPersonData(PersonData data)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		if(request == null)
			throw new RuntimeException("Ќет текущего запроса");
		ClientWebContext.set(request, data);
	}

    public Calendar getNextUpdateDate()
    {
        return nextUpdateDate;
    }

    public void setNextUpdateDate(Calendar nextUpdateDate)
    {
        this.nextUpdateDate = nextUpdateDate;
    }
}
