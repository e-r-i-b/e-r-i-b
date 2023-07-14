package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.push.PushMessage;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� �������� ���������
 */
public interface MessagingService extends Serializable
{
	/**
	 * ���������� ��������� ��� SMS
	 * @param message - ���������
	 * @return ��������� ��������
	 */
	String sendSms(IKFLMessage message) throws IKFLMessagingException, IKFLMessagingLogicException;

	/**
	 * ���������� ��������� ����� Push
	 * @param message - ���������
	 * @return ��������� ��������
	 */
	String sendPush(PushMessage message) throws IKFLMessagingException, IKFLMessagingLogicException;


	/**
	 * ���������� ��������� � ����������� ����� ������������� ����� SMS
	 * @param message - ���������
	 * @return ��������� ��������
	 */
	String sendOTPSms(IKFLMessage message) throws IKFLMessagingException, IKFLMessagingLogicException;

	/**
	 * ���������� ��������� � ����������� ����� ������������� ����� Push
	 * @param message - ���������
	 * @return ��������� ��������
	 */
	String sendOTPPush(PushMessage message) throws IKFLMessagingException, IKFLMessagingLogicException;

}
