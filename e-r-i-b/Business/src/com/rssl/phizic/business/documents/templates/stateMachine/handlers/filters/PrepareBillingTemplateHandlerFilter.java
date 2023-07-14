package com.rssl.phizic.business.documents.templates.stateMachine.handlers.filters;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;

/**
 * Фильтр хендлера, проверяющий необходимость подготовки шаблона платежа в биллинге (согласования с биллингом)
 *
 * @author khudyakov
 * @ created 03.08.14
 * @ $Author$
 * @ $Revision$
 */
public class PrepareBillingTemplateHandlerFilter extends HandlerFilterBase<TemplateDocument>
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();


	public boolean isEnabled(TemplateDocument template) throws DocumentException, DocumentLogicException
	{
		if (!AbstractPaymentSystemPayment.class.isAssignableFrom(template.getType()))
		{
			return false;
		}

		if (isEmptyReceiverBank(template))
		{
			throw new DocumentLogicException("На данный момент оплата услуг в адрес выбранного получателя запрещена.");
		}

		return StringHelper.isEmpty(template.getIdFromPaymentSystem());
	}

	protected boolean isEmptyReceiverBank(TemplateDocument template) throws DocumentException
	{
		try
		{
			ServiceProviderShort provider = serviceProviderService.findShortProviderBySynchKey(template.getReceiverPointCode());
			if (provider == null || !provider.isBankDetails())
			{
				return false;
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		ResidentBank residentBank = template.getReceiverBank();
		return residentBank == null || StringHelper.isEmpty(residentBank.getBIC());
	}
}
