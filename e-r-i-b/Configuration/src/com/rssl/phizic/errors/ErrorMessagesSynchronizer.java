package com.rssl.phizic.errors;

import com.rssl.phizic.ConfigurationCheckedException;

import java.util.List;

/**
 * @author gladishev
 * @ created 21.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ErrorMessagesSynchronizer
{
	private final ErrorMessagesService service = new ErrorMessagesService();
	private List<ErrorMessage> errorMessages;

	public ErrorMessagesSynchronizer(List<ErrorMessage> errorMessages)
	{
		this.errorMessages = errorMessages;
	}

	public void update() throws ConfigurationCheckedException
	{
		for (ErrorMessage message: errorMessages)
		{
			List<ErrorMessage> list = service.find(message.getRegExp(), message.getErrorType(), message.getSystem(), message.getMessage());
			if (list.size() == 0)
			{
				service.add(message);
			}
		}
	}
}
