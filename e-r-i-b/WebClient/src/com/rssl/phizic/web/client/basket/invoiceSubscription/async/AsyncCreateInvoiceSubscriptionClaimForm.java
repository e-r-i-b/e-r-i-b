package com.rssl.phizic.web.client.basket.invoiceSubscription.async;

import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;

/**
 * @author niculichev
 * @ created 10.09.14
 * @ $Author$
 * @ $Revision$
 */
public class AsyncCreateInvoiceSubscriptionClaimForm extends ConfirmPaymentByFormForm
{
	private boolean refresh;
	private Long invoiceId;

	public boolean isRefresh()
	{
		return refresh;
	}

	public void setRefresh(boolean refresh)
	{
		this.refresh = refresh;
	}

	/**
	 * @return идентификатор инвойса, относительно которого выполняется запрос
	 */
	public Long getInvoiceId()
	{
		return invoiceId;
	}

	/**
	 * установить идентификатор инвойса, относительно которого выполняется запрос
	 * @param invoiceId идентификатор инвойса
	 */
	public void setInvoiceId(Long invoiceId)
	{
		this.invoiceId = invoiceId;
	}
}
