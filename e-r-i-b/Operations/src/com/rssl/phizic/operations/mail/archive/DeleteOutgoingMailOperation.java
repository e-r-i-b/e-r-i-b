package com.rssl.phizic.operations.mail.archive;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.business.mail.MailService;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author mihaylov
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class DeleteOutgoingMailOperation extends ProcessMailOperationBase
{
	private static final MailService mailService = new MailService();

	public void process() throws BusinessException
	{
		for(;mailIterator.hasNext();)
		{
			Mail mail = mailIterator.next();
			mail.setDeleted(true);
			mailService.addOrUpdate(mail);
		}
	}

	protected Integer getLastMonthValue() throws BusinessException
	{
		try
		{
			return ConfigFactory.getConfig(MailConfig.class).getLastMonthOutgoing();
		}
		catch (Exception e)
		{
			throw new BusinessException("Не удалось получить значение параметра 'Оставлять записи за...' для перемещения исходящих писем в список удаленных", e);
		}
	}
}
