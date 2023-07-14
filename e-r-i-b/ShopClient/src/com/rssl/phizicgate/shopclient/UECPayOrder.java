package com.rssl.phizicgate.shopclient;

/**
 * @author Erkin
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Бин с данными платёжного поручения УЭК.
 * Используется для передачи статуса поручения в УЭК:
 * - на входе DocUID и статус платежа
 * - на выходе результат процесса передачи (получилось / не получилось).
 */
public class UECPayOrder
{
	/**
	 * ID ЕРИБ-заказа, соответствующего поручению
	 */
	private final long orderId;

	/**
	 * ID поручения в УЭК
	 */
	private final String docUID;

	/**
	 * Статус ЕРИБ-платежа, соответствующего поручению
	 */
	private final String paymentState;

	/**
	 * Код статуса оповещения
	 */
	private Long notifyStatusCode = null;

	/**
	 * Текстовка статуса оповещения
	 */
	private String notifyStatusDescr = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param orderId - ID ЕРИБ-платежа, соответствующего поручению
	 * @param docUID - ID поручения в УЭК
	 * @param paymentState - статус ЕРИБ-платежа, соответствующего поручению
	 */
	public UECPayOrder(long orderId, String docUID, String paymentState)
	{
		this.orderId = orderId;
		this.docUID = docUID;
		this.paymentState = paymentState;
	}

	/**
	 * @return ID поручения в УЭК
	 */
	public String getDocUID()
	{
		return docUID;
	}

	/**
	 * @return ID ЕРИБ-платежа, соответствующего поручению
	 */
	public long getOrderId()
	{
		return orderId;
	}

	/**
	 * @return статус ЕРИБ-платежа, соответствующего поручению
	 */
	public String getPaymentState()
	{
		return paymentState;
	}

	public Long getNotifyStatusCode()
	{
		return notifyStatusCode;
	}

	public void setNotifyStatusCode(Long notifyStatusCode)
	{
		this.notifyStatusCode = notifyStatusCode;
	}

	public String getNotifyStatusDescr()
	{
		return notifyStatusDescr;
	}

	public void setNotifyStatusDescr(String notifyStatusDescr)
	{
		this.notifyStatusDescr = notifyStatusDescr;
	}
}
