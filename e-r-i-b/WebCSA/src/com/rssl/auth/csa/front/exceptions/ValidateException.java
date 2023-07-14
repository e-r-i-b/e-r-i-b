package com.rssl.auth.csa.front.exceptions;

import org.apache.struts.action.ActionMessages;

/**
 * Ошибка валидации
 * @author niculichev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ValidateException extends FrontLogicException
{
	final private ActionMessages messages;

	public ValidateException(ActionMessages messages)
	{
		this.messages = messages;
	}

	public ActionMessages getMessages()
	{
		return messages;
	}
}
