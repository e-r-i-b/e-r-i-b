package com.rssl.phizic.context;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 23.02.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ����������, ������� ���������� ��������� � ����������� � ������������ �������.
 * � ����������� ������ ������ ���������� ������ � ���������� �� �����.
 * � ����������� ������ ������ ����� �� ��� �����.
 *
 * �����!!! ������� �������� ������ ��� ����������� ��������������� ������������ �����.
 * � ����������� �� �������������� ������������ ����� ������ ����� ������ �� �����.
 */
public interface MultiNodeEmployeeData extends Serializable
{

	/**
	 * @return ������������� ������ ����������
	 */
	Long getLoginId();

	/**
	 * @return ������ ���������� �� ���� ��
	 */
	boolean isAllTbAccess();

}
