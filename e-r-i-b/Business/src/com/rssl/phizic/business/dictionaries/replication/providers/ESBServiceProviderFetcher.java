package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.AccountType;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author khudyakov
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class ESBServiceProviderFetcher extends ServiceProviderFetcherBase implements ServiceProviderFetcher
{
	private static final Map<String, FieldValidator[]> fieldValidators = new HashMap<String, FieldValidator[]>();

	static
	{
		fieldValidators.put(PROVIDER_STATE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0-4]{1}$", PROVIDER_STATE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_STATE_FIELD)});
		fieldValidators.put(PROVIDER_CODE_SERVICE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_SERVICE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_SERVICE_FIELD)});
		fieldValidators.put(PROVIDER_NAME_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_NAME_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_NAME_FIELD)});
		fieldValidators.put(PROVIDER_CODE_RECIPIENT_SBOL_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", PROVIDER_CODE_RECIPIENT_SBOL_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_RECIPIENT_SBOL_FIELD)});
		fieldValidators.put(PROVIDER_CODE_RECIPIENT_BS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_RECIPIENT_BS_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_RECIPIENT_BS_FIELD)});
		fieldValidators.put(PROVIDER_GRP_SERVICE_CODE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,14}$", PROVIDER_GRP_SERVICE_CODE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_GRP_SERVICE_CODE_FIELD)});
		fieldValidators.put(PROVIDER_NAME_SERVICE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,150}$", PROVIDER_NAME_SERVICE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_NAME_SERVICE_FIELD)});
		fieldValidators.put(PROVIDER_ALIAS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_ALIAS_FIELD)});
		fieldValidators.put(PROVIDER_LEGAL_NAME_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_LEGAL_NAME_FIELD)});
		fieldValidators.put(PROVIDER_NAME_ON_BILL_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_NAME_ON_BILL_FIELD)});
		fieldValidators.put(PROVIDER_INN_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{10}|\\d{12}$", PROVIDER_INN_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_INN_FIELD)});
		fieldValidators.put(PROVIDER_KPP_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{9}$", PROVIDER_KPP_FIELD)});
		fieldValidators.put(PROVIDER_ACCOUNT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{20,25}$", PROVIDER_ACCOUNT_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_ACCOUNT_FIELD)});
		fieldValidators.put(PROVIDER_BIC_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{9}$", PROVIDER_BIC_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BIC_FIELD)});
		fieldValidators.put(PROVIDER_BANK_NAME_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,320}$", PROVIDER_BANK_NAME_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BANK_NAME_FIELD)});
		fieldValidators.put(PROVIDER_BANK_COR_ACCOUNT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{20}$", PROVIDER_BANK_COR_ACCOUNT_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BANK_COR_ACCOUNT_FIELD)});
		fieldValidators.put(PROVIDER_NOT_VISIBLE_BANK_DETAILS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_NOT_VISIBLE_BANK_DETAILS_FIELD)});
		fieldValidators.put(VISIBLE_PAYMENTS_FOR_INTERNET_BANK, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", VISIBLE_PAYMENTS_FOR_INTERNET_BANK)});
		fieldValidators.put(VISIBLE_PAYMENTS_FOR_MAPI, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", VISIBLE_PAYMENTS_FOR_MAPI)});
		fieldValidators.put(VISIBLE_PAYMENTS_FOR_ATM_API, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", VISIBLE_PAYMENTS_FOR_ATM_API)});
		fieldValidators.put(VISIBLE_PAYMENTS_FOR_SOCIAL_API, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", VISIBLE_PAYMENTS_FOR_SOCIAL_API)});
		fieldValidators.put(AVAILABLE_PAYMENTS_FOR_INTERNET_BANK, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", AVAILABLE_PAYMENTS_FOR_INTERNET_BANK)});
		fieldValidators.put(AVAILABLE_PAYMENTS_FOR_MAPI, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", AVAILABLE_PAYMENTS_FOR_MAPI)});
		fieldValidators.put(AVAILABLE_PAYMENTS_FOR_ATM_API, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", AVAILABLE_PAYMENTS_FOR_ATM_API)});
		fieldValidators.put(AVAILABLE_PAYMENTS_FOR_SOCIAL_API, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", AVAILABLE_PAYMENTS_FOR_SOCIAL_API)});
		fieldValidators.put(AVAILABLE_PAYMENTS_FOR_ERMB, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", AVAILABLE_PAYMENTS_FOR_ERMB)});
		fieldValidators.put(PROVIDER_PHONE_TO_CLIENT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", PROVIDER_PHONE_TO_CLIENT_FIELD)});
		fieldValidators.put(PROVIDER_DESCRIPTION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(.|[\\n\\r\\t\\v]){0,200}$", PROVIDER_DESCRIPTION_FIELD)});
		fieldValidators.put(PROVIDER_IS_FEDERAL_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_FEDERAL_FIELD)});
		fieldValidators.put(PROVIDER_PAYMENT_OFFICE_TB_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,2}$", PROVIDER_PAYMENT_OFFICE_TB_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_PAYMENT_OFFICE_TB_FIELD)});
		fieldValidators.put(PROVIDER_PAYMENT_OFFICE_OSB_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,4}$", PROVIDER_PAYMENT_OFFICE_OSB_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_PAYMENT_OFFICE_OSB_FIELD)});
		fieldValidators.put(PROVIDER_TER_REGION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{2}$", PROVIDER_TER_REGION_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_TER_REGION_FIELD)});
		fieldValidators.put(PROVIDER_CODE1_REGION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{3}$", PROVIDER_CODE1_REGION_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE1_REGION_FIELD)});
		fieldValidators.put(PROVIDER_IS_BANKOMAT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_BANKOMAT_FIELD)});
		fieldValidators.put(PROVIDER_IS_CREDIT_CARD_ACCESSIBLE, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_CREDIT_CARD_ACCESSIBLE)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_LIMIT_FOR_ESB_FIELD)});
		fieldValidators.put(PROVIDER_IS_AUTO_PAY_ACCESSIBLE_FOR_ESB_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_AUTO_PAY_ACCESSIBLE_FOR_ESB_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAY_TYPE_FOR_ESB_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[R,P,A]{1}$", PROVIDER_AUTO_PAY_TYPE_FOR_ESB_FIELD)});
		fieldValidators.put(PROVIDER_LIMIT_FOR_ESB_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_LIMIT_FOR_ESB_FIELD)});
		fieldValidators.put(PROVIDER_CODE_BS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_BS_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_BS_FIELD)});
		fieldValidators.put(ATTRIBUTE_NAME_BS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[a-zA-Z_:]{1}[a-zA-Z0-9_:]{0,39}$", ATTRIBUTE_NAME_BS_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_NAME_BS_FIELD)});
		fieldValidators.put(ATTRIBUTE_NAME_VISIBLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,60}$", ATTRIBUTE_NAME_VISIBLE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_NAME_VISIBLE_FIELD)});
		fieldValidators.put(COMMENT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(.|[\\n\\r\\t\\v]){0,200}$", COMMENT_FIELD)});
		fieldValidators.put(ATTRIBUTE_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", ATTRIBUTE_TYPE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_TYPE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_REQUIRED_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_REQUIRED_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_EDITABLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_EDITABLE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_VISIBLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_VISIBLE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_SUM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_SUM_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_KEY_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_KEY_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_SUM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_SUM_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_FOR_BILL_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_FOR_BILL_FIELD)});
		fieldValidators.put(ATTRIBUTE_INCLUDE_IN_SMS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_INCLUDE_IN_SMS_FIELD)});
		fieldValidators.put(ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD)});
		fieldValidators.put(ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD)});
		fieldValidators.put(ATTRIBUTE_NUMBER_PRECISION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_NUMBER_PRECISION_FIELD)});
		fieldValidators.put(ATTRIBUTE_DEFAULT_VALUE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,1024}$", ATTRIBUTE_DEFAULT_VALUE_FIELD)});
		fieldValidators.put(VALIDATOR_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", TYPE_FIELD), ReplicationHelper.buildRegexpValidator("REGEXP", TYPE_FIELD), ReplicationHelper.buildRequiredValidator(TYPE_FIELD)});
		fieldValidators.put(VALIDATOR_MESSAGE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", VALIDATOR_MESSAGE_FIELD), ReplicationHelper.buildRequiredValidator(VALIDATOR_MESSAGE_FIELD)});
		fieldValidators.put(VALIDATOR_PARAMETER_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", VALIDATOR_PARAMETER_FIELD), ReplicationHelper.buildRequiredValidator(VALIDATOR_PARAMETER_FIELD)});
		fieldValidators.put(ATTRIBUTE_MENU_ITEM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,60}$", ATTRIBUTE_MENU_ITEM_FIELD)});
		fieldValidators.put(ATTRIBUTE_MAX_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_MAX_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_MIN_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_MIN_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_DESCRIPTION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(.|[\\n\\r\\t\\v]){0,200}$", ATTRIBUTE_DESCRIPTION_FIELD)});
		fieldValidators.put(PROVIDER_IS_POPULAR_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_POPULAR_FIELD)});
		fieldValidators.put(PROVIDER_IS_BAR_SUPPORTED, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_BAR_SUPPORTED)});
		fieldValidators.put(ATTRIBUTE_REQUISITE_TYPE, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("(IsListOfCharges)|(IsPeriod)", ATTRIBUTE_REQUISITE_TYPE)});
	}

	protected Map<String, FieldValidator[]> getFieldValidators()
	{
		return Collections.unmodifiableMap(fieldValidators);
	}

	ESBServiceProviderFetcher(List<String> errorsCollector, String dbInstanceName)
	{
		super(errorsCollector, dbInstanceName);
	}

	/**
	 * Собрать поставщика услуг
	 * @param source данные
	 */
	public void collect(Map<String, Object> source)
	{
		List<String> errorsCollector = getErrors();
		BillingServiceProvider provider = getProvider();

		String providerCodeSBOL = (String) source.get(PROVIDER_CODE_RECIPIENT_SBOL_FIELD);
		if (validate(providerCodeSBOL, errorsCollector, fieldValidators.get(PROVIDER_CODE_RECIPIENT_SBOL_FIELD)))
			provider.setCodeRecipientSBOL(providerCodeSBOL);

		String providerCodeBS = (String) source.get(PROVIDER_CODE_RECIPIENT_BS_FIELD);
		if (validate(providerCodeBS, errorsCollector, fieldValidators.get(PROVIDER_CODE_RECIPIENT_BS_FIELD)))
			provider.setCode(providerCodeBS);

		String providerState = (String) source.get(PROVIDER_STATE_FIELD);
		if (validate(providerState, errorsCollector, fieldValidators.get(PROVIDER_STATE_FIELD)))
			provider.setState(ServiceProviderState.fromValue(Integer.parseInt(providerState)));

		String providerCodeService = (String) source.get(PROVIDER_CODE_SERVICE_FIELD);
		if (validate(providerCodeService, errorsCollector, fieldValidators.get(PROVIDER_CODE_SERVICE_FIELD)))
			provider.setCodeService(providerCodeService);

		String providerNameService = (String) source.get(PROVIDER_NAME_SERVICE_FIELD);
		if (validate(providerNameService, errorsCollector, fieldValidators.get(PROVIDER_NAME_SERVICE_FIELD)))
			provider.setNameService(providerNameService);

		List<PaymentService> paymentServices = makeGoroupServices(source);
		if (paymentServices != null)
			getWrapper().getPaymentServices().addAll(paymentServices);

		String providerName = (String) source.get(PROVIDER_NAME_FIELD);
		if (validate(providerName, errorsCollector, fieldValidators.get(PROVIDER_NAME_FIELD)))
			provider.setName(providerName);

		String providerAlias = (String) source.get(PROVIDER_ALIAS_FIELD);
		if (validate(providerAlias, errorsCollector, fieldValidators.get(PROVIDER_ALIAS_FIELD)))
			provider.setAlias(providerAlias);

		String providerLegalName = (String) source.get(PROVIDER_LEGAL_NAME_FIELD);
		if (validate(providerLegalName, errorsCollector, fieldValidators.get(PROVIDER_LEGAL_NAME_FIELD)))
			provider.setLegalName(providerLegalName);

		String providerNameOnBill = (String) source.get(PROVIDER_NAME_ON_BILL_FIELD);
		if (validate(providerNameOnBill, errorsCollector, fieldValidators.get(PROVIDER_NAME_ON_BILL_FIELD)))
			provider.setNameOnBill(providerNameOnBill);

		provider.setBankDetails(true);
		setProviderBankDetails(source, true, true);

		String visiblePaymentsForInternetBank = (String) source.get(VISIBLE_PAYMENTS_FOR_INTERNET_BANK);
		if (validate(visiblePaymentsForInternetBank, errorsCollector, fieldValidators.get(VISIBLE_PAYMENTS_FOR_INTERNET_BANK)))
			provider.setVisiblePaymentsForInternetBank(ReplicationHelper.parseBooleanValue(visiblePaymentsForInternetBank));

		String visiblePaymentsForMApi = (String) source.get(VISIBLE_PAYMENTS_FOR_MAPI);
		if (validate(visiblePaymentsForMApi, errorsCollector, fieldValidators.get(VISIBLE_PAYMENTS_FOR_MAPI)))
			provider.setVisiblePaymentsForMApi(ReplicationHelper.parseBooleanValue(visiblePaymentsForMApi));

		String visiblePaymentsForAtmApi = (String) source.get(VISIBLE_PAYMENTS_FOR_ATM_API);
		if (validate(visiblePaymentsForAtmApi, errorsCollector, fieldValidators.get(VISIBLE_PAYMENTS_FOR_ATM_API)))
			provider.setVisiblePaymentsForAtmApi(ReplicationHelper.parseBooleanValue(visiblePaymentsForAtmApi));

		String visiblePaymentsForSocialApi = (String) source.get(VISIBLE_PAYMENTS_FOR_SOCIAL_API);
		if (validate(visiblePaymentsForSocialApi, errorsCollector, fieldValidators.get(VISIBLE_PAYMENTS_FOR_SOCIAL_API)))
			provider.setVisiblePaymentsForSocialApi(ReplicationHelper.parseBooleanValue(visiblePaymentsForSocialApi));

		String availablePaymentsForInternetBank = (String) source.get(AVAILABLE_PAYMENTS_FOR_INTERNET_BANK);
		if (validate(availablePaymentsForInternetBank, errorsCollector, fieldValidators.get(AVAILABLE_PAYMENTS_FOR_INTERNET_BANK)))
			provider.setAvailablePaymentsForInternetBank(availablePaymentsForInternetBank == null || ReplicationHelper.parseBooleanValue(availablePaymentsForInternetBank));

		String availablePaymentsForMApi = (String) source.get(AVAILABLE_PAYMENTS_FOR_MAPI);
		if (validate(availablePaymentsForMApi, errorsCollector, fieldValidators.get(AVAILABLE_PAYMENTS_FOR_MAPI)))
			provider.setAvailablePaymentsForMApi(availablePaymentsForMApi== null || ReplicationHelper.parseBooleanValue(availablePaymentsForMApi));

		String availablePaymentsForAtmApi = (String) source.get(AVAILABLE_PAYMENTS_FOR_ATM_API);
		if (validate(availablePaymentsForAtmApi, errorsCollector, fieldValidators.get(AVAILABLE_PAYMENTS_FOR_ATM_API)))
			provider.setAvailablePaymentsForAtmApi(availablePaymentsForAtmApi == null || ReplicationHelper.parseBooleanValue(availablePaymentsForAtmApi));

        String availablePaymentsForSocialApi = (String) source.get(AVAILABLE_PAYMENTS_FOR_SOCIAL_API);
		if (validate(availablePaymentsForSocialApi, errorsCollector, fieldValidators.get(AVAILABLE_PAYMENTS_FOR_SOCIAL_API)))
			provider.setAvailablePaymentsForSocialApi(availablePaymentsForSocialApi == null || ReplicationHelper.parseBooleanValue(availablePaymentsForSocialApi));

		String availablePaymentsForErmb = (String) source.get(AVAILABLE_PAYMENTS_FOR_ERMB);
		if (validate(availablePaymentsForErmb, errorsCollector, fieldValidators.get(AVAILABLE_PAYMENTS_FOR_ERMB)))
			provider.setAvailablePaymentsForErmb(availablePaymentsForErmb == null || ReplicationHelper.parseBooleanValue(availablePaymentsForErmb));

		String phoneNumber = (String) source.get(PROVIDER_PHONE_TO_CLIENT_FIELD);
		if (validate(phoneNumber, errorsCollector, fieldValidators.get(PROVIDER_PHONE_TO_CLIENT_FIELD)))
			provider.setPhoneNumber(phoneNumber);

		String providerDescription = (String) source.get(PROVIDER_DESCRIPTION_FIELD);
		if (validate(providerDescription, errorsCollector, fieldValidators.get(PROVIDER_DESCRIPTION_FIELD)))
			provider.setDescription(providerDescription);

		String federal = (String) source.get(PROVIDER_IS_FEDERAL_FIELD);
		if (validate(federal, errorsCollector, fieldValidators.get(PROVIDER_IS_FEDERAL_FIELD)))
			provider.setFederal(ReplicationHelper.parseBooleanValue(federal));

		String popular = (String) source.get(PROVIDER_IS_POPULAR_FIELD);
		if (validate(popular, errorsCollector, fieldValidators.get(PROVIDER_IS_POPULAR_FIELD)))
			provider.setPopular(ReplicationHelper.parseBooleanValue(popular));

		String barSupported = (String) source.get(PROVIDER_IS_BAR_SUPPORTED);
		if (validate(barSupported, errorsCollector, fieldValidators.get(PROVIDER_IS_BAR_SUPPORTED)))
			provider.setBarSupported(ReplicationHelper.parseBooleanValue(barSupported));
		if (barSupported == null)
			provider.setBarSupportedRep(false);

		String creditCardAccessible = (String) source.get(PROVIDER_IS_CREDIT_CARD_ACCESSIBLE);
		if (validate(creditCardAccessible, errorsCollector, fieldValidators.get(PROVIDER_IS_CREDIT_CARD_ACCESSIBLE)))
		{
			//см. CHG061793: Изменить логику обработки тега IsCreditCardAccessible при репликации ПУ
			provider.setCreditCardSupportedTemp(StringHelper.isEmpty(creditCardAccessible) ? null : ReplicationHelper.parseBooleanValue(creditCardAccessible));
		}

		provider.setAccountType(AccountType.CARD);
		provider.setPropsOnline(false);
		provider.setMobilebank(false);
		provider.setTemplateSupported(true);
		provider.setDeptAvailable(false);
		provider.setGround(false);
		provider.setVersionAPI(DEFAULT_VERSION_API);

		//устанавливаем группу риска с признаком «По умолчанию», если она заведена в системе. Если такой группы нет, то у поставщика группа риска не выбрана(останется null).
		provider.setGroupRisk(ReplicationHelper.findDefaultGroupRisk(getDbInstanceName()));

		String providerDepartmentCode = StringHelper.getEmptyIfNull(source.get(PROVIDER_PAYMENT_CODE_OFFICE_FIELD));
		String[] arrayCodeOffice = providerDepartmentCode.split(SPLITER);
		if (arrayCodeOffice.length == 2)
		{
			if (validate(arrayCodeOffice[0], errorsCollector, fieldValidators.get(PROVIDER_PAYMENT_OFFICE_TB_FIELD))
					&& validate(arrayCodeOffice[1], errorsCollector, fieldValidators.get(PROVIDER_PAYMENT_OFFICE_OSB_FIELD)))
			{
				Department department = ReplicationHelper.findDepartment(providerDepartmentCode, getDbInstanceName());
				if (validate(PROVIDER_PAYMENT_OFFICE_TB_FIELD + DELIMITER + PROVIDER_PAYMENT_OFFICE_OSB_FIELD, providerDepartmentCode, department, errorsCollector))
					provider.setDepartmentId(department.getId());
			}
		}
		//случай если не пришло значение OSB
		if (arrayCodeOffice.length == 1)
		{
			if (validate(arrayCodeOffice[0], errorsCollector, fieldValidators.get(PROVIDER_PAYMENT_OFFICE_TB_FIELD)))
			{
				Department department = ReplicationHelper.findDepartment(providerDepartmentCode, getDbInstanceName());
				if (validate(PROVIDER_PAYMENT_OFFICE_TB_FIELD + DELIMITER + PROVIDER_PAYMENT_OFFICE_OSB_FIELD, providerDepartmentCode, department, errorsCollector))
					provider.setDepartmentId(department.getId());
			}
		}

		List<String> regionsMap = (List<String>) source.get(PROVIDER_CODE_REGION_FIELD);
		//контроль за наличием регионов осуществляем только для НЕфедеральных поставщиков
		if (regionsMap != null && !regionsMap.isEmpty())
		{
			Set<Region> providerRegions = new HashSet<Region>();
			for (String providerRegionCode : regionsMap)
			{
				String[] arrayRegionCode = providerRegionCode.split(SPLITER, 2);
				if (validate(arrayRegionCode[0], errorsCollector, fieldValidators.get(PROVIDER_TER_REGION_FIELD))
						& validate(arrayRegionCode[1], errorsCollector, fieldValidators.get(PROVIDER_CODE1_REGION_FIELD)))
				{
					Region region = ReplicationHelper.findRegion(arrayRegionCode[0] + arrayRegionCode[1], getDbInstanceName());
					if (validate(PROVIDER_TER_REGION_FIELD + DELIMITER + PROVIDER_CODE1_REGION_FIELD, arrayRegionCode[0] + arrayRegionCode[1], region, errorsCollector))
						providerRegions.add(region);
				}
			}
			provider.setRegions(providerRegions);
		}
		else if (!provider.isFederal())
		{
			errorsCollector.add("У поставщика услуг отсутствует привязка к регионам.");
			setReplicated(false);
		}

		setAutoPaymentFields(source);

		String codeBilling = (String) source.get(PROVIDER_CODE_BS_FIELD);
		if (validate(codeBilling, errorsCollector, fieldValidators.get(PROVIDER_CODE_BS_FIELD)))
		{
			Billing billing = ReplicationHelper.findBilling(codeBilling, getDbInstanceName());
			if (validate(PROVIDER_CODE_BS_FIELD, codeBilling, billing, errorsCollector))
				provider.setBilling(billing);
		}

		if (provider.getBilling() != null && !StringHelper.isEmpty(providerCodeService)
				&& !StringHelper.isEmpty(providerCodeBS))
		{
			String synchKey = new StringBuffer().append(providerCodeService).append(ADDITIONAL_DELIMITER)
					.append(providerCodeBS).append(DELIMITER).append(provider.getBilling().restoreRouteInfo()).toString();
			provider.setSynchKey(synchKey);
		}
		else
		{
			errorsCollector.add(String.format("Не задано значение для поля %s.", PROVIDER_SYNCH_KEY_FIELD));
			setReplicated(false);
		}

		provider.setCreationDate(Calendar.getInstance());
		provider.setSortPriority(0L);

		//заполняем дополнительные поля поставщика услуг
		setProviderAttributesFields(source);
	}

	/**
	 * Добавить поставщику данные о автоплатеже согласно данным репликации.
	 * @param source - данные репликации
	 */
	protected void setAutoPaymentFields(Map<String, Object> source)
	{
		BillingServiceProvider provider = getProvider();
		List<String> errorsCollector = getErrors();
		boolean autoPayment = false;
		String autoPaymentMap = (String) source.get(PROVIDER_IS_AUTO_PAY_ACCESSIBLE_FOR_ESB_FIELD);
		if (validate(autoPaymentMap, errorsCollector, fieldValidators.get(PROVIDER_IS_AUTO_PAY_ACCESSIBLE_FOR_ESB_FIELD)))
		{
			autoPayment = ReplicationHelper.parseBooleanValue(autoPaymentMap);
			provider.setAutoPaymentSupported(autoPayment);
		}

		if (autoPayment)
		{
			provider.setAutoPaymentSupportedInApi(true);
			provider.setAutoPaymentSupportedInATM(true);
			provider.setAutoPaymentSupportedInSocialApi(true);
			provider.setAutoPaymentSupportedInERMB(true);
			provider.setAutoPaymentVisible(true);
			provider.setNeedAutoPaymentVisibleApply(false); //в загружаемом файле нет данных.
			String isBankomatMap = (String) source.get(PROVIDER_IS_BANKOMAT_FIELD);
			if (validate(isBankomatMap, errorsCollector, fieldValidators.get(PROVIDER_IS_BANKOMAT_FIELD)))
			{
				boolean isBankomat = ReplicationHelper.parseBooleanValue(isBankomatMap);
				provider.setBankomatSupported(isBankomat);
			}

			List<String> autoPayTypes = (List<String>) source.get(PROVIDER_AUTO_PAY_TYPE_FOR_ESB_FIELD);

			boolean alwaysAutoPay = false;
			boolean invoiceAutoPay = false;
			boolean thresholdAutoPay = false;
			for (String type : autoPayTypes)
			{
				if (validate(type, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAY_TYPE_FOR_ESB_FIELD)))
				{
					if (type.equals("R"))
						alwaysAutoPay = true;     //регулярный
			        else if (type.equals("P"))
						thresholdAutoPay = true;  //пороговый
			        else if (type.equals("A"))
						invoiceAutoPay = true;    //по счету
				}
			}

			if (alwaysAutoPay)
			{
				AlwaysAutoPayScheme alwaysAutoPayScheme = new AlwaysAutoPayScheme();
				provider.setAlwaysAutoPayScheme(alwaysAutoPayScheme);
			}
			if (thresholdAutoPay)
			{
				ThresholdAutoPayScheme thresholdAutoPayScheme = new ThresholdAutoPayScheme();
				String limitValues = (String) source.get(PROVIDER_LIMIT_FOR_ESB_FIELD);

				if (!StringUtils.isEmpty(limitValues))
				{
					if (validate(limitValues, errorsCollector, fieldValidators.get(PROVIDER_LIMIT_FOR_ESB_FIELD)))
					{
						String[] arrayLimitValues;
						if (limitValues.contains("-"))
						{
							arrayLimitValues = limitValues.split("\\-");
							if (arrayLimitValues.length == 2)
							{
								if (validate(arrayLimitValues[0], errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD)))
									thresholdAutoPayScheme.setMinValueThreshold(ReplicationHelper.parseBigDecimalValue(arrayLimitValues[0]));
								if (validate(arrayLimitValues[1], errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD)))
									thresholdAutoPayScheme.setMaxValueThreshold(ReplicationHelper.parseBigDecimalValue(arrayLimitValues[1]));
								thresholdAutoPayScheme.setInterval(true);
							}
							else
								errorsCollector.add("В случае интервала для пороговой схемы автоплатежа значение должно быть указано через дефис, должно быть только два числа.");
						}
						else
						{
							arrayLimitValues = limitValues.split(";");

							thresholdAutoPayScheme.setInterval(false);
							boolean valid = true;
							for (String item : arrayLimitValues)
								if (!validate(item, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD)))
									valid = false;

                            if (valid)
								thresholdAutoPayScheme.setDiscreteValues(StringUtils.join(arrayLimitValues, '|'));
						}
					}
				}
				provider.setThresholdAutoPayScheme(thresholdAutoPayScheme);
			}

			if (invoiceAutoPay)
			{
				InvoiceAutoPayScheme invoiceAutoPayScheme = new InvoiceAutoPayScheme();
				provider.setInvoiceAutoPayScheme(invoiceAutoPayScheme);
			}

			if (!alwaysAutoPay && !thresholdAutoPay && !invoiceAutoPay)
			{
				setReplicated(false);
				errorsCollector.add("Необходимо заполнить одну из схем для поддержки автоплатежей: пороговую либо регулярную либо по выставленному счету.");
			}
		}
	}
	protected void setFieldAdditionalAttributes(Map<String, Object> mapAttribute, FieldDescription field)
	{
		List<String> errorsCollector = getErrors();
		String numberPercision = (String) mapAttribute.get(ATTRIBUTE_NUMBER_PRECISION_FIELD);
		if (validate(numberPercision, errorsCollector, fieldValidators.get(ATTRIBUTE_NUMBER_PRECISION_FIELD)))
		{
			if (FieldDataType.number == field.getType())
			{
				numberPercision = StringHelper.isEmpty(numberPercision) ? "0" : numberPercision;
				field.setNumberPrecision(Integer.parseInt(numberPercision));
			}
		}
		String requiredForBill = (String) mapAttribute.get(ATTRIBUTE_IS_FOR_BILL_FIELD);
		if (validate(requiredForBill, errorsCollector, fieldValidators.get(ATTRIBUTE_IS_FOR_BILL_FIELD)))
			field.setRequiredForBill(ReplicationHelper.parseBooleanValue(requiredForBill));

		String requiredForConfirmation = (String) mapAttribute.get(ATTRIBUTE_INCLUDE_IN_SMS_FIELD);
		if (validate(requiredForConfirmation, errorsCollector, fieldValidators.get(ATTRIBUTE_INCLUDE_IN_SMS_FIELD)))
			field.setRequiredForConformation(ReplicationHelper.parseBooleanValue(requiredForConfirmation));

		String saveInTemplate = (String) mapAttribute.get(ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD);
		if (validate(saveInTemplate, errorsCollector, fieldValidators.get(ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD)))
			field.setSaveInTemplate(ReplicationHelper.parseBooleanValue(saveInTemplate));

		String hideInConfirmation = (String) mapAttribute.get(ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD);
		if (validate(hideInConfirmation, errorsCollector, fieldValidators.get(ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD)))
			field.setHideInConfirmation(ReplicationHelper.parseBooleanValue(hideInConfirmation));

		List<Object> requisiteTypeObjects = (List <Object>) mapAttribute.get(ATTRIBUTE_REQUISITE_TYPES);
		if (CollectionUtils.isNotEmpty(requisiteTypeObjects))
		{
			boolean isRequisiteTypesValid = true;
			List<RequisiteType> requisiteTypes = new ArrayList<RequisiteType>(requisiteTypeObjects.size());
			for (Object object: requisiteTypeObjects)
			{
				if (validate((String) object, errorsCollector, fieldValidators.get(ATTRIBUTE_REQUISITE_TYPE)))
				{
					requisiteTypes.add(RequisiteType.fromValue((String) object));
				}
				else
				{
					isRequisiteTypesValid = false;
					break;
				}
			}
			if (isRequisiteTypesValid)
				field.setRequisiteTypes(requisiteTypes);
		}
		
		if (FieldDataType.list == field.getType() || FieldDataType.set == field.getType())
		{
			String menu = StringHelper.getEmptyIfNull(mapAttribute.get(ATTRIBUTE_MENU_FIELD));
			String[] arrayMenu = menu.split(SPLITER);
			for (String item : arrayMenu)
				validate(item, errorsCollector, fieldValidators.get(ATTRIBUTE_MENU_ITEM_FIELD));
			field.setListValues(Arrays.asList(arrayMenu));
		}
	}
}
