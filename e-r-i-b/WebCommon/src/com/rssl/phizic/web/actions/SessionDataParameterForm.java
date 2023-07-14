package com.rssl.phizic.web.actions;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author mihaylov
 * @ created 19.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SessionDataParameterForm extends EditFormBase
{
	private String sessionData;

	public String getSessionData()
	{
		return sessionData;
	}

	public void setSessionData(String sessionData)
	{
		this.sessionData = sessionData;
	}
}
