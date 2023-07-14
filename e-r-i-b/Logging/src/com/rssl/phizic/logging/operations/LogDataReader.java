package com.rssl.phizic.logging.operations;

import java.util.LinkedHashMap;

/**
 * @author Evgrafov
 * @ created 15.03.2006
 * @ $Author: lukina $
 * @ $Revision: 25612 $
 */

public interface LogDataReader
{
	/**
	 * ��������� ���������� ��������
	 */
	enum ResultType
	{
		SUCCESS,
		SYSTEM_ERROR,
		CLIENT_ERROR
	}

	/**
	 * "����(������)", �� �������� ������������ ��������
	 * �������� ��� ���� - URL, ��� SMS-�������� - ��� �������. 
	 * @return
	 */
	String getOperationPath();

	/**
	 * @deprecated ������ ��� ����������� CHG016701 	���� ���������� �����
	 * @return
	 */
	@Deprecated String getOperationKey();

	/**
	 * @return �������� ���������� ������
	 */
	String getDescription();

	/**
	 * @return ���� ���������� ������
	 */
	String getKey();

	/**
	 *
	 * @return ��������� ���������
	 * @throws Exception
	 */
	LinkedHashMap readParameters() throws Exception;

	/**
	 * @return ��� ���������� ��������
	 */
	ResultType getResultType();
}
