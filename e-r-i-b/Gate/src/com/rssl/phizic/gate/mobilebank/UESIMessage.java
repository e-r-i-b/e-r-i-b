package com.rssl.phizic.gate.mobilebank;

import java.util.Calendar;

/**
 * ��������� ���������������� ���������� ���
 * @author Pankin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public interface UESIMessage
{
	/**
	 * @return ������������� ���������
	 */
	Long getMessageId();

	/**
	 * @return ���� ���������
	 */
	Calendar getMessageTime();

	/**
	 * @return ��� ���������
	 */
	String getMessageType();

	/**
	 * @return ����� ���������
	 */
	String getMessageText();
}
