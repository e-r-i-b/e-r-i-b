package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.fields.FieldValidationRuleImpl;
import com.rssl.phizic.business.fields.StringFieldValidationParameter;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author khudyakov
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class ServiceProviderFetcherBase implements ServiceProviderFetcher
{
	protected static final String EMPTY_MENU_ITEMS_ERROR_MESSAGE = "Репликация поставщиков невозможна. В файле загрузки для полей типа \"set\" и \"list\" укажите список значений.";

	private static Map<String, String> banks = new HashMap<String, String>();

	private List<String> errorsCollector;
	private BillingServiceProvider provider;
	private ServiceProviderForReplicationWrapper wrapper;
	private boolean replicated;
	private String dbInstanceName;

	ServiceProviderFetcherBase(List<String> errorsCollector, String dbInstanceName)
	{
		this.errorsCollector = errorsCollector;
		wrapper = new ServiceProviderForReplicationWrapper(new BillingServiceProvider(), new HashSet<PaymentService>());
		provider = wrapper.getProvider();
		replicated = true;
		this.dbInstanceName = dbInstanceName;
	}

	String getDbInstanceName()
	{
		return dbInstanceName;
	}

	/**
	 * Проверка на наличие значения
	 * @param fieldValue      значение
	 * @param errorsCollector хранилище ошибок
	 * @param fieldValidators валидаторы
	 * @return true/false
	 */
	protected boolean validate(String fieldValue, List<String> errorsCollector, FieldValidator... fieldValidators)
	{
		return validate(fieldValue, true, errorsCollector, fieldValidators);
	}

	/**
	 * Проверка на наличие значения
	 * @param fieldValue      значение
	 * @param mandatory       если поле не обязательное, но сообщение об ошибке должно вывестись на экран
	 * @param errorsCollector хранилище ошибок
	 * @param fieldValidators валидаторы
	 * @return true/false
	 */
	protected boolean validate(String fieldValue, boolean mandatory, List<String> errorsCollector, FieldValidator... fieldValidators)
	{
		boolean valid = true;
		if (!ReplicationHelper.validate(fieldValue, errorsCollector, fieldValidators))
		{
			if (!mandatory)
				return false;

			valid = false;
			replicated = false;
		}
		return valid;
	}

	/**
	 * Проверка на наличие значения
	 * @param fieldName  имя поля
	 * @param fieldValue значение поля
	 * @param obj        значение как объекс в б.д. ИКФЛ
	 * @param errorsCollector хранилище ошибок
	 * @return true/false
	 */
	protected boolean validate(String fieldName, String fieldValue, Object obj, List<String> errorsCollector)
	{
		boolean valid = true;
		if (!ReplicationHelper.validate(fieldName, fieldValue, obj, errorsCollector))
		{
			valid = false;
			replicated = false;
		}
		return valid;
	}

	/**
	 * @return ошибки репликации поставщика услуг
	 */
	public List<String> getErrors()
	{
		return errorsCollector;
	}

	/**
	 * @return реплицируемый поставщик услуг
	 */
	public BillingServiceProvider getProvider()
	{
		return provider;
	}

	void setProvider(BillingServiceProvider provider)
	{
		this.provider = provider;
		wrapper.setProvider(provider);
	}

	public ServiceProviderForReplicationWrapper getWrapper()
	{
		return wrapper;
	}

	/**
	 * Реплицируемый ли поставщик услуг
	 * @return да/нет
	 */
	public boolean isReplicated()
	{
		return replicated;
	}

	protected void setReplicated(boolean replicated)
	{
		this.replicated = replicated;
	}

	protected Map<String, String> getBanks()
	{
		return banks;
	}

	protected void addBank(String key, String value)
	{
		banks.put(key, value);
	}

	/**
	 * Получение списка валидаторов полей для заданного фетчера
	 * @return список валидаторов полей.
	 */
	protected abstract Map<String, FieldValidator[]> getFieldValidators();

	/**
	 * Заполнение дополнительных атрибутов поставщика услуг,
	 * используемых как реквизиты в платежах клиентов в адрес этого поставщика услуг.
	 * @param source - данные репликации.
	 */
	protected void setProviderAttributesFields(Map<String, Object> source)
	{
		List<FieldDescription> fieldDescriptions = new ArrayList<FieldDescription>();
		Map<String, FieldValidator[]> fieldValidators = getFieldValidators();
		List<Map<String, Object>> mapAttributes = (List<Map<String, Object>>) source.get(ATTRIBUTES_FIELD);
		if (mapAttributes != null)
		{
			for (Map<String, Object> mapAttribute : mapAttributes)
			{
				FieldDescription field = new FieldDescription();

				String attributeExternalId = (String) mapAttribute.get(ATTRIBUTE_NAME_BS_FIELD);
				if (validate(attributeExternalId, errorsCollector, fieldValidators.get(ATTRIBUTE_NAME_BS_FIELD)))
					field.setExternalId(attributeExternalId);

				String attributeName = (String) mapAttribute.get(ATTRIBUTE_NAME_VISIBLE_FIELD);
				if (validate(attributeName, errorsCollector, fieldValidators.get(ATTRIBUTE_NAME_VISIBLE_FIELD)))
					field.setName(attributeName);

				String attributeHint = (String) mapAttribute.get(COMMENT_FIELD);
				if (validate(attributeHint, errorsCollector, fieldValidators.get(COMMENT_FIELD)))
					field.setHint(attributeHint);

				String attributeDescription = (String) mapAttribute.get(ATTRIBUTE_DESCRIPTION_FIELD);
				if (validate(attributeDescription, errorsCollector, fieldValidators.get(ATTRIBUTE_DESCRIPTION_FIELD)))
					field.setDescription(attributeDescription);

				String attributeType = (String) mapAttribute.get(ATTRIBUTE_TYPE_FIELD);
				if (validate(attributeType, errorsCollector, fieldValidators.get(ATTRIBUTE_TYPE_FIELD)))
				{
					FieldDataType type = FieldDataType.fromValue(attributeType.toLowerCase());
					// Поля типа set или list должны иметь список значений
					if ((type == FieldDataType.set || type == FieldDataType.list) && StringHelper.isEmpty((String) mapAttribute.get(ATTRIBUTE_MENU_FIELD)))
					{
						errorsCollector.add(EMPTY_MENU_ITEMS_ERROR_MESSAGE);
						setReplicated(false);
					}
					else
						field.setType(type);
				}

				String length    = (String) mapAttribute.get(ATTRIBUTE_LENGTH_FIELD);
				String maxLength = (String) mapAttribute.get(ATTRIBUTE_MAX_LENGTH_FIELD);

				if (validate(length, false, errorsCollector, fieldValidators.get(ATTRIBUTE_LENGTH_FIELD))
						|| validate(length, errorsCollector, fieldValidators.get(ATTRIBUTE_MAX_LENGTH_FIELD)))
				{
					if (!StringHelper.isEmpty(maxLength))
					{
						field.setMaxLength(Long.parseLong(maxLength));
					}
					else if (!StringHelper.isEmpty(maxLength))
					{
						field.setMaxLength(Long.parseLong(length));
					}
				}

				String minLength = (String) mapAttribute.get(ATTRIBUTE_MIN_LENGTH_FIELD);
				if (validate(length, false, errorsCollector, fieldValidators.get(ATTRIBUTE_LENGTH_FIELD))
						|| validate(length, errorsCollector, fieldValidators.get(ATTRIBUTE_MIN_LENGTH_FIELD)))
				{
					if (!StringHelper.isEmpty(minLength))
					{
						field.setMinLength(Long.parseLong(minLength));
					}
					else if (!StringHelper.isEmpty(minLength))
					{
						field.setMinLength(Long.parseLong(length));
					}
				}

				String required = (String) mapAttribute.get(ATTRIBUTE_IS_REQUIRED_FIELD);
				if (validate(required, errorsCollector, fieldValidators.get(ATTRIBUTE_IS_REQUIRED_FIELD)))
					field.setRequired(ReplicationHelper.parseBooleanValue(required));

				String editable = (String) mapAttribute.get(ATTRIBUTE_IS_EDITABLE_FIELD);
				if (validate(editable, errorsCollector, fieldValidators.get(ATTRIBUTE_IS_EDITABLE_FIELD)))
					field.setEditable(ReplicationHelper.parseBooleanValue(editable));

				String visible = (String) mapAttribute.get(ATTRIBUTE_IS_VISIBLE_FIELD);
				if (validate(visible, errorsCollector, fieldValidators.get(ATTRIBUTE_IS_VISIBLE_FIELD)))
					field.setVisible(ReplicationHelper.parseBooleanValue(visible));

				String mainSum = (String) mapAttribute.get(ATTRIBUTE_IS_SUM_FIELD);
				if (validate(mainSum, errorsCollector, fieldValidators.get(ATTRIBUTE_IS_SUM_FIELD)))
					field.setMainSum(ReplicationHelper.parseBooleanValue(mainSum));

				String key = (String) mapAttribute.get(ATTRIBUTE_IS_KEY_FIELD);
				if (validate(key, errorsCollector, fieldValidators.get(ATTRIBUTE_IS_KEY_FIELD)))
					field.setKey(ReplicationHelper.parseBooleanValue(key));

				//сетим доп. значения для полей для каждого типа репликации.
				setFieldAdditionalAttributes(mapAttribute, field);

				String defaultValues = (String) mapAttribute.get(ATTRIBUTE_DEFAULT_VALUE_FIELD);
				if (validate(defaultValues, errorsCollector, fieldValidators.get(ATTRIBUTE_DEFAULT_VALUE_FIELD)))
					field.setDefaultValue(defaultValues);

				//заполняем поля валидаторов дополнительных полей поставщика услуг
				List<Map<String, Object>> validators = (List<Map<String, Object>>) mapAttribute.get(mapAttribute.get(ATTRIBUTE_NAME_BS_FIELD) + DELIMITER + VALIDATORS_FIELD);
				if (validators != null)
				{
					List<FieldValidationRule> validationRules = new ArrayList<FieldValidationRule>();
					for(Map<String, Object> mapValidator : validators)
					{
						FieldValidationRuleImpl rule = new FieldValidationRuleImpl();

						String validatorType = (String) mapValidator.get(VALIDATOR_TYPE_FIELD);
						if (validate(validatorType.toUpperCase(), errorsCollector, fieldValidators.get(VALIDATOR_TYPE_FIELD)))
							rule.setFieldValidationRuleType(FieldValidationRuleType.fromValue(validatorType));

						String validatorErrorMessage = (String) mapValidator.get(VALIDATOR_MESSAGE_FIELD);
						if (validate(validatorErrorMessage, errorsCollector, fieldValidators.get(VALIDATOR_MESSAGE_FIELD)))
							rule.setErrorMessage(validatorErrorMessage);

						String validatorParameter = (String) mapValidator.get(VALIDATOR_PARAMETER_FIELD);
						if (validate(validatorParameter, errorsCollector, fieldValidators.get(VALIDATOR_PARAMETER_FIELD)))
						{
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("string", new StringFieldValidationParameter(validatorParameter));

							rule.setFieldValidators(map);
						}
						validationRules.add(rule);
					}
					field.setFieldValidationRules(validationRules);
				}
				fieldDescriptions.add(field);
			}
		}
		validateFieldDescriptions(fieldDescriptions);
		provider.setFieldDescriptions(fieldDescriptions);
	}

	/**
	 * Заполнение дополнительных параметров атрибута поставщика услуг.
	 * @param mapAttribute - данные репликации
	 * @param field - текущее поле для обработки.
	 */
	protected abstract void setFieldAdditionalAttributes(Map<String, Object> mapAttribute, FieldDescription field);

	/**
	 * Валидация доп аттрибутов ПУ в рамках типа репликации.
	 * @param fieldDescriptions - список аттрибутов.
	 */
	protected void validateFieldDescriptions(List<FieldDescription> fieldDescriptions) {}

	/**
	 * Построить предоставляемые группы услуг ПУ по данным репликации
	 * @param source - данные репликации.
	 * @return список услуг/null-если в файле репликации нет данных.
	 */
	protected List<PaymentService> makeGoroupServices(Map<String, Object> source)
	{
		List<String> mapPaymentServices = (List<String>) source.get(PROVIDER_GRP_SERVICES_FIELD);
		Map<String, FieldValidator[]> fieldValidators = getFieldValidators();
		if (mapPaymentServices != null)
		{
			List<PaymentService> paymentServices = new ArrayList<PaymentService>();
			PaymentServiceService paymentServiceService = new PaymentServiceService();
			for (String codeService : mapPaymentServices)
			{
				if (validate(codeService, errorsCollector, fieldValidators.get(PROVIDER_GRP_SERVICE_CODE_FIELD)))
				{
					PaymentService paymentService = ReplicationHelper.findPaymentService(codeService, getDbInstanceName());
					if (validate(PROVIDER_GRP_SERVICE_CODE_FIELD, codeService, paymentService, errorsCollector))
					{
						//если есть дети, то есть услуга корневая то добавляем дочерние усдуги
						try
						{
							List childPaymentServices = paymentServiceService.getChildren(paymentService, getDbInstanceName());
							if(!childPaymentServices.isEmpty())
							{
								paymentServices.addAll(childPaymentServices);
							}
							else
							{
								paymentServices.add(paymentService);
							}
						}
						catch(BusinessException e)
						{
							errorsCollector.add("Ошибка при получении списка дочерних услуг услуги " + paymentService.getName());
							setReplicated(false);
						}
					}
				}
			}
			return paymentServices;
		}
		return null;
	}

	/**
	 * Для заданого provider уставноить банковские реквизиты в соответствии с данными репликации.
	 * @param source - данные репликации
	 * @param isBankDetails - признак необходимости установки банковских реквизитов
	 * @param definitiveInnKppAdding - признак безусловного добавления реквизитов ИНН и КПП.
	 */
	protected void setProviderBankDetails(Map<String, Object> source, boolean isBankDetails, boolean definitiveInnKppAdding)
	{
		Map<String, FieldValidator[]> fieldValidators = getFieldValidators();
		
		if(definitiveInnKppAdding)
		{
			String providerINN = (String) source.get(PROVIDER_INN_FIELD);
			if (validate(providerINN, errorsCollector, fieldValidators.get(PROVIDER_INN_FIELD)))
				provider.setINN(providerINN);

			String providerKPP = (String) source.get(PROVIDER_KPP_FIELD);
			if (validate(providerKPP, errorsCollector, fieldValidators.get(PROVIDER_KPP_FIELD)))
				provider.setKPP(providerKPP);
		}

		if(isBankDetails)
		{
			if(!definitiveInnKppAdding)
			{
				String providerINN = (String) source.get(PROVIDER_INN_FIELD);
				if (validate(providerINN, errorsCollector, fieldValidators.get(PROVIDER_INN_FIELD)))
					provider.setINN(providerINN);

				String providerKPP = (String) source.get(PROVIDER_KPP_FIELD);
				if (validate(providerKPP, errorsCollector, fieldValidators.get(PROVIDER_KPP_FIELD)))
					provider.setKPP(providerKPP);
			}

			String providerAccount = (String) source.get(PROVIDER_ACCOUNT_FIELD);
			if (validate(providerAccount, errorsCollector, fieldValidators.get(PROVIDER_ACCOUNT_FIELD)))
				provider.setAccount(providerAccount);

			String providerBIC = (String) source.get(PROVIDER_BIC_FIELD);
			if (validate(providerBIC, errorsCollector, fieldValidators.get(PROVIDER_BIC_FIELD)))
			{
				if (getBanks().get(providerBIC) != null)
				{
					String[] bank = getBanks().get(providerBIC).split(SPLITER);
					provider.setCorrAccount(bank[0]);
					provider.setBankName(bank[1]);
				}
				else
				{
					ResidentBank bank = ReplicationHelper.findResidentBank(providerBIC, getDbInstanceName());
					if (validate(PROVIDER_BIC_FIELD, providerBIC, bank, errorsCollector))
					{
						String corAccount = StringHelper.isEmpty(bank.getAccount()) ? "" : bank.getAccount();
						addBank(providerBIC, corAccount + DELIMITER + bank.getName());
						provider.setCorrAccount(corAccount);
						provider.setBankName(bank.getName());
					}
					else
					{
						String providerBankName = (String) source.get(PROVIDER_BANK_NAME_FIELD);
						if (validate(providerBankName, errorsCollector, fieldValidators.get(PROVIDER_BANK_NAME_FIELD)))
							provider.setBankName(providerBankName);

						String providerBankCorAccount = (String) source.get(PROVIDER_BANK_COR_ACCOUNT_FIELD);
						if (validate(providerBankCorAccount, errorsCollector, fieldValidators.get(PROVIDER_BANK_COR_ACCOUNT_FIELD)))
							provider.setCorrAccount(providerBankCorAccount);
					}
				}
				provider.setBIC(providerBIC);
			}
		}
	}
}
