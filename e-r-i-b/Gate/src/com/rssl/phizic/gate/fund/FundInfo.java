package com.rssl.phizic.gate.fund;

import com.rssl.phizic.gate.clients.GUID;

/**
 * @author osminin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ����� �������
 */
public interface FundInfo
{
	/**
	 * @return ������ �� ����� �������
	 */
	Request getRequest();

	/**
	 * @return ��� ��� �� �� ����������
	 */
	GUID getInitiatorGuid();

	/**
	 * @return ��� ��� �� �� �����������
	 */
	GUID getSenderGuid();

	/**
	 * @return ������� ������������� ������ � ����� ����������
	 */
	String getExternalResponseId();

	/**
	 * @return �������� ��������� ����� �����������
	 */
	String getInitiatorPhones();
}
