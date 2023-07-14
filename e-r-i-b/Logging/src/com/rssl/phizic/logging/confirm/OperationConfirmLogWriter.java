package com.rssl.phizic.logging.confirm;

import java.io.Serializable;

/**
 * @author lukina
 * @ created 09.02.2012
 * @ $Author$
 * @ $Revision$
 */

public interface OperationConfirmLogWriter extends Serializable
{
	/**
	 * Зафиксировать факт успешной инциализации стратегии подтверждения по СМС
	 * @param recipient получатель(и)
	 * @param textToLog текст смс
	 * @param additionalCheck признак проверки имси
	 */
	void initializeSMSSuccess(String recipient, String textToLog, boolean additionalCheck);

	/**
	 * Зафиксировать факт НЕуспешной инциализации стратегии подтверждения по СМС
	 * @param recipient получатель(и)
	 * @param textToLog текст смс
	 * @param additionalCheck признак проверки имси
	 */
	void initializeSMSFailed(String recipient, String textToLog, boolean additionalCheck);

	/**
	 * Зафиксировать факт успешной инциализации стратегии подтверждения через PUSH
	 * @param recipient получатель(и)
	 * @param textToLog текст сообщения
	 */
	void initializePUSHSuccess(String recipient, String textToLog);

	/**
	 * Зафиксировать факт НЕуспешной инциализации стратегии подтверждения через PUSH
	 * @param recipient получатель(и)
	 * @param textToLog текст сообщения
	 */
	void initializePUSHFailed(String recipient, String textToLog);

	/**
	 * Зафиксировать факт успешной инциализации стратегии подтверждения через чековые пароли
	 * @param cardNumber номер чекового пароля
	 * @param passwordNumber номер пароля в чеке
	 */
	void initializeCardSuccess(String cardNumber, String passwordNumber);

	/**
	 * Зафиксировать факт НЕуспешной инциализации стратегии подтверждения через чековые пароли
	 * @param userId логин Ipas, для которого получались чековые пароли
	 */
	void initializeCardFailed(String userId);

	/**
	 * Зафиксировать факт успешной инциализации стратегии подтверждения через САР
	 * @param cardNumber номер карты
	 */
	void initializeCAPSuccess(String cardNumber);

	/**
	 * Зафиксировать факт НЕуспешной инциализации стратегии подтверждения через чековые пароли
	 */
	void initializeCAPFailed();

	/**
	 * Зафиксировать факт успешной попытки подтверждения
	 * @param confirmType тип стратегии подтверждения(SMS/PUSH/CARD/CAP)
	 * @param confirmCode введенный клиентом код
	 */
	void confirmSuccess(ConfirmType confirmType, String confirmCode);

	/**
	 * Зафиксировать факт неуспешной попытки подтверждения
	 * @param confirmType тип стратегии подтверждения(SMS/PUSH/CARD/CAP)
	 * @param confirmCode введенный клиентом код
	 */
	void confirmFailed(ConfirmType confirmType, String confirmCode);

	/**
	 * Зафиксировать факт неуспешной попытки подтверждения по причине "протухания" кода подтверждения
	 * @param confirmType тип стратегии подтверждения(SMS/PUSH/CARD/CAP)
	 * @param confirmCode введенный клиентом код
	 */
	void confirmTimeout(ConfirmType confirmType, String confirmCode);
}