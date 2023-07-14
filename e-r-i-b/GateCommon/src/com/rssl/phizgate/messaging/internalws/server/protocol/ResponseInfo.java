package com.rssl.phizgate.messaging.internalws.server.protocol;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * ���������� �� ������.
 */

public interface ResponseInfo
{
	/**
	 * @return ��� ���������
	 */
	String getType();

	/**
	 * @return ������������ ���������
	 */
	String getUID();

	/**
	 * @return ���� ������������ ���������
	 */
	Calendar getDate();

	/**
	 * @return ��� ������
	 */
	int getErrorCode();

	/**
	 * @return �������� ������
	 */
	String getErrorDescription();

	/**
	 * @return ��������� ������������� ������.
	 */
	String asString();
}
