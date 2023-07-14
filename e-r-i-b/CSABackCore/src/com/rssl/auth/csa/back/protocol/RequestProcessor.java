package com.rssl.auth.csa.back.protocol;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * ���������� �������
 */

public interface RequestProcessor
{
	/**
	 * ���������� ������ � ������� ���������
	 * @param requestInfo ���������� � �������
	 * @return ���������� �� ������
	 */
	ResponseInfo process(RequestInfo requestInfo) throws Exception;

	/**
	 * �������� �� ������ ��������� � ������ standIn ���
	 * @return true - ��������
	 */
	boolean isAccessStandIn();
}
