package com.rssl.phizic.operations.mail.subjects;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.mail.MailSubject;
import com.rssl.phizic.business.mail.MailSubjectService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;
import org.hibernate.Session;

/**
 * @author komarov
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class RemoveSubjectOperation extends RemoveDictionaryEntityOperationBase<MailSubject, Restriction>
{
	private static final MailSubjectService mailService = new MailSubjectService();
	private MailSubject mailSubject;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		Long defaultMailSubjectId = MultiBlockModeDictionaryHelper.isMultiBlockMode()? MailHelper.getDefaultMultiBlockSubjectId(): MailHelper.getDefaultMailSubjectId();
		if(id.equals(defaultMailSubjectId))
		{
			throw new BusinessLogicException("������ ������� ������ �������� ���������.");
		}

		mailSubject = mailService.getMailSubjectById(id, getInstanceName());

		if (mailSubject == null) throw new BusinessException("�������� � id=" + id + " �� �������");
	}

	public void doRemove() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					//����� ��������� ��������, �� ���� ������� ������������� ����� �������� �� ���������.
					if (MultiBlockModeDictionaryHelper.isMultiBlockMode())
						mailService.updateMailBeforeRemoveSubject(mailSubject);

					mailService.remove(mailSubject, getInstanceName());
					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ �������� ��������� ������.", e);
		}
	}

	public MailSubject getEntity()
	{
		return mailSubject;
	}
}
