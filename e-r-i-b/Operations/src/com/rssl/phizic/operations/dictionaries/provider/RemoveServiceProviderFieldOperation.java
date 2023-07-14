package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityWithImageOperationBase;

/**
 * @author krenev
 * @ created 06.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class RemoveServiceProviderFieldOperation extends RemoveDictionaryEntityWithImageOperationBase<FieldDescription, ServiceProvidersRestriction>
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private FieldDescription description;
	private BillingServiceProviderBase provider;
	private static String MESSAGE_ACTIVE = "Невозможно сохранить изменения по активному поставщику";

	@Override
	protected Class<?> getEntityClass()
	{
		return provider.getClass();
	}

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		FieldDescription temp = simpleService.findById(FieldDescription.class, id, getInstanceName());
		if (temp == null)
		{
			throw new BusinessException("Поле с id = " + id + " не найдено");
		}

		Long holderId = temp.getHolderId();

		BillingServiceProviderBase tempProvider = (BillingServiceProviderBase) providerService.findById(holderId, getInstanceName());
		if (tempProvider == null)
		{
			throw new BusinessException("Поставщик услуг с id = " + holderId + " не найден");
		}

		if (!getRestriction().accept(tempProvider))
		{
			throw new RestrictionViolationException("Поставщик ID= " + tempProvider.getId());
		}
		provider = tempProvider;
		description = temp;
	}

	public void doRemove() throws BusinessException, BusinessLogicException
	{
		if (provider.getState().equals(ServiceProviderState.ACTIVE))
			throw  new BusinessLogicException(MESSAGE_ACTIVE);

		provider.removeField(description);
		providerService.addOrUpdate(provider, getInstanceName());
	}

	public FieldDescription getEntity()
	{
		return description;
	}
}
