package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;

/**
 * Результат попытки отпраки СМС
 * @author Pankin
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class SendMessageResult
{
	private boolean sended;
	private IKFLMessagingException exception;

	/**
	 * конструктор
	 * @param sended отправлено ли сообщение
	 * @param exception исключение
	 */
	public SendMessageResult(boolean sended, IKFLMessagingException exception)
	{
		this.sended = sended;
		this.exception = exception;
	}

	/**
	 * Отправлено ли сообщение
	 * @return true - отправлено
	 */
	public boolean isSended()
	{
		return sended;
	}

	public void setSended(boolean sended)
	{
		this.sended = sended;
	}

	/**
	 * @return причина ошибки
	 */
	public IKFLMessagingException getException()
	{
		return exception;
	}

	public void setException(IKFLMessagingException exception)
	{
		this.exception = exception;
	}
}
