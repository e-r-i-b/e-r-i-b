package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailState;

/**
 * @author Dorzhinov
 * @ created 13.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class DownloadEmployeeMailAttachOperation extends DownloadMailAttachOperationBase
{
	protected MailState getDraftMailState()
	{
		return MailState.EMPLOYEE_DRAFT;
	}

	protected void checkMailAccess(Mail checkingMail)
	{
		//Проверка не нужна
	}
}
