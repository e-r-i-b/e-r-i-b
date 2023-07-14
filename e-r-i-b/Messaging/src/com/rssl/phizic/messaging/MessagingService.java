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
 * Сервис для отправки сообщений
 */
public interface MessagingService extends Serializable
{
	/**
	 * Отправляет сообщение как SMS
	 * @param message - сообщение
	 * @return результат отправки
	 */
	String sendSms(IKFLMessage message) throws IKFLMessagingException, IKFLMessagingLogicException;

	/**
	 * Отправляет сообщение через Push
	 * @param message - сообщение
	 * @return результат отправки
	 */
	String sendPush(PushMessage message) throws IKFLMessagingException, IKFLMessagingLogicException;


	/**
	 * Отправляет сообщение с одноразовым кодом подтверждения через SMS
	 * @param message - сообщение
	 * @return результат отправки
	 */
	String sendOTPSms(IKFLMessage message) throws IKFLMessagingException, IKFLMessagingLogicException;

	/**
	 * Отправляет сообщение с одноразовым кодом подтверждения через Push
	 * @param message - сообщение
	 * @return результат отправки
	 */
	String sendOTPPush(PushMessage message) throws IKFLMessagingException, IKFLMessagingLogicException;

}
