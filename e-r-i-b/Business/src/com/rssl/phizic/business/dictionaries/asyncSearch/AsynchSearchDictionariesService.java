package com.rssl.phizic.business.dictionaries.asyncSearch;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.config.*;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ author: Gololobov
 * @ created: 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsynchSearchDictionariesService extends SimpleService
{
	// ������������ ������ "�����", ����� �������� ���� ���������� ������ � ��, ��� ������ �� ������������
	private static final int BATCH_SIZE = 1000;
	
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	private static final String ASYNC_SEARCH_INSTANCE_NAME = "AsyncSearch";
	private static final String ERROR_MSG_ASYNCH_SEARCH_OBJECT_INSERT = "������ ��� ���������� ������ %s � �� '������' ������";

	/**
	 * ���������� ������ �������� � �� ������������ ������
	 * @param objectInfoForUpdateList - ������ ������ �� ��������� ������� ���������� ��������
	 * @throws BusinessException
	 */
	public void addOrDeleteOrUpdateObjects(List<AsynchSearchObject> objectInfoForUpdateList) throws BusinessException
	{
		ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
		try
		{
			//����� ������
			Map<String, Object> addObjectMap = new HashMap<String, Object>();
			//������������� �������
			Map<String, Object> updateObjectMap = new HashMap<String, Object>();
			//��������� �������
			Map<String, Object> deleteObjectMap = new HashMap<String, Object>();

			for (AsynchSearchObject asynchSearchObject : objectInfoForUpdateList)
			{
			    Class clazz = Thread.currentThread().getContextClassLoader().loadClass(asynchSearchObject.getObjectClassName());
				Object object = clazz.newInstance();
				if (asynchSearchObject.getObjectState().equals(AsynchSearchObjectState.DELETED))
			        deleteObject(object, asynchSearchObject, deleteObjectMap);
			    else
			        addOrUpdateObject(object, asynchSearchObject, addObjectMap, updateObjectMap);
			}
			//�������� ������ ��������
			if (CollectionUtils.isNotEmpty(deleteObjectMap.values()))
			{
				List<Object> deleteObjectList = new ArrayList<Object>(deleteObjectMap.values());
				removeList(deleteObjectList, ASYNC_SEARCH_INSTANCE_NAME);
				log.info(String.format("������� ������� �� �� '������' ������ %s", String.valueOf(deleteObjectList.size())));
			}
			//���������� ������ ��������
			if (CollectionUtils.isNotEmpty(addObjectMap.values()))
			{
				List<Object> addObjectList = new ArrayList<Object>(addObjectMap.values());
				replicateList(addObjectList, ASYNC_SEARCH_INSTANCE_NAME);
				log.info(String.format("��������� ������� � �� '������' ������ %s", String.valueOf(addObjectMap.size())));
			}
			//���������� ����� ������ � ����� ����������
			if (CollectionUtils.isNotEmpty(updateObjectMap.values()))
			{
				List<Object> updateObjectList = new ArrayList<Object>(updateObjectMap.values());
				updateList(updateObjectList, ASYNC_SEARCH_INSTANCE_NAME);
				log.info(String.format("��������� ������� � �� '������' ������ %s", String.valueOf(updateObjectList.size())));
			}
			//�������� ����� ������� �� ���������� �������� � ��
			if (CollectionUtils.isNotEmpty(objectInfoForUpdateList))
				removeList(objectInfoForUpdateList);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			Thread.currentThread().setContextClassLoader(oldClassLoader);
		}
	}

	private void deleteObject(Object object, AsynchSearchObject asynchSearchObject, Map<String, Object> deleteObjectMap) throws BusinessException
	{
		Long objectId = Long.valueOf(asynchSearchObject.getObjectKey());
		Object dbObject = findById(object.getClass(), objectId, ASYNC_SEARCH_INSTANCE_NAME);

		if (dbObject != null)
		{
			if (dbObject instanceof PaymentService)
			{
				AsynchSearchServiceProvider asynchSearchServiceProvider = getAsynchSearchSeviceProvider((ServiceProviderBase) dbObject);
				deleteObjectMap.put(objectId.toString(), asynchSearchServiceProvider);
			}
		}
	}

	private void addOrUpdateObject(Object object, AsynchSearchObject asynchSearchObject,
	                               Map<String, Object> addObjectMap, Map<String, Object> updateObjectMap) throws ClassNotFoundException, BusinessException
	{
		Long objectId = Long.valueOf(asynchSearchObject.getObjectKey());
		Object dbObject = findById(object.getClass(), objectId);

		if (dbObject != null)
		{
			if (dbObject instanceof ServiceProviderBase)
			{
				AsynchSearchServiceProvider asynchSearchServiceProvider = getAsynchSearchSeviceProvider((ServiceProviderBase) dbObject);
				//����� �������� � �� "������" ������. ���� ���, �� ��� ����� ������
				Object asyncDbObject = findById(AsynchSearchServiceProvider.class, objectId, ASYNC_SEARCH_INSTANCE_NAME);
				//�������� ����� ��� ���������������
				if (asyncDbObject == null)
					addObjectMap.put(objectId.toString(), asynchSearchServiceProvider);
				else
					updateObjectMap.put(objectId.toString(), asynchSearchServiceProvider);
			}
		}
	}

	/**
	 * ������ �������� ������� ���������� �������� � ���� "������" ������
	 * @param limitListItems - max-�� ���-�� ������������ �������
	 * @return List<AsynchSearchObject> - ������ �������� ��� ���������� � �� "������" ������
	 * @throws BusinessException
	 */
	public List<AsynchSearchObject> getObjectsForUpdate(int limitListItems) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AsynchSearchObject.class);
		criteria.addOrder(Order.asc("id"));
		return find(criteria, limitListItems);
	}

	/**
	 * ���������� ����������� ����������� �����
	 * @param session
	 * @throws BusinessException
	 */
	public void replicateServiceProviders(Session session) throws BusinessException
	{
		if (session != null)
		{
			List<AsynchSearchServiceProvider> serviceProviderList = getAllActiveBillingServiceProvider();

			log.info(String.format("���������� �������� ����������� ����� ��� ���������� %s ", String.valueOf(serviceProviderList.size())));
			if (CollectionUtils.isEmpty(serviceProviderList))
				return;

			//���������� ������ ����������� �������
			replicateList(serviceProviderList, ASYNC_SEARCH_INSTANCE_NAME);

			log.info(String.format("�������������� ����������� ����� %s", String.valueOf(serviceProviderList.size())));
		}
	}

	/**
	 * ������ ���� �������� ����������� �����
	 * @return List<BillingServiceProvider> - ������ �����������
	 * @throws BusinessException
	 */
	private List<AsynchSearchServiceProvider> getAllActiveBillingServiceProvider() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class);
		//������ �������� ���������� �����
		criteria.add(Expression.eq("state", ServiceProviderState.ACTIVE));
		List<ServiceProviderBase> servProviders = find(criteria);

		List<AsynchSearchServiceProvider> asynchSerchServProviders = new ArrayList<AsynchSearchServiceProvider>();
		for (ServiceProviderBase serviceProvider: servProviders)
		{
			asynchSerchServProviders.add(getAsynchSearchSeviceProvider(serviceProvider));
		}

		return asynchSerchServProviders;
	}

	//����������� ���������� �������� �� � ���������� �� "������" ������
	private AsynchSearchServiceProvider getAsynchSearchSeviceProvider(ServiceProviderBase serviceProvider)
	{
		if (serviceProvider == null) return null;

		AsynchSearchServiceProvider asynchSearchServProvider = new AsynchSearchServiceProvider();
		asynchSearchServProvider.setId(serviceProvider.getId());
		asynchSearchServProvider.setName(serviceProvider.getName());
		if (serviceProvider instanceof BillingServiceProvider)
		{
			BillingServiceProvider billServProv = (BillingServiceProvider)serviceProvider;
			asynchSearchServProvider.setAlias(billServProv.getAlias());
			asynchSearchServProvider.setLegalName(billServProv.getLegalName());

			int i = 0;
			if (billServProv.isMobilebank())
			{
				//�������������� ����
				if (CollectionUtils.isNotEmpty(serviceProvider.getFieldDescriptions()))
					for (FieldDescription fieldDescription : serviceProvider.getFieldDescriptions())
					{
						if (fieldDescription != null)
						{
							i = fieldDescription.isKey() ? ++i : i;

							if (i>1)
								break;
						}
					}
			}
			asynchSearchServProvider.setMobileBankAllowed(i==1);

			asynchSearchServProvider.setAutoPymentSupported(billServProv.isAutoPaymentSupported());
			asynchSearchServProvider.setAccountType(billServProv.getAccountType());
			asynchSearchServProvider.setTemplateSupported(billServProv.isTemplateSupported());
		}
		asynchSearchServProvider.setAccount(serviceProvider.getAccount());
		asynchSearchServProvider.setINN(serviceProvider.getINN());

		//������ ��������(��� �� ��������)
		StringBuilder sb = new StringBuilder();
		for (Region region : serviceProvider.getRegions())
		{
			sb.append(region.getId()).append(',');
		}
		String regionsList = sb.toString();
		if (StringHelper.isNotEmpty(regionsList))
			regionsList = ','+regionsList;
		asynchSearchServProvider.setRegionsList(regionsList);

		String synchKey = (String) serviceProvider.getSynchKey();
		String[] external = synchKey.split("\\|");
		asynchSearchServProvider.setExternalSystemName(external[external.length-1]);
		asynchSearchServProvider.setState(serviceProvider.getState());

		return asynchSearchServProvider;
	}

	/**
	 * ��������� ���������� �� ���������� �������, ����� ����� �������� � ������
	 * (AsynchSearchDictionariesLoaderJob) � �� "������" ������
	 * @param object - ������, ������� ���������� ��������
	 */
	public void addObjectInfoForAsynchSearch(Object object, AsynchSearchObjectState state)
	{
		if (!needTrackingDataModifications())
			return;
		if (object != null && state != null)
		{
			final AsynchSearchObject asynchSearchObject = new AsynchSearchObject();
			Class clazz = object.getClass();
			asynchSearchObject.setObjectClassName(clazz.getName());
			String objectKey = null;
			//��������� �����
		    if (object instanceof ServiceProviderBase)
				objectKey = String.valueOf(((ServiceProviderBase) object).getId());
			asynchSearchObject.setObjectKey(objectKey);
			asynchSearchObject.setObjectState(state);
			try
			{
				add(asynchSearchObject);
			}
			catch (Exception e)
			{
				log.error(String.format(ERROR_MSG_ASYNCH_SEARCH_OBJECT_INSERT, asynchSearchObject.getObjectClassName()),e);
			}
		}
		else
			log.error(ERROR_MSG_ASYNCH_SEARCH_OBJECT_INSERT);
	}

	/**
	 * ���������� ������������� ����������� ��������� ������ ��� '������' ������
	 * true - ��������� ������������� � ������� � ����. "changes_for_asynch_search"
	 * ����� ����� ���� "AsynchSearchDictionariesLoaderJob" ��������� ��� ��������� � �� '������' ������ 
	 * @return
	 */
	private boolean needTrackingDataModifications()
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).isDataModifications();
	}

	/**
	 * ����� ��� ���������� ����� ������ � �� � ����� ����������
	 * @param list ������ �������� ��� ����������
	 * @param instanceName - ������� ��
	 * @param <T>
	 * @return
	 * @throws BusinessException
	 */
	public <T> List<T> updateList(final List<T> list, String instanceName) throws BusinessException
	{
		if (list.isEmpty())
			return list;
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
			return trnExecutor.execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					int count = 0;
					for (T obj : list)
					{
						session.update(obj);
						count++;
						// ���� � ������ ���������� �������� ���������� ����� ������, ���������� �� � ��
						if (count >= BATCH_SIZE) {
							session.flush();
							session.clear();
							count = 0;
						}
					}

					return list;
				}
			}
			);
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

	/**
	 * ���������� ����� ������ � �� � ����� ����������
	 * @param list - ������ ������
	 * @param instanceName - ������� ��
	 * @param <T>
	 * @return
	 * @throws BusinessException
	 */
	public <T> List<T> replicateList(final List<T> list, String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);
			return trnExecutor.execute(new HibernateAction<List<T>>()
			{
				public List<T> run(Session session) throws Exception
				{
					int count = 0;
					for (T obj : list)
					{
						session.replicate(obj,ReplicationMode.OVERWRITE);
						count++;
						// ���� � ������ ���������� �������� ���������� ����� ������, ���������� �� � ��
						if (count >= BATCH_SIZE) {
							session.flush();
							session.clear();
							count = 0;
						}
					}

					return list;
				}
			}
			);
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
