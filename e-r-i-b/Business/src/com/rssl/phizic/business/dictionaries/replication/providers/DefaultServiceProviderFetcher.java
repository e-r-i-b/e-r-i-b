package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author khudyakov
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class DefaultServiceProviderFetcher extends ServiceProviderFetcherBase implements ServiceProviderFetcher
{
	private static final Map<String, FieldValidator[]> fieldValidators = new HashMap<String, FieldValidator[]>();

	static
	{
		fieldValidators.put(PROVIDER_STATE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0-4]{1}$", PROVIDER_STATE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_STATE_FIELD)});
		fieldValidators.put(PROVIDER_CODE_SERVICE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_SERVICE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_SERVICE_FIELD)});
		fieldValidators.put(PROVIDER_NAME_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_NAME_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_NAME_FIELD)});
		fieldValidators.put(PROVIDER_CODE_RECIPIENT_SBOL_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", PROVIDER_CODE_RECIPIENT_SBOL_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_RECIPIENT_SBOL_FIELD)});
		fieldValidators.put(PROVIDER_CODE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_FIELD)});
		fieldValidators.put(PROVIDER_GRP_SERVICE_CODE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,14}$", PROVIDER_GRP_SERVICE_CODE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_GRP_SERVICE_CODE_FIELD)});
		fieldValidators.put(PROVIDER_NAME_SERVICE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,150}$", PROVIDER_NAME_SERVICE_FIELD)});
		fieldValidators.put(PROVIDER_ALIAS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_ALIAS_FIELD)});
		fieldValidators.put(PROVIDER_LEGAL_NAME_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_LEGAL_NAME_FIELD)});
		fieldValidators.put(PROVIDER_NAME_ON_BILL_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,250}$", PROVIDER_NAME_ON_BILL_FIELD)});
		fieldValidators.put(PROVIDER_IS_PROPS_ONLINE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_PROPS_ONLINE_FIELD)});
		fieldValidators.put(PROVIDER_IS_BANK_DETAILS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_BANK_DETAILS_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_BANK_DETAILS_FIELD)});
		fieldValidators.put(PROVIDER_INN_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{10}|\\d{12}$", PROVIDER_INN_FIELD)});
		fieldValidators.put(PROVIDER_KPP_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{9}$", PROVIDER_KPP_FIELD)});
		fieldValidators.put(PROVIDER_ACCOUNT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{20,25}$", PROVIDER_ACCOUNT_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_ACCOUNT_FIELD)});
		fieldValidators.put(PROVIDER_BIC_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{9}$", PROVIDER_BIC_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BIC_FIELD)});
		fieldValidators.put(PROVIDER_BANK_NAME_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,320}$", PROVIDER_BANK_NAME_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BANK_NAME_FIELD)});
		fieldValidators.put(PROVIDER_BANK_COR_ACCOUNT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{20}$", PROVIDER_BANK_COR_ACCOUNT_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_BANK_COR_ACCOUNT_FIELD)});
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
		fieldValidators.put(PROVIDER_CODE_REGION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", PROVIDER_CODE_REGION_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_REGION_FIELD)});
		fieldValidators.put(PROVIDER_IS_FEDERAL_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_FEDERAL_FIELD)});
		fieldValidators.put(PROVIDER_IS_POPULAR_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_POPULAR_FIELD)});
		fieldValidators.put(PROVIDER_IS_DEBT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_DEBT_FIELD)});
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
		fieldValidators.put(PROVIDER_IS_OFFLINE_AVAILABLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_OFFLINE_AVAILABLE_FIELD)});
		fieldValidators.put(PROVIDER_VERSION_API_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{3}$", PROVIDER_VERSION_API_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_VERSION_API_FIELD)});
		fieldValidators.put(PROVIDER_IS_TEMPLATE_SUPPORTED, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_TEMPLATE_SUPPORTED), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_TEMPLATE_SUPPORTED)});
		fieldValidators.put(PROVIDER_IS_EDIT_PAYMENT_SUPPORTED, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_EDIT_PAYMENT_SUPPORTED), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_EDIT_PAYMENT_SUPPORTED)});
		fieldValidators.put(PROVIDER_IS_CREDIT_CARD_ACCESSIBLE, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_CREDIT_CARD_ACCESSIBLE)});
		fieldValidators.put(PROVIDER_IS_BAR_SUPPORTED, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_BAR_SUPPORTED), ReplicationHelper.buildRequiredValidator(PROVIDER_IS_BAR_SUPPORTED)});
		fieldValidators.put(PROVIDER_PAYMENT_ACCOUNT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,25}$", PROVIDER_PAYMENT_ACCOUNT_FIELD)});
		fieldValidators.put(PROVIDER_CODE_OFFICE_NSI_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{1,9}$", PROVIDER_CODE_OFFICE_NSI_FIELD)});
		fieldValidators.put(PROVIDER_ACCOUNT_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0-2]{1}$", PROVIDER_ACCOUNT_TYPE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_ACCOUNT_TYPE_FIELD)});
		fieldValidators.put(PROVIDER_PLANING_FOR_DEACTIVATE, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_PLANING_FOR_DEACTIVATE)});
		fieldValidators.put(PROVIDER_IS_AUTO_PAYMENT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_AUTO_PAYMENT_FIELD)});
		fieldValidators.put(PROVIDER_IS_AUTO_PAYMENT_IN_API, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_AUTO_PAYMENT_IN_API)});
		fieldValidators.put(PROVIDER_IS_AUTO_PAYMENT_IN_ATM, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_AUTO_PAYMENT_IN_ATM)});
		fieldValidators.put(PROVIDER_IS_AUTO_PAYMENT_IN_SCOIAL_API, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_AUTO_PAYMENT_IN_SCOIAL_API)});
		fieldValidators.put(PROVIDER_IS_AUTO_PAYMENT_IN_ERMB, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_AUTO_PAYMENT_IN_ERMB)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_VISIBLE, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_AUTO_PAYMENT_VISIBLE)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_API, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_AUTO_PAYMENT_VISIBLE_IN_API)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ATM, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ATM)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_SCOIAL_API, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_AUTO_PAYMENT_VISIBLE_IN_SCOIAL_API)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ERMB, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ERMB)});
		fieldValidators.put(PROVIDER_IS_BANKOMAT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_IS_BANKOMAT_FIELD)});
		fieldValidators.put(PROVIDER_GENERAL_GROUP_RISK_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{1,100}$", PROVIDER_GENERAL_GROUP_RISK_FIELD)});
		fieldValidators.put(PROVIDER_MOBILE_GROUP_RISK_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{1,6}$", PROVIDER_MOBILE_GROUP_RISK_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MIN_SUM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MIN_SUM_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MAX_SUM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MAX_SUM_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_CLIENT_HINT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_CLIENT_HINT_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_SUM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_SUM_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_SUM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_SUM_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_VALUE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_VALUE_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_VALUE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_VALUE_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_CLIENT_HINT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_CLIENT_HINT_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_IS_ACCESS_TOTAL_AMOUNT_LIMIT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_IS_ACCESS_TOTAL_AMOUNT_LIMIT_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_LIMIT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_LIMIT_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_PERIOD_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(IN_DAY)|(IN_WEEK)|(IN_TENDAY)|(IN_MONTH)|(IN_QUARTER)|(IN_YEAR)$", PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_PERIOD_FIELD)});
		fieldValidators.put(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_CLIENT_HINT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_CLIENT_HINT_FIELD)});
		fieldValidators.put(ATTRIBUTE_NAME_BS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[a-zA-Z_:]{1}[a-zA-Z0-9_:]{0,39}$", ATTRIBUTE_NAME_BS_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_NAME_BS_FIELD)});
		fieldValidators.put(ATTRIBUTE_NAME_VISIBLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,60}$", ATTRIBUTE_NAME_VISIBLE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_NAME_VISIBLE_FIELD)});
		fieldValidators.put(COMMENT_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(.|[\\n\\r\\t\\v]){0,200}$", COMMENT_FIELD)});
		fieldValidators.put(ATTRIBUTE_DESCRIPTION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(.|[\\n\\r\\t\\v]){0,200}$", ATTRIBUTE_DESCRIPTION_FIELD)});
		fieldValidators.put(ATTRIBUTE_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", ATTRIBUTE_TYPE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_TYPE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_REQUIRED_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_REQUIRED_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_REQUIRED_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_EDITABLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_EDITABLE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_EDITABLE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_VISIBLE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_VISIBLE_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_VISIBLE_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_SUM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_SUM_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_SUM_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_KEY_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_KEY_FIELD), ReplicationHelper.buildRequiredValidator(ATTRIBUTE_IS_KEY_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_FOR_BILL_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_FOR_BILL_FIELD)});
		fieldValidators.put(ATTRIBUTE_INCLUDE_IN_SMS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_INCLUDE_IN_SMS_FIELD)});
		fieldValidators.put(ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD)});
		fieldValidators.put(ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD)});
		fieldValidators.put(ATTRIBUTE_IS_KEY_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^[0,1]{1}$", ATTRIBUTE_IS_KEY_FIELD)});
		fieldValidators.put(ATTRIBUTE_NUMBER_PRECISION_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_NUMBER_PRECISION_FIELD)});
		fieldValidators.put(ATTRIBUTE_DEFAULT_VALUE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,1024}$", ATTRIBUTE_DEFAULT_VALUE_FIELD)});
		fieldValidators.put(ATTRIBUTE_VALUES_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", ATTRIBUTE_VALUES_FIELD)});
		fieldValidators.put(VALIDATOR_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", TYPE_FIELD), ReplicationHelper.buildRegexpValidator("REGEXP", TYPE_FIELD), ReplicationHelper.buildRequiredValidator(TYPE_FIELD)});
		fieldValidators.put(VALIDATOR_MESSAGE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", VALIDATOR_MESSAGE_FIELD), ReplicationHelper.buildRequiredValidator(VALIDATOR_MESSAGE_FIELD)});
		fieldValidators.put(VALIDATOR_PARAMETER_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,500}$", VALIDATOR_PARAMETER_FIELD), ReplicationHelper.buildRequiredValidator(VALIDATOR_PARAMETER_FIELD)});
		fieldValidators.put(ATTRIBUTE_MENU_ITEM_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,60}$", ATTRIBUTE_MENU_ITEM_FIELD)});
		fieldValidators.put(ATTRIBUTE_MAX_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_MAX_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_MIN_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_MIN_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_LENGTH_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^\\d+$", ATTRIBUTE_LENGTH_FIELD)});
		fieldValidators.put(ATTRIBUTE_REQUISITE_TYPE, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("(IsListOfCharges)|(IsPeriod)", ATTRIBUTE_REQUISITE_TYPE)});
		fieldValidators.put(ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(phone)|(wallet)$", ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD)});
		fieldValidators.put(SUB_TYPE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^(mobile)|(wallet)$", SUB_TYPE_FIELD)});

	}

	protected Map<String, FieldValidator[]> getFieldValidators()
	{
		return Collections.unmodifiableMap(fieldValidators);
	}

	DefaultServiceProviderFetcher(List<String> errorsCollector, String dbInstanceName)
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

		String providerCode = (String) source.get(PROVIDER_CODE_FIELD);
		if (validate(providerCode, errorsCollector, fieldValidators.get(PROVIDER_CODE_FIELD)))
			provider.setCode(providerCode);

		String providerCodeSBOL = (String) source.get(PROVIDER_CODE_RECIPIENT_SBOL_FIELD);
		if (validate(providerCodeSBOL, errorsCollector, fieldValidators.get(PROVIDER_CODE_RECIPIENT_SBOL_FIELD)))
			provider.setCodeRecipientSBOL(providerCodeSBOL);

		String providerState = (String) source.get(PROVIDER_STATE_FIELD);
		if (validate(providerState, errorsCollector, fieldValidators.get(PROVIDER_STATE_FIELD)))
			provider.setState(ServiceProviderState.fromValue(Integer.parseInt(providerState)));

		List<PaymentService> paymentServices = makeGoroupServices(source);
		if (paymentServices != null)
			getWrapper().getPaymentServices().addAll(paymentServices);

		String providerCodeService = (String) source.get(PROVIDER_CODE_SERVICE_FIELD);
		if (validate(providerCodeService, errorsCollector, fieldValidators.get(PROVIDER_CODE_SERVICE_FIELD)))
			provider.setCodeService(providerCodeService);

		String providerNameService = (String) source.get(PROVIDER_NAME_SERVICE_FIELD);
		if (validate(providerNameService, errorsCollector, fieldValidators.get(PROVIDER_NAME_SERVICE_FIELD)))
			provider.setNameService(providerNameService);

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

		setProviderBankDetails(source, isBankDetails, true);

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
			provider.setAvailablePaymentsForErmb(availablePaymentsForErmb == null || ReplicationHelper.parseBooleanValue(availablePaymentsForErmb) );

		String phoneNumber = (String) source.get(PROVIDER_PHONE_TO_CLIENT_FIELD);
		if (validate(phoneNumber, errorsCollector, fieldValidators.get(PROVIDER_PHONE_TO_CLIENT_FIELD)))
			provider.setPhoneNumber(phoneNumber);

		String subType = (String) source.get(SUB_TYPE_FIELD);
		if (validate(subType, errorsCollector, fieldValidators.get(SUB_TYPE_FIELD)))
			provider.setSubType(StringHelper.isNotEmpty(subType) ? ServiceProviderSubType.valueOf(subType) : null);

		String providerDescription = (String) source.get(PROVIDER_DESCRIPTION_FIELD);
		if (validate(providerDescription, errorsCollector, fieldValidators.get(PROVIDER_DESCRIPTION_FIELD)))
			provider.setDescription(providerDescription);
                                                                              		
		String federal = (String) source.get(PROVIDER_IS_FEDERAL_FIELD);
		if (validate(federal, errorsCollector, fieldValidators.get(PROVIDER_IS_FEDERAL_FIELD)))
			provider.setFederal(ReplicationHelper.parseBooleanValue(federal));

		provider.setFederal(ReplicationHelper.parseBooleanValue(federal));

		String planForDeactivate = (String) source.get(PROVIDER_PLANING_FOR_DEACTIVATE);
		if (validate(planForDeactivate, errorsCollector, fieldValidators.get(PROVIDER_PLANING_FOR_DEACTIVATE)))
			provider.setPlaningForDeactivate(ReplicationHelper.parseBooleanValue(planForDeactivate));

		List<String> regionsMap = (List<String>) source.get(PROVIDER_CODE_REGION_FIELD);
		//контроль за наличием регионов осуществляем только для НЕфедеральных поставщиков
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
		else if (!provider.isFederal())
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
			provider.setTransitAccount((String) source.get(PROVIDER_PAYMENT_ACCOUNT_FIELD));

		String providerNSICode = (String) source.get(PROVIDER_CODE_OFFICE_NSI_FIELD);
		if (validate(providerNSICode, errorsCollector, fieldValidators.get(PROVIDER_CODE_OFFICE_NSI_FIELD)))
			provider.setNSICode(StringHelper.getEmptyIfNull(providerNSICode));


		String providerAccountType = (String) source.get(PROVIDER_ACCOUNT_TYPE_FIELD);
		if (validate(providerAccountType, errorsCollector, fieldValidators.get(PROVIDER_ACCOUNT_TYPE_FIELD)))
			provider.setAccountType(AccountType.fromValue(Integer.parseInt(providerAccountType)));

		String popular = (String) source.get(PROVIDER_IS_POPULAR_FIELD);
		if (validate(popular, errorsCollector, fieldValidators.get(PROVIDER_IS_POPULAR_FIELD)))
			provider.setPopular(ReplicationHelper.parseBooleanValue(popular));

		String debt = (String) source.get(PROVIDER_IS_DEBT_FIELD);
		if (validate(debt, errorsCollector, fieldValidators.get(PROVIDER_IS_DEBT_FIELD)))
			provider.setDeptAvailable(ReplicationHelper.parseBooleanValue(debt));

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

		String versionAPIString = (String)source.get(PROVIDER_VERSION_API_FIELD);
		if(provider.isAvailablePaymentsForMApi() && validate(versionAPIString, errorsCollector, fieldValidators.get(PROVIDER_VERSION_API_FIELD)))
		{
			Integer versionAPI = Integer.parseInt(versionAPIString);
			provider.setVersionAPI(versionAPI);
		}

		String isOfflineAvailable = (String) source.get(PROVIDER_IS_OFFLINE_AVAILABLE_FIELD);
		if (validate(isOfflineAvailable, errorsCollector, fieldValidators.get(PROVIDER_IS_OFFLINE_AVAILABLE_FIELD)))
		{
			provider.setOfflineAvailable(ReplicationHelper.parseBooleanValue(isOfflineAvailable));
		}

		String isTemplateSupported = (String) source.get(PROVIDER_IS_TEMPLATE_SUPPORTED);
		if(validate(isTemplateSupported,errorsCollector,fieldValidators.get(PROVIDER_IS_TEMPLATE_SUPPORTED)))
		{
			provider.setTemplateSupported(ReplicationHelper.parseBooleanValue(isTemplateSupported));
		}
		else
		{
			provider.setTemplateSupported(true);
		}

		String isBarSupported = (String) source.get(PROVIDER_IS_BAR_SUPPORTED);
		if(validate(isBarSupported,errorsCollector,fieldValidators.get(PROVIDER_IS_BAR_SUPPORTED)))
		{
			provider.setBarSupported(ReplicationHelper.parseBooleanValue(isBarSupported));
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

		String isEditPaymentSupported = (String) source.get(PROVIDER_IS_EDIT_PAYMENT_SUPPORTED);
		if(validate(isEditPaymentSupported, errorsCollector, fieldValidators.get(PROVIDER_IS_EDIT_PAYMENT_SUPPORTED)))
		{
			provider.setEditPaymentSupported(ReplicationHelper.parseBooleanValue(isEditPaymentSupported));
		}
		else
		{
			provider.setTemplateSupported(true);
		}

		String creditCardAccessible = (String) source.get(PROVIDER_IS_CREDIT_CARD_ACCESSIBLE);
		if (validate(creditCardAccessible, errorsCollector, fieldValidators.get(PROVIDER_IS_CREDIT_CARD_ACCESSIBLE)))
		{
			provider.setCreditCardSupported(ReplicationHelper.parseBooleanValue(creditCardAccessible));
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

		provider.setCreationDate(Calendar.getInstance());

		setAutoPaymentFields(source);

		String groupRiskName = (String) source.get(PROVIDER_GENERAL_GROUP_RISK_FIELD);
		if (StringHelper.isNotEmpty(groupRiskName))
		{
			GroupRisk groupRisk = ReplicationHelper.findGroupRisk(groupRiskName, getDbInstanceName());
			if (validate(groupRiskName, errorsCollector, fieldValidators.get(PROVIDER_GENERAL_GROUP_RISK_FIELD)) && groupRisk != null)
			{
				provider.setGroupRisk(groupRisk);
			}
			else
			{
				//устанавливаем группу риска с признаком «По умолчанию», если она заведена в системе. Если такой группы нет, то у поставщика группа риска не выбрана(останется null).
				provider.setGroupRisk(ReplicationHelper.findDefaultGroupRisk(getDbInstanceName()));
			}
		}
		else
		{
			//устанавливаем группу риска с признаком «По умолчанию», если она заведена в системе. Если такой группы нет, то у поставщика группа риска не выбрана(останется null).
			provider.setGroupRisk(ReplicationHelper.findDefaultGroupRisk(getDbInstanceName()));
		}

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
		String autoPaymentMap = (String) source.get(PROVIDER_IS_AUTO_PAYMENT_FIELD);
		if (validate(autoPaymentMap, errorsCollector, fieldValidators.get(PROVIDER_IS_AUTO_PAYMENT_FIELD)))
		{
			autoPayment = ReplicationHelper.parseBooleanValue(autoPaymentMap);
			provider.setAutoPaymentSupported(autoPayment);
		}

		boolean autoPaymentInATM = false;
		String autoPaymentInATMField = (String) source.get(PROVIDER_IS_AUTO_PAYMENT_IN_ATM);
		if (validate(autoPaymentInATMField, errorsCollector, fieldValidators.get(PROVIDER_IS_AUTO_PAYMENT_IN_ATM)))
		{
			if (autoPayment && autoPaymentInATMField == null)
				autoPaymentInATM = true;
			else
				autoPaymentInATM = ReplicationHelper.parseBooleanValue(autoPaymentInATMField);
			provider.setAutoPaymentSupportedInATM(autoPaymentInATM);
		}

        boolean autoPaymentInSocialApi = false;
        String autoPaymentInSocialApiField = (String) source.get(PROVIDER_IS_AUTO_PAYMENT_IN_SCOIAL_API);
        if (validate(autoPaymentInSocialApiField, errorsCollector, fieldValidators.get(PROVIDER_IS_AUTO_PAYMENT_IN_SCOIAL_API)))
        {
            if (autoPayment && autoPaymentInSocialApiField == null)
                autoPaymentInSocialApi = true;
            else
                autoPaymentInSocialApi = ReplicationHelper.parseBooleanValue(autoPaymentInSocialApiField);
            provider.setAutoPaymentSupportedInSocialApi(autoPaymentInSocialApi);
        }

		boolean autoPaymentInApi = false;
		String autoPaymentInApiField = (String) source.get(PROVIDER_IS_AUTO_PAYMENT_IN_API);
		if (validate(autoPaymentInApiField, errorsCollector, fieldValidators.get(PROVIDER_IS_AUTO_PAYMENT_IN_API)))
		{
			if (autoPayment && autoPaymentInApiField == null)
				autoPaymentInApi = true;
			else
				autoPaymentInApi = ReplicationHelper.parseBooleanValue(autoPaymentInApiField);
			provider.setAutoPaymentSupportedInApi(autoPaymentInApi);
		}
		boolean autoPaymentInERMB = false;
		String autoPaymentInERMBField = (String) source.get(PROVIDER_IS_AUTO_PAYMENT_IN_ERMB);
		if (validate(autoPaymentInERMBField, errorsCollector, fieldValidators.get(PROVIDER_IS_AUTO_PAYMENT_IN_ERMB)))
		{
			if (autoPayment && autoPaymentInERMBField == null)
				autoPaymentInERMB = true;
			else
				autoPaymentInERMB = ReplicationHelper.parseBooleanValue(autoPaymentInERMBField);
			provider.setAutoPaymentSupportedInERMB(autoPaymentInERMB);
		}

		String visibleAutoPayments = (String) source.get(PROVIDER_AUTO_PAYMENT_VISIBLE);
		if (validate(visibleAutoPayments, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_VISIBLE)))
		{
			if (autoPayment && visibleAutoPayments == null)
				provider.setAutoPaymentVisible(true);
			else
				provider.setAutoPaymentVisible(ReplicationHelper.parseBooleanValue(visibleAutoPayments));
		}
		String visibleAutoPaymentsInApi = (String) source.get(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_API);
		if (validate(visibleAutoPaymentsInApi, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_API)))
			provider.setAutoPaymentVisibleInApi(ReplicationHelper.parseBooleanValue(visibleAutoPaymentsInApi));
		String visibleAutoPaymentsInATM = (String) source.get(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ATM);
		if (validate(visibleAutoPaymentsInATM, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ATM)))
			provider.setAutoPaymentVisibleInATM(ReplicationHelper.parseBooleanValue(visibleAutoPaymentsInATM));
		String visibleAutoPaymentsInSocialApi = (String) source.get(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_SCOIAL_API);
		if (validate(visibleAutoPaymentsInSocialApi, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_SCOIAL_API)))
			provider.setAutoPaymentVisibleInSocialApi(ReplicationHelper.parseBooleanValue(visibleAutoPaymentsInSocialApi));
		String visibleAutoPaymentsInERMB = (String) source.get(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ERMB);
		if (validate(visibleAutoPaymentsInERMB, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_VISIBLE_IN_ERMB)))
			provider.setAutoPaymentVisibleInERMB(ReplicationHelper.parseBooleanValue(visibleAutoPaymentsInERMB));

		if (autoPayment || autoPaymentInApi || autoPaymentInATM || autoPaymentInSocialApi || autoPaymentInERMB)
		{
			String isBankomatMap = (String) source.get(PROVIDER_IS_BANKOMAT_FIELD);
			if (validate(isBankomatMap, errorsCollector, fieldValidators.get(PROVIDER_IS_BANKOMAT_FIELD)))
			{
				boolean isBankomat = ReplicationHelper.parseBooleanValue(isBankomatMap);
				provider.setBankomatSupported(isBankomat);
			}

			Boolean alwaysAutoPay = ReplicationHelper.parseBooleanValue((String) source.get(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_FIELD));
			if (alwaysAutoPay != null && alwaysAutoPay)
			{
				AlwaysAutoPayScheme alwaysAutoPayScheme = new AlwaysAutoPayScheme();

				String minPaySum = (String) source.get(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_PREFIX + PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MIN_SUM_FIELD);
				if (validate(minPaySum, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MIN_SUM_FIELD)))
					alwaysAutoPayScheme.setMinSumAlways(ReplicationHelper.parseBigDecimalValue(minPaySum));

				String maxPaySum = (String) source.get(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_PREFIX + PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MAX_SUM_FIELD);
				if (validate(maxPaySum, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_MAX_SUM_FIELD)))
					alwaysAutoPayScheme.setMaxSumAlways(ReplicationHelper.parseBigDecimalValue(maxPaySum));

				String clientHint = (String) source.get(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_PREFIX + PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_CLIENT_HINT_FIELD);
				if (validate(clientHint, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_ALWAYS_SCHEM_CLIENT_HINT_FIELD)))
					alwaysAutoPayScheme.setClientHint(clientHint);

				provider.setAlwaysAutoPayScheme(alwaysAutoPayScheme);
			}

			Boolean thresholdAutoPay = ReplicationHelper.parseBooleanValue((String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_FIELD));
			if (thresholdAutoPay != null && thresholdAutoPay)
			{
				ThresholdAutoPayScheme thresholdAutoPayScheme = new ThresholdAutoPayScheme();

				String minPaySum = (String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_PREFIX + PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_SUM_FIELD);
				if (validate(minPaySum, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_SUM_FIELD)))
					thresholdAutoPayScheme.setMinSumThreshold(ReplicationHelper.parseBigDecimalValue(minPaySum));

				String maxPaySum = (String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_PREFIX + PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_SUM_FIELD);
				if (validate(maxPaySum, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_SUM_FIELD)))
					thresholdAutoPayScheme.setMaxSumThreshold(ReplicationHelper.parseBigDecimalValue(maxPaySum));

				String minPayValue = (String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_VALUE_FIELD);
				if (validate(minPayValue, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MIN_VALUE_FIELD)))
					thresholdAutoPayScheme.setMinValueThreshold(ReplicationHelper.parseBigDecimalValue(minPayValue));

				String maxPayValue = (String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_VALUE_FIELD);
				if (validate(maxPayValue, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_MAX_VALUE_FIELD)))
					thresholdAutoPayScheme.setMaxValueThreshold(ReplicationHelper.parseBigDecimalValue(maxPayValue));

				List<String> ThresholdLimitMap = (List<String>) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD);

				boolean valid = true;
				for (String item : ThresholdLimitMap)
					if (!validate(item, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_LIMIT_FIELD)))
						valid = false;

				thresholdAutoPayScheme.setInterval(ThresholdLimitMap.isEmpty());

                if (valid)
					thresholdAutoPayScheme.setDiscreteValues(StringUtils.join(ThresholdLimitMap, '|'));

				provider.setThresholdAutoPayScheme(thresholdAutoPayScheme);

				String isAccessTotalAmountStr = (String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_IS_ACCESS_TOTAL_AMOUNT_LIMIT_FIELD);
				if (validate(isAccessTotalAmountStr, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_IS_ACCESS_TOTAL_AMOUNT_LIMIT_FIELD)))
					thresholdAutoPayScheme.setAccessTotalMaxSum(ReplicationHelper.parseBooleanValue(isAccessTotalAmountStr));

				if(thresholdAutoPayScheme.isAccessTotalMaxSum())
				{
					String totalAmountPeriodStr = (String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_PERIOD_FIELD);
					// проверяем ручками а не валидатором, т.к. поле обязательно только елси флажок доступности суммы равен true
					if(StringHelper.isEmpty(totalAmountPeriodStr))
					{
						errorsCollector.add("Значение ThresholdTotalAmountPeriod не может быть пустым при установленном флажке ThresholdIsAccessTotalAmount");
					}
					else if (validate(totalAmountPeriodStr, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_PERIOD_FIELD)))
					{
						thresholdAutoPayScheme.setPeriodMaxSum(TotalAmountPeriod.valueOf(totalAmountPeriodStr));
					}

					String totalAmountLimitStr = (String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_LIMIT_FIELD);
					if (validate(totalAmountLimitStr, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_TOTAL_AMOUNT_LIMIT_FIELD)))
					{
						thresholdAutoPayScheme.setTotalMaxSum(ReplicationHelper.parseBigDecimalValue(totalAmountLimitStr));
					}
				}

				String clientHint = (String) source.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_PREFIX + PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_CLIENT_HINT_FIELD);
				if (validate(clientHint, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_THRESHOLD_SCHEM_CLIENT_HINT_FIELD)))
					thresholdAutoPayScheme.setClientHint(clientHint);

				provider.setThresholdAutoPayScheme(thresholdAutoPayScheme);
			}

			Boolean invoiceAutoPay = ReplicationHelper.parseBooleanValue((String) source.get(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_FIELD));
			if (invoiceAutoPay != null && invoiceAutoPay)
			{
				InvoiceAutoPayScheme invoiceAutoPayScheme = new InvoiceAutoPayScheme();

				String clientHint = (String) source.get(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_PREFIX + PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_CLIENT_HINT_FIELD);
				if (validate(clientHint, errorsCollector, fieldValidators.get(PROVIDER_AUTO_PAYMENT_INVOICE_SCHEM_CLIENT_HINT_FIELD)))
					invoiceAutoPayScheme.setClientHint(clientHint);

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
		if(FieldDataType.string == field.getType())
			field.setMask((String)mapAttribute.get(ATTRIBUTE_MASK_FIELD));

		String businessSubType = (String) mapAttribute.get(ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD);
		if (validate(businessSubType, errorsCollector, fieldValidators.get(ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD)))
			field.setBusinessSubType(StringHelper.isNotEmpty(businessSubType) ? BusinessFieldSubType.valueOf(businessSubType) : null);

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

		String values = (String) mapAttribute.get(ATTRIBUTE_VALUES_FIELD);
		if (validate(values, errorsCollector, fieldValidators.get(ATTRIBUTE_VALUES_FIELD)))
			field.setValuesAsString(values, FieldDescription.VALUES_SPLITER);
	}

	protected void validateFieldDescriptions(List<FieldDescription> fieldDescriptions)
	{
		BillingServiceProvider provider = getProvider();
		List<String> errorCollector = getErrors();
		// Если  тип поставщика – «Мобильная связь», то  только одно ключевое поле  может быть отмечено типом, и тип может принимать только значение – «Мобильный телефон»
		if (provider.getSubType() == ServiceProviderSubType.mobile)
		{
			int counter = 0;
			for (FieldDescription fd : fieldDescriptions)
			{
				if (fd.isKey() && fd.getBusinessSubType() != null)
					counter++;
				if (counter > 1 || (fd.getBusinessSubType() != null && fd.getBusinessSubType() != BusinessFieldSubType.phone && fd.isKey()))
				{
					errorCollector.add("Для поставщика с типом «Мобильная связь» должно быть добавлено одно ключевое поле, имеющее тип «Мобильный телефон»");
					setReplicated(false);
					return;
				}
			}
			if(counter == 0)
			{
				errorCollector.add("Для поставщика с типом «Мобильная связь» должно быть добавлено одно ключевое поле, имеющее тип «Мобильный телефон»");
				setReplicated(false);
			}
		}
	}
}
