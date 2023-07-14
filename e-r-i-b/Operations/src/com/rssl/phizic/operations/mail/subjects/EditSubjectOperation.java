package com.rssl.phizic.operations.mail.subjects;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.MailSubject;
import com.rssl.phizic.business.mail.MailSubjectService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author komarov
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSubjectOperation  extends EditDictionaryEntityOperationBase<MailSubject, Restriction>
{
	private static final MailSubjectService mailService = new MailSubjectService();


	private MailSubject mailSubject;
	
	public void initialize()
	{
		mailSubject = new MailSubject();
	}

	public void initialize(Long id) throws BusinessException
	{

		mailSubject = mailService.getMailSubjectById(id, getInstanceName());

		if (mailSubject == null) throw new BusinessException("Тематика с id=" + id + " не найдена");

	}

	public void doSave() throws BusinessException
	{
		mailService.addOrUpdateMailSubject(mailSubject, getInstanceName());
	}

	public MailSubject getEntity() throws BusinessException, BusinessLogicException
	{
		return mailSubject;
	}
}
