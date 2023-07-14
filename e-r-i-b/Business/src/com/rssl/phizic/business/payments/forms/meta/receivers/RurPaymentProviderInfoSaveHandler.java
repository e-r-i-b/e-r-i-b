package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.ServiceImpl;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.exceptions.WrongDocumentTypeException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;

/**
 * @author gladishev
 * @ created 12.11.2014
 * @ $Author$
 * @ $Revision$
 * Хендлер сохранения дополнительной информации о получателе в биллинговом платеже:
 * Сохраняется:
 * 1) информация о предоставляемой услуге.
 */

public class RurPaymentProviderInfoSaveHandler extends BusinessDocumentHandlerBase
{

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof RurPayment))
		{
			throw new WrongDocumentTypeException((BusinessDocument) document, RurPayment.class);
		}
		RurPayment rurPayment = (RurPayment) document;
		try
		{
			ServiceProviderShort serviceProvider = ServiceProviderHelper.getServiceProvider(rurPayment.getReceiverInternalId());
			if (serviceProvider == null)
			{
				throw new DocumentException("Не задан поставщик услуг");
			}

			rurPayment.setService(new ServiceImpl(serviceProvider.getCodeService(), serviceProvider.getNameService()));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

	}
}
