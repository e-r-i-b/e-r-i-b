package com.rssl.phizic.gate.clients;

/**
 * �������� ������������ �������. ��� ����� ��������� ��� ������� ��� � ����������
 *
 * @author egorova
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

public interface User extends Client
{
	/**
	 * ��������� ������������ - ��������� ��� ������
	 *
	 * @return ��������� ������������
	 */
	UserCategory getCategory();
}
