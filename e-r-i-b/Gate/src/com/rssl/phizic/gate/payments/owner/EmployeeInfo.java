package com.rssl.phizic.gate.payments.owner;

import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author osminin
 * @ created 21.12.2012
 * @ $Author$
 * @ $Revision$
 * ���������� � ����������
 */
public interface EmployeeInfo
{
	/**
	 * @return ������� �������������
	 */
	String getGuid();

	/**
	 * @return ����� ����������
	 */
	String getLogin();

	/**
	 * @return ��� ����������
	 */
	PersonName getPersonName();

	/**
	 * @return ���� ����������
	 */
	Office getEmployeeOffice();
}
