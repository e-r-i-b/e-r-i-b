package com.rssl.phizic.gate.einvoicing;

/**
 * Информация о статусе заказа
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class OrderStateInfo
{
	private OrderState state; //статус заказа
	private String utrrno; //уникальный код операции в биллинге

	/**
	 * ctor
	 * @param state - статус заказа
	 * @param utrrno - уникальный код операции в биллинге
	 */
	public OrderStateInfo(OrderState state, String utrrno)
	{
		this.state = state;
		this.utrrno = utrrno;
	}

	/**
	 * @return статус заказа
	 */
	public OrderState getState()
	{
		return state;
	}

	/**
	 * @return уникальный код операции в биллинге
	 */
	public String getUtrrno()
	{
		return utrrno;
	}
}
