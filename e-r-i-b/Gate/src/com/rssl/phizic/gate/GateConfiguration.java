package com.rssl.phizic.gate;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 22.12.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ��������� ���������� �����.
 */
public interface GateConfiguration extends Serializable
{
	/**
	 * ��� ������������, �� ����� �������� ��������������� ���������� � ������� ��������
	 *
	 * @return ��� ������������, �� ����� �������� ��������������� ���������� � ������� ��������
	 * */
	String getUserName();

	/**
	 * ����� �������� ������ �� ������� ������� � �������������.
	 *
	 * @return  ����� �������� ������ �� ������� ������� � �������������
	 * */
	Long getConnectionTimeout();

	/**
	 * ����� ������ � ������� ��������: ����������� ��� ����������
	 *
	 * @return ����� ������ � ������� ��������: ����������� ��� ����������
	 * */
	ConnectMode getConnectMode();
}