package com.rssl.phizic.gate.einvoicing;

/**
 * ���������� � ������� �������� ��� ������
 * @author gladishev
 * @ created 12.03.2014
 * @ $Author$
 * @ $Revision$
 */

public class RecallStateInfo
{
	private RecallState state; //������ ������
	private String utrrno; //���������� ��� �������� � ��������

	/**
	 * ctor
	 * @param state - ������ ������
	 * @param utrrno - ���������� ��� �������� � ��������
	 */
	public RecallStateInfo(RecallState state, String utrrno)
	{
		this.state = state;
		this.utrrno = utrrno;
	}

	/**
	 * @return ������ ������
	 */
	public RecallState getState()
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
