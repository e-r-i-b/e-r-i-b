package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * формы с сообщением о прекращении удаленного подключения УДБО
 * @author basharin
 * @ created 24.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CancelConnectUDBOForm extends ActionFormBase
{
	private String messageTitle;
	private String messageText;

	public String getMessageTitle()
	{
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle)
	{
		this.messageTitle = messageTitle;
	}

	public String getMessageText()
	{
		return messageText;
	}

	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}
}
