package com.rssl.phizic.business.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.locale.MultiLocaleDetachedCriteria;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * ������ ��� ������ � ��� ���������.
 *
 * @author  Balovtsev
 * @version 15.03.13 17:09
 */
public class SMSResourcesService
{
	private SimpleService simpleService = new SimpleService();

	/**
	 *
	 * ���������� ���������� ��� ������� � ��������� id.
	 *
	 * @param  id ������������� �������
	 * @return String ������ ���������� ���������� ������������ ������� �������� �������
	 * @throws Exception
	 */
	public String getTemplateVariables(final Long id) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
		{
			public String run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.business.sms.SMSResource.getTemplateVariables");
				query.setLong("id", id);

				return (String) query.uniqueResult();
			}
		});
	}

	/**
	 *
	 * ���������� ���������� ��� ������� ��������� � ����� ��� � ��������� id.
	 *
	 * @param  id ������������� �������
	 * @return String ������ ���������� ���������� ������������ ������� �������� �������
	 * @throws Exception
	 */
	public String getCSATemplateVariables(final Long id) throws Exception
	{
		return HibernateExecutor.getInstance(Constants.DB_CSA).execute(new HibernateAction<String>()
		{
			public String run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.business.sms.SMSResource.getCSATemplateVariables");
				query.setLong("id", id);

				return (String) query.uniqueResult();
			}
		});
	}

	/**
	 *
	 * ���� ��� ������� ������� ���� � ��� ����� �� ��� � � �������� � ��������� id.
	 *
	 * @param  id �������� �� ������� ����� �������������� ����������
	 * @return List&lt;SMSResource&gt;
	 * @throws BusinessException
	 */
	public List<SMSResource> findSmsResourcesById(final Long id) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<SMSResource>>()
			{
				public List<SMSResource> run(Session session) throws Exception
				{
					Query  query = session.getNamedQuery("com.rssl.phizic.business.sms.SMSResource.getById");
					query.setLong("id", id);
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
	 *
	 * ���� ��� ������ �� id
	 *
	 * @param  id - ������������� �������
	 * @return SMSResource
	 * @throws BusinessException
	 */
	public SMSResource findSmsResourceById(final Long id) throws BusinessException
	{
		return simpleService.findById(SMSResource.class, id);
	}

	/**
	 *
	 * ���� ������� ��� ��������� �� ���.
	 *
	 * @param id ������������� �������� �������
	 * @param instanceName ������� ��
	 *
	 * @return CSASmsResource
	 *
	 * @throws BusinessException
	 */
	public CSASmsResource findCSAResourcesById(final Long id, final String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CSASmsResource.class)
													.add(Expression.eq("id", id));

		return simpleService.findSingle(criteria, instanceName);
	}

	/**
	 *
	 * ���� ��� ������ ������ �� ��� ����(��� �� ������ ��� ��� � �������������) � �����
	 *
	 * @param clazz       ����� �������
	 * @param key         ���� �������� ��� �������
	 * @param channelType ��� ������
	 *
	 * @return SMSResource
	 * @throws BusinessException
	 *
	 */
	public SMSResource findResourcesByKey(final Class<? extends SMSResource> clazz, final String key, final ChannelType channelType) throws BusinessException
	{
//		������� ������:  INDEX_SMS_CHANNEL_KEY
//		��������� �������: access("THIS_"."KEY"=:key AND "THIS_"."CHANNEL"=:channelType)
//		��������������: 1
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
													.add(Expression.eq("key",     key))
													.add(Expression.eq("channel", channelType));
		return simpleService.findSingle(criteria);
	}

	/**
	 *
	 * ���� ��� ������ ������ �� ��� ����(��� �� ������ ��� ��� � �������������), ������ � �����
	 *
	 * @param clazz       ����� �������
	 * @param key         ���� �������� ��� �������
	 * @param localeId    ���� ������
	 * @param channelType ��� ������
	 *
	 * @return SMSResource
	 * @throws BusinessException
	 */
	public SMSResource findResourcesByLocaleAndKey(final Class<? extends SMSResource> clazz, final String key, String localeId, final ChannelType channelType) throws BusinessException
	{
//		������� ������:  INDEX_SMS_CHANNEL_KEY
//		��������� �������: access("THIS_"."KEY"=:key AND "THIS_"."CHANNEL"=:channelType)
//		��������������: 1
		DetachedCriteria criteria = MultiLocaleDetachedCriteria.forClassInLocale(clazz,localeId)
													.add(Expression.eq("key",     key))
													.add(Expression.eq("channel", channelType));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ����� ������ ��� ��������� ������ ��� ��������, ����� ������� ����� WEB_CHANNEL
	 *
	 * @return List&lt;SMSResource&gt; ������ ���-��������
	 * @throws Exception
	 */
	public List<SMSResource> getSMSResources(final ChannelType type) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<SMSResource>>()
		{
			public List<SMSResource> run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.operations.sms.SmsSettingsListOperation.getSMSResources");
					  query.setParameter("channelType", type);
				return query.list();
			}
		});
	}

	/**
	 * @param instanceName ������� ��
	 * @param resources ����������� ��� ���������
	 *
	 * @throws BusinessException
	 */
	public void updateSmsResources(final String instanceName, Object... resources) throws BusinessException
	{
		for (Object template : resources)
		{
			simpleService.update(template, instanceName);
		}
	}
}
