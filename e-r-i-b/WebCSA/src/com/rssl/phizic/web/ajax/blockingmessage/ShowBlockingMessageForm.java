package com.rssl.phizic.web.ajax.blockingmessage;

import org.apache.struts.action.ActionForm;

/**
 * ‘орма подт€гивани€ сообщени€ об ограничении входа.
 * @author mihaylov
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowBlockingMessageForm extends ActionForm
{
	private String blockingMessage;

	/**
	 * @return сообщение о блокировке доступа
	 */
	public String getBlockingMessage()
	{
		return blockingMessage;
	}

	/**
	 * ”становить сообщение о блокировке
	 * @param blockingMessage - сообщение
	 */
	public void setBlockingMessage(String blockingMessage)
	{
		this.blockingMessage = blockingMessage;
	}
}
