package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.CloseInvoiceSubscriptionClaim;
import com.rssl.phizic.business.documents.payments.source.NewInvoiceSubscriptionClaimSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * Удаление автоматически сгенерированной подписки
 * @author niculichev
 * @ created 18.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CloseInvoiceSubscriptionOperation extends EditDocumentOperationBase
{
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	private InvoiceSubscription subscription;

	public void initializeForGenerated(Long subscriptionId) throws BusinessException, BusinessLogicException
	{
		if (subscriptionId == null)
			throw new BusinessException("Не задан идентификатор подписки");

		InvoiceSubscription tempSub = invoiceSubscriptionService.findById(subscriptionId);

		if (tempSub == null)
			throw new BusinessException("Услуга по идентификатору " + subscriptionId + " не найдена.");

		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(tempSub))
			throw new BusinessException("Подписка с id = " + subscriptionId + " не принадлежит клиенту");

		if(tempSub.getCreationType() != CreationType.system)
			throw new BusinessException("Подписка создана не автоматически.");

		this.subscription = tempSub;
		NewInvoiceSubscriptionClaimSource source = new NewInvoiceSubscriptionClaimSource(subscription, FormConstants.CLOSE_INVOICE_SUBSCRIPTION_CLAIM);
		initialize(source);
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));
	}

	public void initializeForRecoverAutoSub(Long subscriptionId) throws BusinessException, BusinessLogicException
	{
		if (subscriptionId == null)
			throw new BusinessException("Не задан идентификатор подписки");

		InvoiceSubscription tempSub = invoiceSubscriptionService.findById(subscriptionId);

		if (tempSub == null)
			throw new BusinessException("Услуга по идентификатору " + subscriptionId + " не найдена.");

		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(tempSub))
			throw new BusinessException("Подписка с id = " + subscriptionId + " не принадлежит клиенту.");

		if(StringHelper.isEmpty(tempSub.getAutoSubExternalId()))
			throw new BusinessException("Подписка с id = " + subscriptionId + " не может восстанавливать автоплатеж.");

		this.subscription = tempSub;
		NewInvoiceSubscriptionClaimSource source = new NewInvoiceSubscriptionClaimSource(subscription, FormConstants.CLOSE_INVOICE_SUBSCRIPTION_CLAIM);

		// инициализируем параметры для последующего восстановления
		CloseInvoiceSubscriptionClaim claim = (CloseInvoiceSubscriptionClaim) source.getDocument();
		claim.setLongOfferExternalId(tempSub.getAutoSubExternalId());
		claim.setRecoverAutoSubscription(true);

		initialize(source);
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));
	}

	@Transactional
	public void remove() throws BusinessException, BusinessLogicException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, getSourceEvent()));
		target.save(document);
	}

	public InvoiceSubscription getEntity()
	{
		return subscription;
	}
}
