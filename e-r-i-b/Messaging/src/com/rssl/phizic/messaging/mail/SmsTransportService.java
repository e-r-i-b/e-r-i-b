package com.rssl.phizic.messaging.mail;

import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.TranslitMode;

import java.util.Map;

/**
 * @author Erkin
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */
public interface SmsTransportService
{
	/**
	 * �� ������� toPhone �������� ��������� text
	 * @param toPhone
	 * @param text
	 * @param textToLog
	 * @throws IKFLMessagingException
	 */
	void sendSms(String toPhone, String text, String textToLog, Long priority) throws IKFLMessagingException;

	/**
	 * �������� ��������� ���������� ��������� �� �������
	 * @param toPhone - ����� ��������
	 * @param translit - ����� ��������������
	 * @param text - ����� ���������
	 * @param textToLog - ����� ��� �����������
	 * @throws IKFLMessagingException
	 */
	void sendSms(String toPhone, TranslitMode translit, String text,String textToLog, Long priority) throws IKFLMessagingException;

	/**
	 * �������� ��� � ��������� IMSI �� ������ toPhones
	 * @param messageInfo ���������� � ���������
	 * @param mbSystemId ID ���� � �� "��������� ����"
	 * @param toPhones ��������
	 * @return ��� (����� �������� - ������)
	 */
	Map<String, SendMessageError> sendSmsWithIMSICheck(MessageInfo messageInfo, Long priority, String... toPhones) throws IKFLMessagingException;

	/**
	 * ��������� ���-���������, ������ ���� ��������� ��������� �� ����
	 * @return ���������� SmsMessage
	 * @throws IKFLMessagingException
	 */
	SmsMessage receive() throws IKFLMessagingException;
}
