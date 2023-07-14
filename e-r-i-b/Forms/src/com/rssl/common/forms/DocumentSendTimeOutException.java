package com.rssl.common.forms;

/**
 * Исключение при наступлении таймаута при отправке документа(send())
 * @author vagin
 * @ created 26.03.14
 * @ $Author$
 * @ $Revision$
 */
public class DocumentSendTimeOutException extends DocumentTimeOutException
{
	public DocumentSendTimeOutException(Throwable cause)
	{
		super(cause);
	}
}
