package com.rssl.phizic.web.common.client.contacts;

import com.rssl.phizic.web.common.FilterActionForm;

/**
 * @author lepihina
 * @ created 10.06.14
 * $Author$
 * $Revision$
 * ‘орма отображени€ клиенту информирующего сообщени€ об услуге адресной книги
 */
public class ShowContactsMessageForm extends FilterActionForm
{
	private String message;

	/**
	 * @return информирующее сообщение об услуге адресной книги
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message - информирующее сообщение об услуге адресной книги
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
}
