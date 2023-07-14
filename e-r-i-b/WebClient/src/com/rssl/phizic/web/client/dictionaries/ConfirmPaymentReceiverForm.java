package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Krenev
 * @ created 15.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmPaymentReceiverForm extends ActionFormBase
{
	private Long id;
	private PaymentReceiverBase receiver;

	public ConfirmPaymentReceiverForm() {}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setReceiver(PaymentReceiverBase receiver)
	{
		this.receiver = receiver;
	}

	public PaymentReceiverBase getReceiver()
	{
		return receiver;
	}
}
