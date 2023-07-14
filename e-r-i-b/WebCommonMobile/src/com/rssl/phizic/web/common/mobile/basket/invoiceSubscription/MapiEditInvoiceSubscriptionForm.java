package com.rssl.phizic.web.common.mobile.basket.invoiceSubscription;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.web.common.mobile.payments.forms.EditMobilePaymentForm;

/**
 * @author Balovtsev
 * @since 05.11.14.
 */
public class MapiEditInvoiceSubscriptionForm extends EditMobilePaymentForm
{
	private ConfirmStrategy confirmStrategy;
	private boolean anotherStrategyAvailable;
	private Long subscriptionId;
	private boolean recoverAutoSubscription;

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public boolean isAnotherStrategyAvailable()
	{
		return anotherStrategyAvailable;
	}

	public void setAnotherStrategyAvailable(boolean anotherStrategyAvailable)
	{
		this.anotherStrategyAvailable = anotherStrategyAvailable;
	}

	public Long getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}

	/**
	 * @return Необходимо ли восстаноаить автоплатеж при успешном закрытии.
	 */
	public boolean isRecoverAutoSubscription()
	{
		return recoverAutoSubscription;
	}

	/**
	 * @param recoverAutoSubscription Необходимо ли восстаноаить автоплатеж при успешном закрытии.
	 */
	public void setRecoverAutoSubscription(boolean recoverAutoSubscription)
	{
		this.recoverAutoSubscription = recoverAutoSubscription;
	}
}
