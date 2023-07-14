package com.rssl.phizgate.messaging.internalws.server.protocol;

import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * ���������� � �������
 */

public interface RequestInfo
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
	 * @return ������ ���������
	 */
	String getVersion();

	/**
	 * @return �������� ���������(��� �����������)
	 */
	String getSource();

	/**
	 * @return IP-����� �������, � ��������� �������� ����������� ������
	 */
	String getIP();

	/**
	 * @return ���� ��������
	 */
	Document getBody();
}
