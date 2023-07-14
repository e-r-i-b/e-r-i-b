package com.rssl.phizic.web.dictionaries.pages.messages;

import com.rssl.phizic.business.dictionaries.pages.messages.InformMessage;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Форма просмотра информационного сообщения.
 * @author komarov
 * @ created 28.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewInformMessagesForm extends EditFormBase
{
	private InformMessage informMessage;//просматриваемое информационное сообщение.
	private Boolean canEdit; //Можем ли мы что-нибудь делать с сообщением.

	public InformMessage getInformMessage()
	{
		return informMessage;
	}

	public void setInformMessage(InformMessage informMessage)
	{
		this.informMessage = informMessage;
	}

	public Boolean getCanEdit()
	{
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit)
	{
		this.canEdit = canEdit;
	}
}
