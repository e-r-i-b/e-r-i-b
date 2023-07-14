package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �������� ��� �����������/���������� ���������� �����������
 * @author basharin
 * @ created 06.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ActivateOrLockManyServiceProviderOperation extends EditServiceProvidersOperationBase
{
	protected static final ServiceProviderService providerService = new ServiceProviderService();
	private List<ServiceProviderBase> providers = new ArrayList<ServiceProviderBase>();
	Map<String, Exception> exceptions = new HashMap<String, Exception>();

	public void initialize(String[] ids) throws BusinessException, BusinessLogicException
	{
		if (ArrayUtils.isEmpty(ids))
			throw new BusinessLogicException("��� ���������� �������� ���������� ������� ���� ��� ��������� ������� �� ������");

		for (String idStr : ids)
		{
			Long id = Long.valueOf(idStr);
			ServiceProviderBase temp = providerService.findById(id, getInstanceName());
			if (temp == null)
				throw new BusinessException("��������� ����� � id = " + id + " �� ������");

			if (!getRestriction().accept(temp))
			{
				throw new BusinessLogicException("�� ������� ���� ��� ���������� ����� " + temp.getName() + ". �������� �� ���� ���������.");
			}

			providers.add(temp);
		}
	}

	public List<ServiceProviderBase> getEntity()
	{
		return providers;
	}

	private void activateProviders(final ServiceProviderBase provider) throws BusinessLogicException, BusinessException
	{
		this.provider = provider;

		if (ServiceProviderState.ACTIVE == provider.getState())
		{
			throw new BusinessLogicException(String.format("��������� ����� %s ��� ��������� (�����������). �������� �� ���� ���������.", provider.getName()));
		}
		executeTransactional(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				//���� � �� ������ ������-�������. �� ��������� ������ �� ����.
				checkServiceProviderSubTypeFieldRestriction();
				// ��������� ���������� �������� ����� � ����������, ��������������� �����������, ��� ���������� ��
				checkBillingServiceProviderNumOfKeyField();

				//��������� ������������� ��������
				if (provider instanceof BillingServiceProvider)
				{
					GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
					try
					{
						Boolean available = gateInfoService.isPaymentCommissionAvailable(((BillingServiceProvider) provider).getBilling());
						if (!available && (((BillingServiceProvider) provider).getComissionRate() == null || ((BillingServiceProvider) provider).getMaxComissionAmount() == null || ((BillingServiceProvider) provider).getMinComissionAmount() == null))
						{
							throw new BusinessLogicException(String.format("��� ����������� ���������� %s ���������� ������ ��������.", provider.getName()));
						}
					}
					catch (GateException e)
					{
						throw new BusinessException(e);
					}
					catch (GateLogicException e)
					{
						throw new BusinessLogicException(e);
					}
				}

				provider.setState(ServiceProviderState.ACTIVE);
				providerService.addOrUpdate(provider, getInstanceName());
				clearEntitiesListCache();
				return null;
			}
		});
	}

	/**
	 * ���������� �����������
	 */
	public Map<String, Exception> activate()
	{
		for (ServiceProviderBase provider : providers)
		{
			try
			{
				activateProviders(provider);
			}
			catch (BusinessException ex)
			{
				exceptions.put(provider.getName(), ex);
			}
			catch (BusinessLogicException ex)
			{
				exceptions.put(provider.getName(), ex);
			}
		}

		return exceptions;
	}

	private void lockProviders(final ServiceProviderBase provider) throws BusinessLogicException, BusinessException
	{
		if (ServiceProviderState.ACTIVE != provider.getState())
		{
			throw new BusinessLogicException(String.format("��������� ����� %s ��� ��� �������� (�������������). �������� �� ���� ���������.", provider.getName()));
	    }
		else if (ServiceProviderState.ACTIVE == provider.getState())
		{
			executeTransactional(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					provider.setState(ServiceProviderState.NOT_ACTIVE);
					providerService.addOrUpdate(provider, getInstanceName());
					clearProviderListCache(provider);
					return null;
				}
			});
		}
	}

	/**
	 * ��������� �����������
	 */
	public Map<String, Exception> lock()
	{

		for (ServiceProviderBase provider : providers)
		{
			try
			{
				lockProviders(provider);
			}
			catch (BusinessException ex)
			{
				exceptions.put(provider.getName(), ex);
			}
			catch (BusinessLogicException ex)
			{
				exceptions.put(provider.getName(), ex);
			}
		}

		return exceptions;
	}

	/**
	 * ��� ��������� ���������� ��������� ������� ��� xml-����������� �����������
	 */
	protected void clearProviderListCache(ServiceProviderBase provider)
	{
		//��� ��������� ���������� ��������� ������� ��� xml-����������� �����������
		XmlEntityListCacheSingleton.getInstance().clearCache(provider, ServiceProviderBase.class);
	}
}
