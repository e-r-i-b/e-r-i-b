package com.rssl.phizic.gate.einvoicing;

/**
 * Состояние заказа.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum OrderState
{
	CREATED ("принят"),
	RELATED ("привязан к клиенту"),
	CANCELED ("отменен клиентом"),
	PAYMENT ("оплата"),
	WRITE_OFF ("списание средств"),
	ERROR ("ошибка исполнения"),
	EXECUTED ("оплачен"),
	REFUSED ("отказан"),
	PARTIAL_REFUND ("частичный возврат"),
	REFUND ("возврат"),
	DELAYED("отложен клиентом");

	final String description;

	OrderState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
