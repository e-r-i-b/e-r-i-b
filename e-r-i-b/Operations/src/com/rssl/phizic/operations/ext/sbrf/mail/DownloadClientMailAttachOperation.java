package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.mail.MailState;
import com.rssl.phizic.context.PersonContext;

/**
 * @author Dorzhinov
 * @ created 13.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class DownloadClientMailAttachOperation extends DownloadMailAttachOperationBase
{
	protected MailState getDraftMailState()
	{
		return MailState.CLIENT_DRAFT;
	}

	protected void checkMailAccess(Mail checkingMail) throws BusinessException
	{
		MailHelper.checkAccess(checkingMail, PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin());
	}
}
