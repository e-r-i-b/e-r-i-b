package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.NotMatchRegexpFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.providers.AccountType;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author khudyakov
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class TBServiceProviderFetcher extends ServiceProviderFetcherBase implements ServiceProviderFetcher
{
	private static final Map<String, FieldValidator[]> fieldValidators = new HashMap<String, FieldValidator[]>();

	static
	{
		fieldValidators.put(PROVIDER_STATE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1,2,3,4]{1}$", PROVIDER_STATE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_STATE_FIELD)});
		fieldValidators.put(PROVIDER_CODE_SERVICE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_SERVICE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_SERVICE_FIELD)});
		fieldValidators.put(PROVIDER_NAME_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_NAME_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_NAME_FIELD)});
		fieldValidators.put(PROVIDER_CODE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_FIELD)});
		fieldValidators.put(PROVIDER_SERVICE_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_SERVICE_TYPE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_SERVICE_TYPE_FIELD)});
		fieldValidators.put(PROVIDER_IS_PROPS_ONLINE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_PROPS_ONLINE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_PROPS_ONLINE_FIELD)});
		fieldValidators.put(PROVIDER_IS_BANK_DETAILS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_BANK_DETAILS_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_BANK_DETAILS_FIELD)});
		fieldValidators.put(PROVIDER_INN_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{10}|\\d{12}$", PROVIDER_INN_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_INN_FIELD)});
		fieldValidators.put(PROVIDER_KPP_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{9}$", PROVIDER_KPP_FIELD)});
		fieldValidators.put(PROVIDER_ACCOUNT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{20,25}$", PROVIDER_ACCOUNT_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_ACCOUNT_FIELD)});
		fieldValidators.put(PROVIDER_BIC_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{9}$", PROVIDER_BIC_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BIC_FIELD)});
		fieldValidators.put(PROVIDER_BANK_NAME_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,320}$", PROVIDER_BANK_NAME_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BANK_NAME_FIELD)});
		fieldValidators.put(PROVIDER_BANK_COR_ACCOUNT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{20}$", PROVIDER_BANK_COR_ACCOUNT_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BANK_COR_ACCOUNT_FIELD)});
		fieldValidators.put(PROVIDER_PHONE_TO_CLIENT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_PHONE_TO_CLIENT_FIELD)});
		fieldValidators.put(PROVIDER_PHONE_TO_CLIENT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", PROVIDER_PHONE_TO_CLIENT_FIELD)});
		fieldValidators.put(PROVIDER_DESCRIPTION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(.|[\\n\\r\\t\\v]){0,200}$", PROVIDER_DESCRIPTION_FIELD)});
		fieldValidators.put(PROVIDER_CODE_REGION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", PROVIDER_CODE_REGION_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_REGION_FIELD)});
		fieldValidators.put(PROVIDER_PAYMENT_CODE_OFFICE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_PAYMENT_CODE_OFFICE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_PAYMENT_CODE_OFFICE_FIELD)});
		fieldValidators.put(PROVIDER_CODE_BS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_BS_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_BS_FIELD)});
		fieldValidators.put(PROVIDER_MIN_COMMISSION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_MIN_COMMISSION_FIELD)});
		fieldValidators.put(PROVIDER_MAX_COMMISSION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_MAX_COMMISSION_FIELD)});
		fieldValidators.put(PROVIDER_PERCENT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_PERCENT_FIELD)});
		fieldValidators.put(PROVIDER_IS_GROUND_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_GROUND_FIELD)});
		fieldValidators.put(PROVIDER_SEPARATOR1_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{1}$", PROVIDER_SEPARATOR1_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_SEPARATOR1_FIELD)});
		fieldValidators.put(PROVIDER_SEPARATOR2_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{1}$", PROVIDER_SEPARATOR2_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_SEPARATOR2_FIELD)});
		fieldValidators.put(PROVIDER_IS_MOBILE_BANK_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_MOBILE_BANK_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_MOBILE_BANK_FIELD)});
		fieldValidators.put(PROVIDER_MOBILE_BANK_CODE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,30}$", PROVIDER_MOBILE_BANK_CODE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_MOBILE_BANK_CODE_FIELD)});
		fieldValidators.put(PROVIDER_PAYMENT_ACCOUNT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,25}$", PROVIDER_PAYMENT_ACCOUNT_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_PAYMENT_ACCOUNT_FIELD)});
		fieldValidators.put(PROVIDER_CODE_OFFICE_NSI_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{1,9}$", PROVIDER_CODE_OFFICE_NSI_FIELD)});
		fieldValidators.put(PROVIDER_ACCOUNT_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0-2]{1}$", PROVIDER_ACCOUNT_TYPE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_ACCOUNT_TYPE_FIELD)});
		fieldValidators.put(PROVIDER_IS_POPULAR_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_POPULAR_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_POPULAR_FIELD)});
		fieldValidators.put(PROVIDER_IS_DEBT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_DEBT_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_DEBT_FIELD)});
		fieldValidators.put(ATTRIBUTE_NAME_BS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[a-zA-Z_:]{1}[a-zA-Z0-9_:]{0,39}$", ATTRIBUTE_NAME_BS_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_NAME_BS_FIELD)});
		fieldValidators.put(ATTRIBUTE_NAME_VISIBLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,60}$", ATTRIBUTE_NAME_VISIBLE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_NAME_VISIBLE_FIELD)});
		fieldValidators.put(COMMENT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(.|[\\n\\r\\t\\v]){0,200}$", COMMENT_FIELD)});
		fieldValidators.put(ATTRIBUTE_DESCRIPTION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(.|[\\n\\r\\t\\v]){0,200}$", ATTRIBUTE_DESCRIPTION_FIELD)});
		fieldValidators.put(ATTRIBUTE_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", ATTRIBUTE_TYPE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_TYPE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_REQUIRED_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_REQUIRED_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_REQUIRED_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_EDITABLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_EDITABLE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_EDITABLE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_VISIBLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_VISIBLE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_VISIBLE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_SUM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_SUM_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_SUM_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_KEY_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_KEY_FIELD)});
		fieldValidators.put(ATTRIBUTE_DEFAULT_VALUE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,1024}$", ATTRIBUTE_DEFAULT_VALUE_FIELD)});
		fieldValidators.put(VALIDATOR_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", TYPE_FIELD), ReplicationHelper.buildRegexpValidator("REGEXP", TYPE_FIELD), ReplicationHelper.buildRequiredValidator(TYPE_FIELD)});
		fieldValidators.put(VALIDATOR_MESSAGE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", VALIDATOR_MESSAGE_FIELD), ReplicationHelper.buildRequiredValidator(VALIDATOR_MESSAGE_FIELD)});
		fieldValidators.put(VALIDATOR_PARAMETER_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", VALIDATOR_PARAMETER_FIELD), ReplicationHelper.buildRequiredValidator(VALIDATOR_PARAMETER_FIELD)});
		fieldValidators.put(ATTRIBUTE_MAX_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_MAX_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_MIN_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_MIN_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_VALUES_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", ATTRIBUTE_VALUES_FIELD)});
	}

	protected Map<String, FieldValidator[]> getFieldValidators()
	{
		return Collections.unmodifiableMap(fieldValidators);
	}

	TBServiceProviderFetcher(List<String> errorCollector, String dbInstanceName)
	{
		super(errorCollector, dbInstanceName);
	}

	/**
	 * Собрать поставщика услуг
	 * @param source данные
	 */
	public void collect(Map<String, Object> source)
	{
		List<String> errorsCollector = getErrors();
		BillingServiceProvider provider = getProvider();

		String providerCode = (String) source.get(PROVIDER_CODE_FIELD);
		if (validate(providerCode, errorsCollector, fieldValidators.get(PROVIDER_CODE_FIELD)))
		{
			provider.setCode(providerCode);
			provider.setCodeRecipientSBOL(providerCode);
		}

		String providerState = (String) source.get(PROVIDER_STATE_FIELD);
		if (validate(providerState, errorsCollector, fieldValidators.get(PROVIDER_STATE_FIELD)))
			provider.setState(ServiceProviderState.fromValue(Integer.parseInt(providerState)));

		String providerPaymentServiceCode = (String) source.get(PROVIDER_SERVICE_TYPE_FIELD);
		if (validate(providerPaymentServiceCode, errorsCollector, fieldValidators.get(PROVIDER_SERVICE_TYPE_FIELD)))
		{
			List<PaymentService> paymentServices = new ArrayList<PaymentService>();
			PaymentService paymentService        = ReplicationHelper.findPaymentService(providerPaymentServiceCode, getDbInstanceName());
			if (validate(PROVIDER_SERVICE_TYPE_FIELD, providerPaymentServiceCode, paymentService, errorsCollector))
			{
				try
				{
					PaymentServiceService paymentServiceService = new PaymentServiceService();
					List childPaymentServices = paymentServiceService.getChildren(paymentService);
					if (childPaymentServices.size() != 0)
					{
						paymentServices.addAll(childPaymentServices);
					}
					else
					{
						paymentServices.add(paymentService);
					}
				}
				catch (BusinessException e)
				{
					errorsCollector.add("Ошибка при получении списка дочерних услуг услуги " + paymentService.getName());
					setReplicated(false);
				}
				getWrapper().getPaymentServices().addAll(paymentServices);
			}
		}

		String providerCodeService = (String) source.get(PROVIDER_CODE_SERVICE_FIELD);
		if (validate(providerCodeService, errorsCollector, fieldValidators.get(PROVIDER_CODE_SERVICE_FIELD)))
			provider.setCodeService(providerCodeService);

		String providerName = (String) source.get(PROVIDER_NAME_FIELD);
		if (validate(providerName, errorsCollector, fieldValidators.get(PROVIDER_NAME_FIELD)))
		{
			provider.setName(providerName);
		}

		String propsOnline = (String) source.get(PROVIDER_IS_PROPS_ONLINE_FIELD);
		if (validate(propsOnline, errorsCollector, fieldValidators.get(PROVIDER_IS_PROPS_ONLINE_FIELD)))
			provider.setPropsOnline(ReplicationHelper.parseBooleanValue(propsOnline));

		boolean isBankDetails = false;
		String bankDetails = (String) source.get(PROVIDER_IS_BANK_DETAILS_FIELD);
		if (validate(bankDetails, errorsCollector, fieldValidators.get(PROVIDER_IS_BANK_DETAILS_FIELD)))
		{
			isBankDetails = ReplicationHelper.parseBooleanValue(bankDetails);
			provider.setBankDetails(isBankDetails);
		}

		setProviderBankDetails(source, isBankDetails, false);

		String phoneNumber = (String) source.get(PROVIDER_PHONE_TO_CLIENT_FIELD);
		if (validate(phoneNumber, errorsCollector, fieldValidators.get(PROVIDER_PHONE_TO_CLIENT_FIELD)))
			provider.setPhoneNumber(phoneNumber);

		String providerDescription = (String) source.get(PROVIDER_DESCRIPTION_FIELD);
		if (validate(providerDescription, errorsCollector, fieldValidators.get(PROVIDER_DESCRIPTION_FIELD)))
			provider.setDescription(providerDescription);

		List<String> regionsMap = (List<String>) source.get(PROVIDER_CODE_REGION_FIELD);
		if (regionsMap != null && !regionsMap.isEmpty())
		{
			Set<Region> providerRegions = new HashSet<Region>();
			for (String regionCode : regionsMap)
			{
				if (validate(regionCode, errorsCollector, fieldValidators.get(PROVIDER_CODE_REGION_FIELD)))
				{
					Region region = ReplicationHelper.findRegion(regionCode, getDbInstanceName());
					if (validate(PROVIDER_CODE_REGION_FIELD, regionCode, region, errorsCollector))
						providerRegions.add(region);
				}
			}
			provider.setRegions(providerRegions);
		}
		else
		{
			errorsCollector.add("У поставщика услуг отсутствует привязка к регионам.");
			setReplicated(false);
		}

		String providerDepartmentCode = (String) source.get(PROVIDER_PAYMENT_CODE_OFFICE_FIELD);
		if (validate(providerDepartmentCode, errorsCollector, fieldValidators.get(PROVIDER_PAYMENT_CODE_OFFICE_FIELD)))
		{
			Department department = ReplicationHelper.findDepartment(providerDepartmentCode, getDbInstanceName());
			if (validate(PROVIDER_PAYMENT_CODE_OFFICE_FIELD, providerDepartmentCode, department, errorsCollector))
				provider.setDepartmentId(department.getId());
		}

		String providerTransitAccount = (String) source.get(PROVIDER_PAYMENT_ACCOUNT_FIELD);
		if (validate(providerTransitAccount, errorsCollector, fieldValidators.get(PROVIDER_PAYMENT_ACCOUNT_FIELD)))
			provider.setTransitAccount(providerTransitAccount);

		String providerNSICode = (String) source.get(PROVIDER_CODE_OFFICE_NSI_FIELD);
		if (validate(providerNSICode, errorsCollector, fieldValidators.get(PROVIDER_CODE_OFFICE_NSI_FIELD)))
			provider.setNSICode(StringHelper.getEmptyIfNull(providerNSICode));

		String providerAccountType = (String) source.get(PROVIDER_ACCOUNT_TYPE_FIELD);
		if (validate(providerAccountType, errorsCollector, fieldValidators.get(PROVIDER_ACCOUNT_TYPE_FIELD)))
			provider.setAccountType(AccountType.fromValue(Integer.parseInt(providerAccountType)));

		String debt = (String) source.get(PROVIDER_IS_DEBT_FIELD);
		if (validate(debt, errorsCollector, fieldValidators.get(PROVIDER_IS_DEBT_FIELD)))
			provider.setDeptAvailable(ReplicationHelper.parseBooleanValue(debt));

		String popular = (String) source.get(PROVIDER_IS_POPULAR_FIELD);
		if (validate(popular, errorsCollector, fieldValidators.get(PROVIDER_IS_POPULAR_FIELD)))
			provider.setPopular(ReplicationHelper.parseBooleanValue(popular));

		String codeBilling = (String) source.get(PROVIDER_CODE_BS_FIELD);
		if (validate(codeBilling, errorsCollector, fieldValidators.get(PROVIDER_CODE_BS_FIELD)))
		{
			Billing billing = ReplicationHelper.findBilling(codeBilling, getDbInstanceName());
			if (validate(PROVIDER_CODE_BS_FIELD, codeBilling, billing, errorsCollector))
			{
				provider.setBilling(billing);

				String maxCommissionAmount = (String) source.get(PROVIDER_MAX_COMMISSION_FIELD);
				if (validate(maxCommissionAmount, errorsCollector, fieldValidators.get(PROVIDER_MAX_COMMISSION_FIELD)))
					provider.setMaxComissionAmount(ReplicationHelper.parseBigDecimalValue(maxCommissionAmount));

				String minCommissionAmount = (String) source.get(PROVIDER_MIN_COMMISSION_FIELD);
				if (validate(minCommissionAmount, errorsCollector, fieldValidators.get(PROVIDER_MIN_COMMISSION_FIELD)))
					provider.setMinComissionAmount(ReplicationHelper.parseBigDecimalValue(minCommissionAmount));

				String commissionRate = (String) source.get(PROVIDER_PERCENT_FIELD);
				if (validate(commissionRate, errorsCollector, fieldValidators.get(PROVIDER_PERCENT_FIELD)))
					provider.setComissionRate(ReplicationHelper.parseBigDecimalValue(commissionRate));
			}
		}

		boolean ground = false;
		String groundMap = (String) source.get(PROVIDER_IS_GROUND_FIELD);
		if (validate(groundMap, errorsCollector, fieldValidators.get(PROVIDER_IS_GROUND_FIELD)))
		{
			ground = ReplicationHelper.parseBooleanValue(groundMap);
			provider.setGround(ground);
		}

		if (ground)
		{
			String attrDelimiter = (String) source.get(PROVIDER_SEPARATOR1_FIELD);
			if (validate(attrDelimiter, errorsCollector, fieldValidators.get(PROVIDER_SEPARATOR1_FIELD)))
				provider.setAttrDelimiter(attrDelimiter);

			String attrValuesDelimiter = (String) source.get(PROVIDER_SEPARATOR2_FIELD);
			if (validate(attrValuesDelimiter, errorsCollector, fieldValidators.get(PROVIDER_SEPARATOR2_FIELD)))
				provider.setAttrValuesDelimiter(attrValuesDelimiter);
		}

		boolean mobileBank = false;
		String mobileBankMap = (String) source.get(PROVIDER_IS_MOBILE_BANK_FIELD);
		if (validate(mobileBankMap, errorsCollector, fieldValidators.get(PROVIDER_IS_MOBILE_BANK_FIELD)))
		{
			mobileBank = ReplicationHelper.parseBooleanValue(mobileBankMap);
			provider.setMobilebank(mobileBank);
		}

		if (mobileBank)
		{
			String mobileBankCode  = (String) source.get(PROVIDER_MOBILE_BANK_CODE_FIELD);
			if (validate(mobileBankCode, errorsCollector, fieldValidators.get(PROVIDER_MOBILE_BANK_CODE_FIELD)))
			{
				BillingServiceProvider mobileProvider = ReplicationHelper.findProviderByMobileBankCode(mobileBankCode, getDbInstanceName());
				if (mobileProvider != null && !mobileProvider.getSynchKey().equals(provider.getSynchKey()))
				{
					errorsCollector.add(String.format("В справочнике системы уже существует поставщик услуг, которому присвоен код мобильного банка %s.", mobileBankCode));
					setReplicated(false);
				}
				provider.setMobilebankCode(mobileBankCode);
			}
		}

		if (provider.getBilling() != null && !StringHelper.isEmpty(providerCodeService)
				&& !StringHelper.isEmpty(providerCode))
		{
			String synchKey = new StringBuffer().append(providerCodeService).append(ADDITIONAL_DELIMITER)
								.append(providerCode).append(DELIMITER).append(provider.getBilling().restoreRouteInfo()).toString();

			provider.setSynchKey(synchKey);
		}
		else
		{
			errorsCollector.add(String.format("Не задано значение для поля %s.", PROVIDER_SYNCH_KEY_FIELD));
			setReplicated(false);
		}

		provider.setTemplateSupported(true);
		provider.setAvailablePaymentsForInternetBank(true);
		provider.setAvailablePaymentsForAtmApi(true);
		provider.setAvailablePaymentsForSocialApi(true);
		provider.setAvailablePaymentsForErmb(true);
		provider.setAvailablePaymentsForMApi(true);
		provider.setFederal(false);
		provider.setCreationDate(Calendar.getInstance());
		provider.setSortPriority(0L);
		provider.setVersionAPI(DEFAULT_VERSION_API);
		//устанавливаем группу риска с признаком «По умолчанию», если она заведена в системе. Если такой группы нет, то у поставщика группа риска не выбрана(останется null).
		provider.setGroupRisk(ReplicationHelper.findDefaultGroupRisk(getDbInstanceName()));
		//заполняем дополнительные поля поставщика услуг
		setProviderAttributesFields(source);
	}

	protected void setFieldAdditionalAttributes(Map<String, Object> mapAttribute, FieldDescription field)
	{
		List<String> errorsCollector = getErrors();
		field.setRequiredForBill(false);
		field.setRequiredForConformation(false);
		field.setSaveInTemplate(false);
		field.setHideInConfirmation(false);

		String values = (String) mapAttribute.get(ATTRIBUTE_VALUES_FIELD);
		if (FieldDataType.list.toString().equals(field.getType()))
		{
			if (validate(values, errorsCollector, fieldValidators.get(ATTRIBUTE_VALUES_FIELD)))
				field.setValuesAsString(values, FieldDescription.VALUES_SPLITER);
		}
		else
		{
			validate(values, errorsCollector, new NotMatchRegexpFieldValidator(".*",
					"Для поля " + field.getExternalId() + " не допускается указывать Values"));
		}
	}
}
