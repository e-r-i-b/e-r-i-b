package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.access.NullConfirmStrategySource;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * Подтверждение подписки на инвойсы(через оплату инвойса)
 * @author niculichev
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmInvoiceSubscriptionOperation extends ConfirmableOperationBase
{
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	private InvoiceSubscription subscription;

	public void initialize(Long subscriptionId) throws BusinessException
	{
		if(subscriptionId == null || subscriptionId.equals(0L))
			throw new BusinessException("Не указан идентификатор подписки для подтверждения");

		InvoiceSubscription temp = invoiceSubscriptionService.findById(subscriptionId);
		if(temp == null)
			throw new BusinessException("Не найдена подписка с id = " + subscriptionId);

		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(temp))
			throw new BusinessException("Подписка с id = " + temp.getId() + " не принадлежит клиенту");

		initializeNew();
		this.subscription = temp;
	}

	public void initializeByInvoice(Long invoiceId) throws BusinessException
	{
		if(invoiceId == null || invoiceId.equals(0L))
			throw new BusinessException("Не задан идентификатор инвойса");

		InvoiceSubscription temp = invoiceSubscriptionService.findInvoiceSubByInvoiceId(invoiceId);
		if(temp == null)
			throw new BusinessException("Не найдена подписка по инвойсу с id = " + invoiceId);

		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(temp))
			throw new BusinessException("Подписка с id = " + temp.getId() + " не принадлежит клиенту");

		initializeNew();
		this.subscription = temp;
	}

	public void setUserStrategyType(ConfirmStrategyType type)
	{
		super.setUserStrategyType(type);
		subscription.setConfirmStrategyType(type);
	}

	@Override
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		invoiceSubscriptionService.update(subscription);
	}

	public ConfirmableObject getConfirmableObject()
	{
		return subscription;
	}

	public ConfirmStrategySource getStrategy()
	{
		//В mAPI стратегия без подтверждения
		if (ApplicationUtil.isMobileApi())
			return new NullConfirmStrategySource();
		return super.getStrategy();
	}
}
