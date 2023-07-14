package com.rssl.phizic.gate.messaging.configuration;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;

/**
 * @author Roshka
 * @ created 15.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class MessagingConfigurationValidator implements ValidationEventHandler
{
	private GateMessagingConfigurationException exception = null;

	public boolean handleEvent(ValidationEvent ve)
	{
		if (ve.getSeverity() == ValidationEvent.FATAL_ERROR ||
				ve.getSeverity() == ValidationEvent.ERROR)
		{
			ValidationEventLocator locator = ve.getLocator();

			String message =
					"Неверный формат документа: " + locator.getURL() +
			        " Ошибка: " + ve.getMessage() +
					" Ошибка в столбце " + locator.getColumnNumber() +
							", строка " + locator.getLineNumber();

			exception = new GateMessagingConfigurationException(message);

		}
		return true;
	}

	public boolean isSuccess()
	{
		return exception == null;
	}

	public void throwException() throws GateMessagingConfigurationException
	{
		if ( exception != null )
			throw exception;
	}
}