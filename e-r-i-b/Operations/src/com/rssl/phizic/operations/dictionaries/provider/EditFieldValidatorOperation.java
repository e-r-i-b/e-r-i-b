package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.fields.FieldValidationRuleImpl;
import com.rssl.phizic.business.fields.StringFieldValidationParameter;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditFieldValidatorOperation extends EditDictionaryEntityOperationBase<FieldValidationRule, ServiceProvidersRestriction>
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private FieldDescription description;
	private FieldValidationRuleImpl validator;
	private BillingServiceProvider provider;
	private Long fieldId;
    private static String MESSAGE_ACTIVE = "Невозможно сохранить изменения по активному поставщику";

	@Override
	protected Class<?> getEntityClass()
	{
		return provider.getClass();
	}

	/**
	 * Инициализировать операцию для создания нового валидатора
	 * @param id идентифкатор поставщика
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initializeNew(Long id) throws BusinessException
	{
		initializeProvider(id);
		validator = new FieldValidationRuleImpl();
	}

	/**
	 * Инициализировать операцию для редактирования валидатора
	 * @param id идентифкатор валидатора
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		FieldValidationRuleImpl temp = simpleService.findById(FieldValidationRuleImpl.class, id, getInstanceName());
		if (temp == null)
		{
			throw new BusinessException("Валидатор с id = " + id + " не найден");
		}

		FieldDescription tempDescription = simpleService.findById(FieldDescription.class, temp.getFieldId(), getInstanceName());
		if (tempDescription == null)
		{
			throw new BusinessException("Поле с id = " + temp.getFieldId() + " не найдено");
		}

		initializeProvider(tempDescription.getHolderId());

		description = getFieldDescriptionByID(temp.getFieldId());

		//Находим валидатор в поле.
		for (FieldValidationRule validationRule : description.getFieldValidationRules())
		{
			FieldValidationRuleImpl impl = (FieldValidationRuleImpl) validationRule;
			if (impl.getId().equals(id))
			{
				validator = impl;
				break;
			}
		}
	}

	private void initializeProvider(Long providerId) throws BusinessException
	{
		provider = (BillingServiceProvider) providerService.findById(providerId, getInstanceName());
		if (provider == null)
		{
			throw new BusinessException("Поставщик услуг с id = " + providerId + " не найден");
		}

		if (!getRestriction().accept(provider))
		{
			throw new RestrictionViolationException("Поставщик ID= " + provider.getId());
		}
	}

	public void setValidatorRule(String validatorRuleType, Object validatorRule)
	{
		Map<String, Object> fieldValidators = validator.getFieldValidators();
		StringFieldValidationParameter parameter;
		if (fieldValidators == null)
		{
			//новый валидатор
			parameter = new StringFieldValidationParameter();
			parameter.setValue((String) validatorRule);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(validatorRuleType, parameter);
			validator.setFieldValidators(map);
			return;
		}
		parameter = (StringFieldValidationParameter) fieldValidators.get(validatorRuleType);
		parameter.setValue((String) validatorRule);
	}
	
	public void doSave() throws BusinessException, BusinessLogicException
	{
		if (provider.getState().equals(ServiceProviderState.ACTIVE))
			throw new BusinessLogicException(MESSAGE_ACTIVE);

		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					if (validator.getId() != null)
					{
						description.getFieldValidationRules().remove(validator);
					}
					if (!fieldId.equals(validator.getFieldId()))
					{
						validator = new FieldValidationRuleImpl(validator);
						validator.setFieldId(fieldId);
						description = getFieldDescriptionByID(fieldId);
					}
					description.addValidatorRule(validator);
					providerService.update(provider, getInstanceName());
					return null;
				}
			});
		}
		catch (BusinessLogicException e)
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

	public FieldValidationRuleImpl getEntity()
	{
		return validator;
	}

	public FieldDescription getFieldDescription()
	{
		return description;
	}

	public Long getProviderId()
	{
		return provider.getId();
	}

	public List<FieldDescription> getFieldDescriptions()
	{
		return provider.getFieldDescriptions();
	}

	public String getProviderName()
	{
		return provider.getName();
	}

	public String getBillingName()
	{
		return provider.getBilling().getName();
	}

	public String getServiceName()
	{
		return provider.getNameService();
	}

	private FieldDescription getFieldDescriptionByID(Long id) throws BusinessException
	{
		for (FieldDescription description: provider.getFieldDescriptions())
		{
			if (id.equals(description.getId()))
			{
				return description;
			}
		}
		throw new BusinessException("Поле с id = " + id + " не найдено");
	}

	public void setFieldId(Long fieldId) throws BusinessException
	{
		this.fieldId= fieldId;
	}
}