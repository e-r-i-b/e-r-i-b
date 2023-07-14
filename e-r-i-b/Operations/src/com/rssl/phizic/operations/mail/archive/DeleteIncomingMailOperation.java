package com.rssl.phizic.operations.mail.archive;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Iterator;

/**
 * @author mihaylov
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class DeleteIncomingMailOperation extends ProcessMailOperationBase
{

	private static final MailService mailService = new MailService();

	protected Integer getLastMonthValue() throws BusinessException
	{
		try
		{
			return ConfigFactory.getConfig(MailConfig.class).getLastMonthIncoming();
		}
		catch (Exception e)
		{
			throw new BusinessException("Ќе удалось получить значение параметра 'ќставл€ть записи за...' дл€ перемещени€ вход€щих писем в список удаленных", e);
		}
	}

	public void process() throws BusinessException
	{
		for(;mailIterator.hasNext();)
		{
			Mail mail = mailIterator.next();
			Iterator<Recipient> recipients = mailService.getRecipients(mail.getId());
			while(recipients.hasNext())
			{
				Recipient recipient = recipients.next();
				if(recipient.getState() == RecipientMailState.DRAFT)
				{
					Mail draftMail = mailService.getMailDraftByParentId(mail.getId(), MailState.EMPLOYEE_DRAFT);
					if(draftMail != null)
					{
						draftMail.setDeleted(true);
						mailService.addOrUpdate(draftMail);
					}
				}
				recipient.setDeleted(true);
				mailService.addOrUpdate(recipient);
			}
			mailService.addOrUpdate(mail);
		}
	}
}
