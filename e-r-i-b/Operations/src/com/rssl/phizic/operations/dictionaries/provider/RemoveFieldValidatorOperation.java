package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.fields.FieldValidationRuleImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

import java.util.Iterator;
import java.util.List;

/**
 * @author krenev
 * @ created 06.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class RemoveFieldValidatorOperation extends RemoveDictionaryEntityOperationBase<FieldValidationRuleImpl, ServiceProvidersRestriction>
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private FieldValidationRuleImpl validator;
	private ServiceProviderBase provider;
	private static String MESSAGE_ACTIVE = "Невозможно сохранить изменения по активному поставщику";

	@Override
	protected Class<?> getEntityClass()
	{
		return provider.getClass();
	}

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		FieldValidationRuleImpl temp = simpleService.findById(FieldValidationRuleImpl.class, id, getInstanceName());
		if (temp == null)
		{
			throw new BusinessException("Валидатор с id = " + id + " не найден");
		}

		Long fieldId = temp.getFieldId();
		FieldDescription tempDescription = simpleService.findById(FieldDescription.class, fieldId, getInstanceName());
		if (tempDescription == null)
		{
			throw new BusinessException("Поле с id = " + fieldId + " не найдено");
		}

		Long holderId = tempDescription.getHolderId();
		ServiceProviderBase tempProvider = providerService.findById(holderId, getInstanceName());
		if (tempProvider == null)
		{
			throw new BusinessException("Поставщик услуг с id = " + holderId + " не найден");
		}

		if (!getRestriction().accept(tempProvider))
		{
			throw new RestrictionViolationException("Поставщик ID= " + tempProvider.getId());
		}
		validator = temp;
		provider = tempProvider;
	}

	private void removeValidator(List<FieldDescription> fields, FieldValidationRuleImpl fieldValidationRule)
	{
		for (FieldDescription fieldDescription : fields)
		{
			if (fieldDescription.getId().equals(fieldValidationRule.getFieldId()))
			{
				Iterator<FieldValidationRule> iterator = fieldDescription.getFieldValidationRules().iterator();
				while (iterator.hasNext())
				{
					FieldValidationRuleImpl current = (FieldValidationRuleImpl) iterator.next();
					if (current.getId().equals(fieldValidationRule.getId()))
					{
						iterator.remove();
						return;
					}
				}
			}
		}
	}

	public void doRemove() throws BusinessLogicException, BusinessException
	{
		if (provider.getState().equals(ServiceProviderState.ACTIVE))
		{
			throw new BusinessLogicException(MESSAGE_ACTIVE);
		}

		removeValidator(provider.getFieldDescriptions(), validator);

		providerService.update(provider, getInstanceName());
	}

	public FieldValidationRuleImpl getEntity()
	{
		return validator;
	}
}