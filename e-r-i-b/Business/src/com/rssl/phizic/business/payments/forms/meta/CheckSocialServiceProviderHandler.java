package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.JurPayment;

/**
 * @author sergunin
 * @ created 19.11.2014
 * @ $Authors$
 * @ $Revision$
 */
public class CheckSocialServiceProviderHandler extends BusinessDocumentHandlerBase
{
	private static final ServiceProviderService service = new ServiceProviderService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof JurPayment))
			return;
		JurPayment payment = (JurPayment) document;
		Long providerId = payment.getReceiverInternalId();
		/**
		 * В случаях если это:
		 * - перевод юрику с карты
		 * - перевод юрику со вклада через билинговую систему, установленную в ТБ. (возможно только в БС ЦПФЛ)
		 * то id ПУ заполнен не будет, поэтому проверку не выполняем.
		 */
		if (providerId == null)
			return;
		ServiceProviderShort spb = findProviderById(providerId);
		if (spb == null)
			throw new DocumentException("Не найден поставщик услуг с идентификатором " + providerId);
		if (!(spb.getKind().equals("B")))
			throw new DocumentException("Получатель с идентификатором " + providerId + " не является билинговым поставщиком услуг");

		if ( !spb.isAvailablePaymentsForSocialApi())
			throw new DocumentLogicException("Поставщик недоступен для социального приложения.");
	}

	private ServiceProviderShort findProviderById(Long id) throws DocumentException
	{
		try
		{
			return service.findShortProviderById(id);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("Ошибка при поиске поставщика с идентификатором " + id, e);
		}
	}
}
