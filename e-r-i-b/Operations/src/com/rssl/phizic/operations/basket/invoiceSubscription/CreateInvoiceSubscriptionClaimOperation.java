package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.source.NewInvoiceSubscriptionClaimSource;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 23.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция для создания заявок по подпискам
 */
public class CreateInvoiceSubscriptionClaimOperation extends EditDocumentOperationBase
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	/**
	 * Инициализировать операцию
	 * @param subscriptionId идентификатор подписки
	 * @param formName имя формы
	 */
	public void initialize(Long subscriptionId, String formName) throws BusinessLogicException, BusinessException
	{
		if (subscriptionId == null)
		{
			throw new IllegalArgumentException("Идентификатор подписки не может быть null");
		}
		if (StringHelper.isEmpty(formName))
		{
			throw new IllegalArgumentException("Имя формы не может быть null");
		}

		InvoiceSubscription subscription = invoiceSubscriptionService.findById(subscriptionId);

		if (subscription == null)
		{
			throw new BusinessException("Услуга по идентификатору " + subscriptionId + " не найдена.");
		}
		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(subscription))
		{
			throw new BusinessException("Подписка с id = " + subscriptionId + " не принадлежит клиенту");
		}

		NewInvoiceSubscriptionClaimSource source = new NewInvoiceSubscriptionClaimSource(subscription, formName);
		initialize(source);
	}
}
