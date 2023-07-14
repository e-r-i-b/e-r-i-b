package com.rssl.common.forms.state;

import com.rssl.phizic.common.types.documents.State;

/**
 * @author Omeliyanchuk
 * @ created 23.06.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ��� �������� ����������� � ��������� ���������
 */
public interface StateObject
{
	/**
	 * @return ������������� ������� ������ ���������
	 */
	Long getId();

	/**
	 * �������� ���������� � ������ ���������
	 * @return ���������� � ������ ���������
	 */
	StateMachineInfo getStateMachineInfo();

	/**
	 * �������� ������� ���������
	 * @return ������� ���������
	 */
	State getState();

	/**
	 * �������� ������� ���������
	 * @param state ����� ���������
	 */
	void setState(State state);

	/**
	 * ������������� ��� ������� � ������� ��������� ��������
	 * @param name ��� �������
	 */
	void setSystemName(String name);

	/**
	 * �������� ��������� ���������
	 * @return ��������� ���������
	 */
	String getNextState();
}
