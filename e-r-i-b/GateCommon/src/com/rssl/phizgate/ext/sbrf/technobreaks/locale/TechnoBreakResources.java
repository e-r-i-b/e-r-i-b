package com.rssl.phizgate.ext.sbrf.technobreaks.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * @author koptyaev
 * @ created 13.10.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("JavaDoc")
public class TechnoBreakResources extends LanguageResource
{
	private String message; // сообщение клиенту

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
