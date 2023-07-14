package com.rssl.phizic.business.ext.sbrf.mobilebank.validator;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author Erkin
 * @ created 24.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class UniqueMobileBankCodeValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_PROVIDER_ID = "id";

	public static final String FIELD_MOBILEBANK_CODE = "mobilebankCode";

	private static final ServiceProviderService serviceProviderService
			= new ServiceProviderService();

	/**
	 * Конструктор
	 * @param message - сообщение об ошибке
	 */
	public UniqueMobileBankCodeValidator(String message)
	{
		setMessage(message);
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String mobilebankCode = (String) retrieveFieldValue(FIELD_MOBILEBANK_CODE, values);
		if (StringHelper.isEmpty(mobilebankCode))
			return true;

		String providerIdAsString = (String) retrieveFieldValue(FIELD_PROVIDER_ID, values);
		Long providerId = null;
		if (!StringHelper.isEmpty(providerIdAsString))
			providerId = Long.valueOf(providerIdAsString);

		try
		{
			BillingServiceProvider provider = serviceProviderService.findByMobileBankCode(mobilebankCode, MultiBlockModeDictionaryHelper.getDBInstanceName());
			boolean rc = provider == null;
			rc=rc || provider.getId().equals(providerId);
			return rc;
		}
		catch (BusinessException ex)
		{
			throw new TemporalDocumentException(ex);
		}
	}
}
