package com.rssl.phizic.business.quick.pay;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.SumRestrictions;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author komarov
 * @ created 29.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class ProviderSummValidator extends MultiFieldsValidatorBase
{
	private static final ServiceProviderService providerService = new ServiceProviderService();

	public static final String PROVIDER_ID = "providerId";
	public static final String SUMM        = "summ";


	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long providerID =  Long.parseLong((String)retrieveFieldValue(PROVIDER_ID, values));
		BigDecimal summ =  (BigDecimal)retrieveFieldValue(SUMM, values);

		ServiceProviderBase provider = null;
		try
		{
			provider = providerService.findById(providerID, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch (BusinessException be)
		{
			throw new TemporalDocumentException(be);
		}

		if(provider == null)
			throw new TemporalDocumentException("Ошибка при получении ограничений суммы операций.");

		SumRestrictions summRestrictions =  provider.getRestrictions();
		if(summRestrictions == null)
			return true;

		BigDecimal maxSumm = summRestrictions.getMaximumSum();
		BigDecimal minSumm = summRestrictions.getMinimumSum();

		if(maxSumm != null && maxSumm.compareTo(summ) < 0)
		{
			setMessage(getMessage() + " меньше " +maxSumm+ ".");
			return false;
		}

		if(minSumm != null && minSumm.compareTo(summ) > 0)
		{
			setMessage(getMessage() + " больше " +minSumm+ ".");
			return false;
		}

		return true;
	}
}
