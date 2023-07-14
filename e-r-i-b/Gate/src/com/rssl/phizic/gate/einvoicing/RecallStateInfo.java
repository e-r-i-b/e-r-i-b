package com.rssl.phizic.gate.einvoicing;

/**
 * Информация о статусе возврата или отмены
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class RecallStateInfo
{
	private RecallState state; //статус заказа
	private String utrrno; //уникальный код операции в биллинге

	/**
	 * ctor
	 * @param state - статус заказа
	 * @param utrrno - уникальный код операции в биллинге
	 */
	public RecallStateInfo(RecallState state, String utrrno)
	{
		this.state = state;
		this.utrrno = utrrno;
	}

	/**
	 * @return статус заказа
	 */
	public RecallState getState()
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
