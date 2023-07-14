package com.rssl.phizgate.common.payments.offline.processState;

/**
 * @author vagin
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 * Состояние обработки входящих сообщений от АС "AutoPay" в рамках корзины платежей.
 */
public class BasketInvoiceProcessState
{
	private static final String LOCK_KEY = "invoice_basket_processing_state";

	private String key;

	public BasketInvoiceProcessState()
	{
		this.key = LOCK_KEY;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}
}
