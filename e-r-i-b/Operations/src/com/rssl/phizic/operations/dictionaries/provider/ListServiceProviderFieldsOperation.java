package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.fields.FieldDescription;

import java.util.List;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListServiceProviderFieldsOperation extends ServiceProviderListEntitiesOperationBase
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private BillingServiceProvider provider;
	private List<FieldDescription> fieldList;
	private FieldDescription field;

	public void initialize(Long providerId, Long fieldId) throws BusinessLogicException, BusinessException
	{
		provider = (BillingServiceProvider) serviceProviderService.findById(providerId, getInstanceName());
		fieldList = provider.getFieldDescriptions();
		field = initializeField(fieldId);
	}

	private FieldDescription initializeField(Long fieldId) throws BusinessLogicException
	{
		for (FieldDescription fieldDescription: fieldList)
		{
			if (fieldDescription.getId().equals(fieldId))
			{
				return fieldDescription;
			}
		}
		throw new BusinessLogicException("Поле с id=" + fieldId + " не найдено.");
	}

	/**
	 * поднимаем запись выше по списку
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void upInList() throws BusinessException, BusinessLogicException
	{
		int index = fieldList.indexOf(field);
		fieldList.remove(field);
		fieldList.add(index-1, field);
		serviceProviderService.addOrUpdate(provider, getInstanceName());
		MultiBlockModeDictionaryHelper.updateDictionary(provider.getClass());
	}

	/**
	 * опускаем запись ниже по списку
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void downInList() throws BusinessException, BusinessLogicException
	{
		int index = fieldList.indexOf(field);
		fieldList.remove(field);
		fieldList.add(index+1, field);
		serviceProviderService.addOrUpdate(provider, getInstanceName());
		MultiBlockModeDictionaryHelper.updateDictionary(provider.getClass());
	}
}
