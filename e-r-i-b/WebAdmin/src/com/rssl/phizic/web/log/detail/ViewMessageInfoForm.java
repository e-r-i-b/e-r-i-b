package com.rssl.phizic.web.log.detail;

import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.MessagingLogEntryBase;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма просмотра информации о сообщении
 */

public class ViewMessageInfoForm extends EditFormBase
{
	private MessagingLogEntryBase messageLog;
	private String messageRequest;
	private String messageResponse;
	private String messageType;

	/**
	 * @return запрос
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public String getMessageRequest()
	{
		return messageRequest;
	}

	/**
	 * задать запрос
	 * @param messageRequest запрос
	 */
	public void setMessageRequest(String messageRequest)
	{
		this.messageRequest = messageRequest;
	}

	/**
	 * @return ответ
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public String getMessageResponse()
	{
		return messageResponse;
	}

	/**
	 * задать ответ
	 * @param messageResponse ответ
	 */
	public void setMessageResponse(String messageResponse)
	{
		this.messageResponse = messageResponse;
	}

	/**
	 * @return сущность лога
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public MessagingLogEntryBase getMessageLog()
	{
		return messageLog;
	}

	/**
	 * задать сущность лога
	 * @param messageLog сущность лога
	 */
	public void setMessageLog(MessagingLogEntryBase messageLog)
	{
		this.messageLog = messageLog;
	}

	public String getMessageType()
	{
		return messageType;
	}

	public void setMessageType(String messageType)
	{
		this.messageType = messageType;
	}
}