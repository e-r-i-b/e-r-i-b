package com.rssl.phizic.web.client.mail;

import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Gainanov
 * @ created 01.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class ViewMailForm extends EditFormBase
{
	private Mail mail;

	public Mail getMail()
	{
		return mail;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}
}
