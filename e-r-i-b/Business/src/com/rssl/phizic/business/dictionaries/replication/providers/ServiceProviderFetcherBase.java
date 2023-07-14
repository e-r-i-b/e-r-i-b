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
	protected static final String EMPTY_MENU_ITEMS_ERROR_MESSAGE = "���������� ����������� ����������. � ����� �������� ��� ����� ���� \"set\" � \"list\" ������� ������ ��������.";

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
	 * �������� �� ������� ��������
	 * @param fieldValue      ��������
	 * @param errorsCollector ��������� ������
	 * @param fieldValidators ����������
	 * @return true/false
	 */
	protected boolean validate(String fieldValue, List<String> errorsCollector, FieldValidator... fieldValidators)
	{
		return validate(fieldValue, true, errorsCollector, fieldValidators);
	}

	/**
	 * �������� �� ������� ��������
	 * @param fieldValue      ��������
	 * @param mandatory       ���� ���� �� ������������, �� ��������� �� ������ ������ ��������� �� �����
	 * @param errorsCollector ��������� ������
	 * @param fieldValidators ����������
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
	 * �������� �� ������� ��������
	 * @param fieldName  ��� ����
	 * @param fieldValue �������� ����
	 * @param obj        �������� ��� ������ � �.�. ����
	 * @param errorsCollector ��������� ������
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
	 * @return ������ ���������� ���������� �����
	 */
	public List<String> getErrors()
	{
		return errorsCollector;
	}

	/**
	 * @return ������������� ��������� �����
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
	 * ������������� �� ��������� �����
	 * @return ��/���
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
	 * ��������� ������ ����������� ����� ��� ��������� �������
	 * @return ������ ����������� �����.
	 */
	protected abstract Map<String, FieldValidator[]> getFieldValidators();

	/**
	 * ���������� �������������� ��������� ���������� �����,
	 * ������������ ��� ��������� � �������� �������� � ����� ����� ���������� �����.
	 * @param source - ������ ����������.
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
					// ���� ���� set ��� list ������ ����� ������ ��������
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

				//����� ���. �������� ��� ����� ��� ������� ���� ����������.
				setFieldAdditionalAttributes(mapAttribute, field);

				String defaultValues = (String) mapAttribute.get(ATTRIBUTE_DEFAULT_VALUE_FIELD);
				if (validate(defaultValues, errorsCollector, fieldValidators.get(ATTRIBUTE_DEFAULT_VALUE_FIELD)))
					field.setDefaultValue(defaultValues);

				//��������� ���� ����������� �������������� ����� ���������� �����
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
	 * ���������� �������������� ���������� �������� ���������� �����.
	 * @param mapAttribute - ������ ����������
	 * @param field - ������� ���� ��� ���������.
	 */
	protected abstract void setFieldAdditionalAttributes(Map<String, Object> mapAttribute, FieldDescription field);

	/**
	 * ��������� ��� ���������� �� � ������ ���� ����������.
	 * @param fieldDescriptions - ������ ����������.
	 */
	protected void validateFieldDescriptions(List<FieldDescription> fieldDescriptions) {}

	/**
	 * ��������� ��������������� ������ ����� �� �� ������ ����������
	 * @param source - ������ ����������.
	 * @return ������ �����/null-���� � ����� ���������� ��� ������.
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
						//���� ���� ����, �� ���� ������ �������� �� ��������� �������� ������
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
							errorsCollector.add("������ ��� ��������� ������ �������� ����� ������ " + paymentService.getName());
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
	 * ��� �������� provider ���������� ���������� ��������� � ������������ � ������� ����������.
	 * @param source - ������ ����������
	 * @param isBankDetails - ������� ������������� ��������� ���������� ����������
	 * @param definitiveInnKppAdding - ������� ������������ ���������� ���������� ��� � ���.
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
