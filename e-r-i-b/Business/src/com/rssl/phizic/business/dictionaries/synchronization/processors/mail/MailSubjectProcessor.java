package com.rssl.phizic.business.dictionaries.synchronization.processors.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.mail.MailSubject;
import com.rssl.phizic.business.mail.MailSubjectService;

/**
 * @author akrenev
 * @ created 02.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей тематик писем
 */

public class MailSubjectProcessor extends ProcessorBase<MailSubject>
{
	private static final MailSubjectService service = new MailSubjectService();

	@Override
	protected Class<MailSubject> getEntityClass()
	{
		return MailSubject.class;
	}

	@Override
	protected MailSubject getNewEntity()
	{
		return new MailSubject();
	}

	@Override
	protected void update(MailSubject source, MailSubject destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setDescription(source.getDescription());
	}

	@Override
	protected void doRemove(MailSubject localEntity) throws BusinessException, BusinessLogicException
	{
		service.updateMailBeforeRemoveSubject(localEntity);
		super.doRemove(localEntity);
	}
}
