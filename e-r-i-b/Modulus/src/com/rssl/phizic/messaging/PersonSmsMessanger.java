package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.TextMessage;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ ���-��������� �������
 */
public interface PersonSmsMessanger
{
	/**
	 * ���������� ��� �������
	 * @param message - ����� ���
	 */
	void sendSms(TextMessage message);

	/**
	 * �������� ��� ������� �� ������ ��������
	 * @param message - ����� ���
	 * @param phoneNumber - ����� ��������
	 */
	void sendSms(TextMessage message, String phoneNumber);

	/**
	 * @return ����� �� ���������, �� ������� ������������ ���������
	 */
	String getDefaultPhone();
}
