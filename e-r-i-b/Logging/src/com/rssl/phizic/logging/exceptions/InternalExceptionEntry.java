package com.rssl.phizic.logging.exceptions;

import com.rssl.phizic.common.types.Application;

/**
 * @author komarov
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class InternalExceptionEntry extends ExceptionEntry
{
	private static final String KIND = "I";

	private Application application;

	/**
	 * @return приложение
	 */
	public Application getApplication()
	{
		return application;
	}

	/**
	 * @param application приложение
	 */
	public void setApplication(Application application)
	{
		this.application = application;
	}

	@Override
	public String getKind()
	{
		return KIND;
	}
}
