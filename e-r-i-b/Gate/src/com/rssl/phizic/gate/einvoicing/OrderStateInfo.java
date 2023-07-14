package com.rssl.phizic.gate.einvoicing;

/**
 * ���������� � ������� ������
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class OrderStateInfo
{
	private OrderState state; //������ ������
	private String utrrno; //���������� ��� �������� � ��������

	/**
	 * ctor
	 * @param state - ������ ������
	 * @param utrrno - ���������� ��� �������� � ��������
	 */
	public OrderStateInfo(OrderState state, String utrrno)
	{
		this.state = state;
		this.utrrno = utrrno;
	}

	/**
	 * @return ������ ������
	 */
	public OrderState getState()
	{
		return state;
	}

	/**
	 * @return ���������� ��� �������� � ��������
	 */
	public String getUtrrno()
	{
		return utrrno;
	}
}
