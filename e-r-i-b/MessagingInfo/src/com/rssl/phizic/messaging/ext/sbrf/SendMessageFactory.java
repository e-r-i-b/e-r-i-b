package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.person.Person;

import java.util.Map;

/**
 * ��������� ������� ��� �������� ������� �������� ���������
 * @author basharin
 * @ created 02.10.13
 * @ $Author$
 * @ $Revision$
 */

public interface SendMessageFactory
{
	/**
	 * ������� ����� ��� �������� ��������� �� ��������� �� ������
	 * @param person - ������
	 * @param phone - ����� ��������
	 * @param translit - ��������� � ���������
	 * @param onlyMobileBank - ������ �������� "������ ��������� ����"
	 * @param message - ���������
	 * @return
	 */
	SendMessageMethod createSendByClientAllInfoCardMethod(Person person, String phone, TranslitMode translit, boolean onlyMobileBank, IKFLMessage message);

	/**
	 * ������� ����� ��� �������� ��������� �� ���������� ������ ��������
	 * @param person - ������
	 * @param phone - ����� ��������
	 * @param translit - ��������� � ���������
	 * @param message - ���������
	 * @return
	 */
	SendMessageMethod createSendByPhoneMethod(Person person, String phone, TranslitMode translit, IKFLMessage message);

	/**
	 * ������� ����� ��� �������� ��������� �� ��������� �� ���������� �����
	 * @param person - ������
	 * @param translit - ��������� � ���������
	 * @param message - ���������
     * @param useAlternativeRegistrations - ������ ��������� ��������� �������
	 * @return
	 */
	SendMessageMethod createSendByClientInfoCardsMethod(Person person, TranslitMode translit, IKFLMessage message, Boolean useAlternativeRegistrations);

	/**
	 * ������� ����� ����� ��� �������� ����������
	 * @param person - ������
	 * @param phone - ����� ��������
	 * @param translit - ��������� � ���������
	 * @param message - ���������
	 * @return
	 */
	SendMessageMethod createSendGeneralMethod(Person person, String phone, TranslitMode translit, IKFLMessage message);

	/**
	 * �������� �� ������ �������� �������� IMSI
	 * @return
	 */
	boolean methodsCanDoAdditionalCheck();

	String getErrorInfoMessage(Map<String, SendMessageError> errorInfo, OperationType operationType);

	ConfirmType getConfirmType();

}
