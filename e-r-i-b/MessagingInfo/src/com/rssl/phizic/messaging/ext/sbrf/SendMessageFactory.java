package com.rssl.phizic.messaging.ext.sbrf;

import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.person.Person;

import java.util.Map;

/**
 * Интерфейс фабрики для создания методов отправки сообщений
 * @author basharin
 * @ created 02.10.13
 * @ $Author$
 * @ $Revision$
 */

public interface SendMessageFactory
{
	/**
	 * Создать метод для отправки сообщения по телефонам из анкеты
	 * @param person - клиент
	 * @param phone - номер телефона
	 * @param translit - сообщение в транслите
	 * @param onlyMobileBank - способ отправки "Только Мобильный Банк"
	 * @param message - сообщение
	 * @return
	 */
	SendMessageMethod createSendByClientAllInfoCardMethod(Person person, String phone, TranslitMode translit, boolean onlyMobileBank, IKFLMessage message);

	/**
	 * Создать метод для отправки сообщения по указанному номеру телефона
	 * @param person - клиент
	 * @param phone - номер телефона
	 * @param translit - сообщение в транслите
	 * @param message - сообщение
	 * @return
	 */
	SendMessageMethod createSendByPhoneMethod(Person person, String phone, TranslitMode translit, IKFLMessage message);

	/**
	 * Создать метод для отправки сообщения по телефонам из мобильного банка
	 * @param person - клиент
	 * @param translit - сообщение в транслите
	 * @param message - сообщение
     * @param useAlternativeRegistrations - способ получения телефонов клиента
	 * @return
	 */
	SendMessageMethod createSendByClientInfoCardsMethod(Person person, TranslitMode translit, IKFLMessage message, Boolean useAlternativeRegistrations);

	/**
	 * Создать общий метод для отправки сообщениий
	 * @param person - клиент
	 * @param phone - номер телефона
	 * @param translit - сообщение в транслите
	 * @param message - сообщение
	 * @return
	 */
	SendMessageMethod createSendGeneralMethod(Person person, String phone, TranslitMode translit, IKFLMessage message);

	/**
	 * Проводят ли методы отправки проверку IMSI
	 * @return
	 */
	boolean methodsCanDoAdditionalCheck();

	String getErrorInfoMessage(Map<String, SendMessageError> errorInfo, OperationType operationType);

	ConfirmType getConfirmType();

}
