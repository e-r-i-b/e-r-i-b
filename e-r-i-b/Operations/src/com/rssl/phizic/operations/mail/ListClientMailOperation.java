package com.rssl.phizic.operations.mail;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.AuthModule;

import java.util.List;

/**
 * @author Gainanov
 * @ created 26.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class ListClientMailOperation extends OperationBase implements ListEntitiesOperation
{
	private MailService mailService = new MailService();

	public List<Mail> getNewImportantMailList() throws BusinessException
	{
		return mailService.getNewImportantClientMails(AuthModule.getAuthModule().getPrincipal().getLogin());
	}
}