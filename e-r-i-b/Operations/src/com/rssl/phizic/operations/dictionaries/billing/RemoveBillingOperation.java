package com.rssl.phizic.operations.dictionaries.billing;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.MultiInstanceBillingService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class RemoveBillingOperation extends RemoveDictionaryEntityOperationBase
{
	private Billing billing;
	private static final MultiInstanceBillingService billingService = new MultiInstanceBillingService();
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		billing = billingService.getById(id, getInstanceName());
		if (billing == null)
		{
			throw new BusinessLogicException("Биллинговая система с id=" + id + " не найдена.");
		}
	}

	public void doRemove() throws BusinessException, BusinessLogicException
	{
		billingService.remove(billing, getInstanceName());
	}

	/**
	 * Имеются ли поставщики услуг, подключенные к биллинговой системе
	 * @param billingId - идентификатор биллинговой системы
	 * @return true - имеются поставщики услуг, подключенные к биллинговой системе
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public boolean hasProviders(Long billingId) throws BusinessLogicException, BusinessException
	{
		return providerService.findByBillingId(billingId, getInstanceName()) > 0;
	}

	public Billing getEntity()
	{
		return billing;
	}
}
