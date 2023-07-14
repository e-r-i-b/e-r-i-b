package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.operations.dictionaries.synchronization.ListDictionaryEntityOperationBase;

/**
 * @author krenev
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 * ������� �������� ��� ������ �� �������� ��������� � ����������� ���������
 */
public abstract class ServiceProviderListEntitiesOperationBase extends ListDictionaryEntityOperationBase<ServiceProvidersRestriction>
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private BillingServiceProviderBase provider;

	/**
	 * ���������������� ��������
	 * @param providerId ������������ ���������� �����
	 * @throws BusinessException
	 */
	public void initialize(Long providerId) throws BusinessException
	{
		provider = (BillingServiceProviderBase) providerService.findById(providerId, getInstanceName());
		if (provider == null)
		{
			throw new BusinessException("��������� ����� � id = " + provider  + " �� ������");
		}
		if (!getRestriction().accept(provider))
		{
			throw new RestrictionViolationException("��������� ID= " + provider.getId());
		}
	}

	/**
	 * @return ��������, ��� �������� ���������� ������ ��������� ���������
	 */
	public BillingServiceProviderBase getProvider()
	{
		return provider;
	}
}
