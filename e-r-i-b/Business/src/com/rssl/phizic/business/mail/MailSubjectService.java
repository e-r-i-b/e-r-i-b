package com.rssl.phizic.business.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 02.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ���������� ���������
 */

public class MailSubjectService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * ���������� �������� ���������
	 * @param id �������������
	 * @param instance ������� ��
	 * @return �������� ���������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public MailSubject getMailSubjectById(Long id, String instance) throws BusinessException
	{
		return simpleService.findById(MailSubject.class, id, instance);
	}

	/**
	 * �������� ������ ���� ������� ���������
	 * @return �������� ���������
	 * @param instance ������� ��
	 * @throws BusinessException
	 */
	public List<MailSubject> getAllSubjects(String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(MailSubject.class).addOrder(Order.asc("id"));
		return simpleService.find(criteria, instance);
	}

	/**
	 * ��������� ��� ��������� �������� ���������
	 * @param mailSubject �������� ���������
	 * @param instance ������� ��
	 * @return �������� ���������
	 * @throws BusinessException
	 */
	public MailSubject addOrUpdateMailSubject(final MailSubject mailSubject, final String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<MailSubject>()
			{
				public MailSubject run(Session session) throws Exception
				{
					MailSubject savedMailSubject = simpleService.addOrUpdate(mailSubject, instance);
					dictionaryRecordChangeInfoService.addChangesToLog(savedMailSubject, ChangeType.update);
					return savedMailSubject;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ���������� �������� ���������.", e);
		}
	}

	/**
	 * ���������� �������� �������� ��������� �� ��������� ����� ��������� ������.
	 * @param subject �������� ���������
	 * @throws BusinessException
	 */
	public void updateMailBeforeRemoveSubject(final MailSubject subject) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute( new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mail.setDefaultSubjectId");
					query.setParameter("defaultTheme", MailHelper.getDefaultMailSubject());
					query.setParameter("theme", subject);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� �������� ���������
	 * @param mailSubject �������� ���������
	 * @param instance ������� ��
	 * @throws BusinessException
	 */
	public void remove(final MailSubject mailSubject, final String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.remove(mailSubject, instance);
					dictionaryRecordChangeInfoService.addChangesToLog(mailSubject, ChangeType.delete);
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ �������� �������� ���������.", e);
		}
	}
}
