package com.rssl.phizic.business.reminders;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;

/**
 * @author osminin
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � ������� �����������
 */
public class ReminderLinkService
{
	private static final SimpleService simpleService = new SimpleService();

	public void addOrUpdate(ReminderLink reminderLink) throws BusinessException
	{
		simpleService.addOrUpdate(reminderLink);
	}

	/**
	 * ����� ���� ����������� �� ������ � �������������
	 * @param loginId ����� �������
	 * @param reminderId ������������� ����������
	 * @return ����
	 * @throws BusinessException
	 */
	public ReminderLink getByLoginAndId(final Long loginId, final Long reminderId) throws BusinessException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("����� ������� �� ����� ���� null");
		}
		if (reminderId == null)
		{
			throw new IllegalArgumentException("������������� ����������� �� ����� ���� null");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ReminderLink>()
			{
				public ReminderLink run(Session session) throws Exception
				{
					return (ReminderLink) session.getNamedQuery("com.rssl.phizic.business.reminders.ReminderLink.findByLoginAndId")
							.setParameter("login_id", loginId)
							.setParameter("reminder_id", reminderId)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ����� ����������� �� ������
	 * @param loginId ������������� ������
	 * @return ������ ������
	 * @throws BusinessException
	 */
	public List<ReminderLink> getByLogin(final Long loginId) throws BusinessException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("����� ������� �� ����� ���� null");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ReminderLink>>()
			{
				public List<ReminderLink> run(Session session) throws Exception
				{
					return (List<ReminderLink>) session.getNamedQuery("com.rssl.phizic.business.reminders.ReminderLink.findByLogin")
							.setParameter("login_id", loginId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ����������� �� ������ � �������������� �������
	 * @param reminderId ������������� ������� � ������������
	 * @param loginId ������������� ������
	 * @throws BusinessException
	 */
	public void deleteById(final Long reminderId, final Long loginId) throws BusinessException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("����� ������� �� ����� ���� null");
		}
		if (reminderId == null)
		{
			throw new IllegalArgumentException("������������� ����������� �� ����� ���� null");
		}

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.reminders.ReminderLink.deleteByLoginAndId")
							.setParameter("login_id", loginId)
							.setParameter("reminder_id", reminderId)
							.executeUpdate();

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
