package com.rssl.phizic.business.dictionaries.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderType;
import com.rssl.phizic.business.dictionaries.providers.TaxationServiceProvider;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author gladishev
 * @ created 24.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderINNValidator extends MultiFieldsValidatorBase
{
	public static final String PROVIDER_ID_FIELD = "id";
	public static final String PROVIDER_TYPE_FIELD = "providerType";
	public static final String PROVIDER_INN_FIELD = "inn";
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String providerType = (String) retrieveFieldValue(PROVIDER_TYPE_FIELD, values);

		if (!ServiceProviderType.valueOf(providerType).equals(ServiceProviderType.TAXATION))
			return true;

		String providerINN = (String) retrieveFieldValue(PROVIDER_INN_FIELD, values);
		if (StringHelper.isEmpty(providerINN))
			return true;

		TaxationServiceProvider provider;
		try
		{
			provider = providerService.findTaxProviderByINN(providerINN, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}

		String providerId = (String) retrieveFieldValue(PROVIDER_ID_FIELD, values);
		return (provider == null) || (provider.getId().toString().equals(providerId));

	}
}
