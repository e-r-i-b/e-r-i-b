package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.fields.FieldDescription;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author khudyakov
 * @ created 03.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class DeleteServiceProviderFetcher extends ServiceProviderFetcherBase implements ServiceProviderFetcher
{
	private static final Map<String, FieldValidator[]> fieldValidators = new HashMap<String, FieldValidator[]>();

	static
	{
		fieldValidators.put(PROVIDER_CODE_BS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_BS_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_BS_FIELD)});
		fieldValidators.put(PROVIDER_CODE_SERVICE_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,50}$", PROVIDER_CODE_SERVICE_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_SERVICE_FIELD)});
		fieldValidators.put(PROVIDER_CODE_RECIPIENT_BS_FIELD, new FieldValidator[]{ReplicationHelper.buildRegexpValidator("^.{0,20}$", PROVIDER_CODE_RECIPIENT_BS_FIELD), ReplicationHelper.buildRequiredValidator(PROVIDER_CODE_RECIPIENT_BS_FIELD)});
	}

	protected Map<String, FieldValidator[]> getFieldValidators()
	{
		return Collections.unmodifiableMap(fieldValidators);
	}

	DeleteServiceProviderFetcher(List<String> errorsCollector, String dbInstanceName)
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

		Billing billing = null;
		String codeBilling = (String) source.get(PROVIDER_CODE_BS_FIELD);
		if (validate(codeBilling, errorsCollector, fieldValidators.get(PROVIDER_CODE_BS_FIELD)))
		{
			billing = ReplicationHelper.findBilling(codeBilling, getDbInstanceName());
            ReplicationHelper.validate(PROVIDER_CODE_BS_FIELD, codeBilling, billing, errorsCollector);
		}

		String codeService  = (String) source.get(PROVIDER_CODE_SERVICE_FIELD);
		validate(codeService, errorsCollector, fieldValidators.get(PROVIDER_CODE_SERVICE_FIELD));

		String providerCode = (String) source.get(PROVIDER_CODE_RECIPIENT_BS_FIELD);
		validate(providerCode, errorsCollector, fieldValidators.get(PROVIDER_CODE_RECIPIENT_BS_FIELD));

		StringBuffer synchKey = new StringBuffer();
		if (billing != null)
		{
			synchKey.append(codeService).append(ADDITIONAL_DELIMITER).append(providerCode).append(DELIMITER).append(billing.restoreRouteInfo());
		}

		BillingServiceProvider temp = ReplicationHelper.findProviderBySynchKey(synchKey.toString());
		if (temp == null)
		{
			setReplicated(false);
			return;
		}
		setProvider(temp);
	}

	protected void setProviderAdditionalFields(Map<String, Object> source)
	{
		//nothing;
	}

	protected void setFieldAdditionalAttributes(Map<String, Object> mapAttribute, FieldDescription field)
	{
		//nothing;
	}
}
