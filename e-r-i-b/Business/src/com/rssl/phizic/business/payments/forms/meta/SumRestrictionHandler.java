package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.common.types.Money;

/**
 * @author bogdanov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер на проверку ограничений суммы операции.
 * Ограничений может быть два: на минимальную сумму платежа и на максимальную сумму платежа.
 */

public class SumRestrictionHandler extends BusinessDocumentHandlerBase
{
	private static final String MIN_SUMM_RESTRICTION_MESSAGE
			= "Сумма операции в адрес данного поставщика услуг не может быть меньше %.2f руб. Укажите другую сумму";
	private static final String ERMB_MIN_SUMM_RESTRICTION_MESSAGE
			= "Операция не выполнена. сумма платежа меньше минимальной: %.2f руб";
	private static final String MAX_SUMM_RESTRICTION_MESSAGE
			= "Сумма операции в адрес данного поставщика услуг не может быть больше %.2f руб. Укажите другую сумму";
	private static final String SUMM_RESTRICTION_MESSAGE
			= "Сумма операции в адрес данного поставщика услуг не может быть меньше %.2f руб. и больше %.2f руб. Укажите другую сумму";


	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		Money exactAmount;
		Long serviceProviderId;
		CreationType creationChannel;
		if (document instanceof JurPayment)
		{
			JurPayment payment = (JurPayment) document;
			exactAmount = payment.getExactAmount();
			serviceProviderId = payment.getReceiverInternalId();
			creationChannel = payment.getClientCreationChannel();
		}
		else if (document instanceof RurPayment)
		{
			RurPayment payment = (RurPayment) document;
			if (!payment.isServiceProviderPayment())
				return;

			exactAmount = payment.getExactAmount();
			serviceProviderId = payment.getReceiverInternalId();
			creationChannel = payment.getClientCreationChannel();
		}
		else return;
		ServiceProviderShort serviceProvider;
		try
		{
			serviceProvider  = ServiceProviderHelper.getServiceProvider(serviceProviderId);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (serviceProvider == null)
			return;


		boolean hasMin = serviceProvider.getMinimumSum() != null;
		boolean hasMax = serviceProvider.getMaximumSum() != null;

		//проверяем на соответствие ограничениям.
		boolean lessThan = hasMin && serviceProvider.getMinimumSum().compareTo(exactAmount.getDecimal()) > 0;
		boolean greaterThan = hasMax && serviceProvider.getMaximumSum().compareTo(exactAmount.getDecimal()) < 0;
		if (!lessThan && !greaterThan)
			return;

		//ермб текстовки
		if (lessThan && creationChannel == CreationType.sms)
			throw new DocumentLogicException((String.format(ERMB_MIN_SUMM_RESTRICTION_MESSAGE, serviceProvider.getMinimumSum())));
		//не ермб текстовки
		else if (hasMin && hasMax)
			throw new DocumentLogicException((String.format(SUMM_RESTRICTION_MESSAGE, serviceProvider.getMinimumSum(), serviceProvider.getMaximumSum())));
		else if (hasMin)
			throw new DocumentLogicException((String.format(MIN_SUMM_RESTRICTION_MESSAGE, serviceProvider.getMinimumSum())));
		else if (hasMax)
			throw new DocumentLogicException(String.format(MAX_SUMM_RESTRICTION_MESSAGE, serviceProvider.getMaximumSum()));
		else
			;//ok
	}
}
