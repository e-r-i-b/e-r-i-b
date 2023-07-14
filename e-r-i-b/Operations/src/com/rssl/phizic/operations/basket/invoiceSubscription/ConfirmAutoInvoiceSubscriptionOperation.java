package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;

/**
 * Подтверждение преобразование автоподписки в автоплатеж
 * @author niculichev
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmAutoInvoiceSubscriptionOperation extends ConfirmFormPaymentOperation
{
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		JurPayment payment = (JurPayment) source.getDocument();
		Long invoiceSubId = payment.getInvoiceSubscriptionId();

		if(invoiceSubId == null)
			throw new BusinessException("Идентификатор подписки в документе с id = " + payment.getId() + " не может быть null");

		InvoiceSubscription temp = invoiceSubscriptionService.findById(invoiceSubId);
		if(temp == null)
			throw new BusinessException("Не найдена подписка с id = " + invoiceSubId);

		if(!((InvoiceSubscriptionRestriction)getRestriction()).accept(temp))
			throw new BusinessException("Подписка с id = " + invoiceSubId + " не принадлежит клиенту");

		super.initialize(source);
	}
}
