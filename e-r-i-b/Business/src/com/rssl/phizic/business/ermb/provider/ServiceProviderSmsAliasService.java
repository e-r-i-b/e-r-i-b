package com.rssl.phizic.business.ermb.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 20.05.2013
 * Time: 14:30:51
 * ������ ��� ������ �  ������������ ������������ ������� ����
 */
public class ServiceProviderSmsAliasService
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final SimpleService service = new SimpleService();

	/**
	 * �������� ����� �� �����
	 * @param name - �����
	 * @return ���-�����
	 * @throws BusinessException
	 */
	public ServiceProviderSmsAlias findSmsAliasByName(String name) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderSmsAlias.class).add(Expression.eq("name", name));
		return service.findSingle(criteria);
	}
	/**
	 * @param alias ��� ����� ����������
	 * @return ������ ����� ���������� � ������� �������, �� ������ ����
	 */
	public List<ServiceProviderSmsAliasField> findAllFieldsByProviderAlias(final String alias) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ServiceProviderSmsAliasField>>()
			{
				public List<ServiceProviderSmsAliasField> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias.findAllFieldsByProviderAlias");
					query.setParameter("name",alias);
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
	 * @param alias ��� ����� ����������
	 * @return ������ ����� ���������� � ������� �������, �� ������ ����, ������������� � ������������ � field_descriptions
	 */
	public List<ServiceProviderSmsAliasField> findAllSortedFieldsByProviderSmsAlias(final ServiceProviderSmsAlias alias) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ServiceProviderSmsAliasField>>()
			{
				public List<ServiceProviderSmsAliasField> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias.findAllSortedFieldsByProviderAlias");
					query.setParameter("providerSmsAliasId", alias.getId());
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
	 * ��������� ����� ���������� �����.
	 * @param aliasArr ���� ������
	 * @param serviceProvider ��������� �����
	 * @param instance ��� �������� ��
	 * @throws ServiceProviderSmsAliasNotUniqException
	 */
	public void addOrUpdateAliasToServiceProvider(String[] aliasArr, ServiceProviderBase serviceProvider, final String instance) throws BusinessException
	{
		if (serviceProvider == null)
			throw new BusinessException("���������� �������� sms ����������, ��������� �� ��������");

		//���� ����������
		List<FieldDescription> fields = serviceProvider.getFieldDescriptions();

		final List<ServiceProviderSmsAlias> smsAliases = new ArrayList<ServiceProviderSmsAlias>(aliasArr.length);
		for (String alias: aliasArr)
		{
			ServiceProviderSmsAlias smsAlias = new ServiceProviderSmsAlias();
			smsAlias.setName(alias);
			smsAlias.setServiceProvider(serviceProvider);
			smsAlias.setSmsAliaseFields(getFields(fields));
			smsAliases.add(smsAlias);
		}

		try
		{
			execute(instance, new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					service.addOrUpdateListWithConstraintViolationException(smsAliases, instance);
					addToLog(smsAliases, ChangeType.update);
					return null;
				}
			});
		}
		catch(ConstraintViolationException ex)
		{
			throw new ServiceProviderSmsAliasNotUniqException(ex);
		}

	}

	private List<ServiceProviderSmsAliasField> getFields(List<FieldDescription> fields)
	{
		List<ServiceProviderSmsAliasField> smsFields = new ArrayList<ServiceProviderSmsAliasField>(fields.size());
		if (!fields.isEmpty())
		{
			for (FieldDescription fd:fields)
			{
				ServiceProviderSmsAliasField saf = new ServiceProviderSmsAliasField();
				saf.setField(fd);
				saf.setValue(fd.getDefaultValue());
				saf.setEditable(fd.isEditable());
				smsFields.add(saf);
			}
		}
		return  smsFields;
	}

	/**
	 * @param alias ����� ���������� �����
	 * @return ��������� �����
	 */
	public ServiceProviderBase findServiceProviderByAlias(final String alias) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ServiceProviderBase>()
			{
				public ServiceProviderBase run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias.findProviderByAlias");
					query.setParameter("name",alias);
					return (ServiceProviderBase)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ��� ���������� ����� ������� sms �����
	 */
	public List<ServiceProviderBase> findAllServiceProvidersWithAlias() throws BusinessException
	{
	 try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ServiceProviderBase>>()
			{
				public List<ServiceProviderBase> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias.findAllServiceProvidersWithAlias");
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
	 * @param provider ��������� �����
	 * @param instance ��� �������� ��
	 * @return ��� ���������� ����� ������� sms �����
	 */
	public List<ServiceProviderSmsAlias> getServiceProviderAlias(ServiceProviderBase provider, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderSmsAlias.class);
		criteria.add(Expression.eq("serviceProvider",provider));
		return service.find(criteria, instance);
	}

	/**
	  * ������� ���������� ����������
	 * @param ids id ��������� �����������
	 * @param instance ��� �������� ��
	 */
	public void removeServiceProviderAliasByIds(final List<Long> ids, final String instance) throws BusinessException
	{
		try
		{
			execute(instance, new HibernateAction<Void>()
			{
				public Void run(Session session) throws BusinessException
				{
					DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderSmsAlias.class).add(Expression.in("id", ids));
					List<ServiceProviderSmsAlias> removedAliasList = service.find(criteria, instance);
					for (ServiceProviderSmsAlias removedAlias : removedAliasList)
					{
						service.removeWithConstraintViolationException(removedAlias, instance);
						addToLog(removedAlias, ChangeType.delete);
					}
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
	 * ����� �����
	 * @param aliasId �������������
	 * @param instanceName ��� �������� ��
	 * @return �����
	 * @throws BusinessException
	 */
	public ServiceProviderSmsAlias findById(Long aliasId, String instanceName) throws BusinessException
	{
		return service.findById(ServiceProviderSmsAlias.class,aliasId, instanceName);
	}

	/**
	 * �������� �����
	 * @param alias �����
	 * @param instanceName ��� �������� ��
	 * @throws BusinessException
	 */
	public void update(final ServiceProviderSmsAlias alias, final String instanceName) throws BusinessException
	{
		execute(instanceName, new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				service.update(alias, instanceName);
				addToLog(alias, ChangeType.update);
				return null;
			}
		});
	}

	private void addToLog(MultiBlockDictionaryRecord record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}

	private <T extends MultiBlockDictionaryRecord> void addToLog(List<T> records, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(records, changeType);
	}

	private <T> T execute(final String instance, HibernateAction<T> action) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(action);
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
			throw new BusinessException(e);
		}
	}
}
