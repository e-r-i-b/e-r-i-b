package com.rssl.phizic.operations.basket;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author tisov
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 * Операция привязки автоподписки к объекту учёта (вызывается ассинхронно при манипуляции клиента с визуальными объектами на странице)
 */

public class BindSubscriptionToEntityOperation extends OperationBase
{
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void move(Long subscriptionId, Long entityId, String type) throws BusinessException, BusinessLogicException
	{
		if(StringHelper.isEmpty(type) || !"invoiceSubType".equals(type))
			throw new BusinessLogicException("Невозможно выполнить операцию для данного типа " + type);

		bindInvoiceSub(subscriptionId, entityId);
	}

	private void bindInvoiceSub(Long subscriptionId, Long entityId) throws BusinessException
	{
		long loginId = PersonHelper.getContextPerson().getLogin().getId();
		if (entityId == null)
		{
			invoiceSubscriptionService.unbindSubscriptionToAccountingEntity(subscriptionId, loginId);
		}
		else
		{
			invoiceSubscriptionService.bindSubscriptionToAccountingEntity(subscriptionId, entityId, loginId);
		}
	}
}
