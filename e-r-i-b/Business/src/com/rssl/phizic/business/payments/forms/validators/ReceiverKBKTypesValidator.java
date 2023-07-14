package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.dictionaries.kbk.KBKService;
import com.rssl.phizic.business.dictionaries.kbk.KBK;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.TaxationServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;

/**
 * @author gladishev
 * @ created 18.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class ReceiverKBKTypesValidator extends MultiFieldsValidatorBase
{
	private static final KBKService kbkService = new KBKService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private static final String INN_PARAMETER = "receiverINN";
	private static final String KBK_PARAMETER = "taxKBK";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String INNValue = (String)retrieveFieldValue(INN_PARAMETER, values);
		String KBKValue = (String)retrieveFieldValue(KBK_PARAMETER, values);
		KBK kbk;
		TaxationServiceProvider provider;
		try
		{
			kbk = kbkService.findByCode(KBKValue);
			if (kbk == null)
				return false;

			provider = serviceProviderService.findTaxProviderByINN(INNValue);
			if (provider == null)
				return true;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		return kbk.getPaymentType().equals(provider.getPayType());
	}
}
