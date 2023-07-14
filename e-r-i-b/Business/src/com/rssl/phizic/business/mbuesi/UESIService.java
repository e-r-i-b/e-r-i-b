package com.rssl.phizic.business.mbuesi;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * ������ ��� ������ � ����������� UESI � ��
 * @author Pankin
 * @ created 30.01.15
 * @ $Author$
 * @ $Revision$
 */
public class UESIService extends SimpleService
{
	/**
	 * @param externalId ������� �������������
	 * @return ��������� �� �������������� ���������
	 */
	public UESICancelLimitMessage findByExternalId(final String externalId) throws BusinessException
	{
		if (StringHelper.isEmpty(externalId))
			return null;

		List<UESICancelLimitMessage> messages;

		try
		{
			final Calendar minDate = Calendar.getInstance();
			minDate.add(Calendar.DATE, -1 * (ConfigFactory.getConfig(UESIMessagesConfig.class).getStorageTime() + 1));
			messages = HibernateExecutor.getInstance().execute(new HibernateAction<List<UESICancelLimitMessage>>()
			{
				public List<UESICancelLimitMessage> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mbuesi.UESICancelLimitMessage.findByExternalId");
					query.setParameter("externalId", externalId);
					query.setParameter("minDate", minDate);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		if (CollectionUtils.isEmpty(messages))
			return null;

		if (messages.size() > 1)
			throw new BusinessException("������� ����� ������ ��������� � ��������������� " + externalId);

		return messages.get(0);
	}

	/**
	 * @return ������ �� ������������ ���������
	 */
	public List<UESICancelLimitMessage> getNewMessages() throws BusinessException
	{
		final Calendar minDate = Calendar.getInstance();
		minDate.add(Calendar.DATE, -1 * ConfigFactory.getConfig(UESIMessagesConfig.class).getStorageTime());
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<UESICancelLimitMessage>>()
			{
				public List<UESICancelLimitMessage> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mbuesi.UESICancelLimitMessage.getNewMessages");
					query.setParameter("minDate", minDate);
					query.setMaxResults(500);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ��������� ���������
	 * @param message ���������
	 * @param newState ����� ���������
	 */
	public void updateState(final UESICancelLimitMessage message, final State newState) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<List<Void>>()
			{
				public List<Void> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mbuesi.UESICancelLimitMessage.updateState");
					query.setParameter("state", newState.name());
					query.setLong("id", message.getId());
					query.setCalendar("creationDate", message.getCreationDate());
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ��������� ���������
	 * @param message ���������
	 * @param newState ����� ���������
	 * @param externalId ������� �������������
	 */
	public void updateStateAndExtId(final UESICancelLimitMessage message, final State newState, final String externalId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<List<Void>>()
			{
				public List<Void> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mbuesi.UESICancelLimitMessage.updateStateAndExtId");
					query.setParameter("state", newState.name());
					query.setParameter("externalId", externalId);
					query.setLong("id", message.getId());
					query.setCalendar("creationDate", message.getCreationDate());
					query.executeUpdate();
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
