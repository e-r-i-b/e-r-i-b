package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.config.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработанный ответ запроса на подтверждение.
 *
 * @author basharin
 * @ created 23.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class CardClientConfirmResponse implements ConfirmResponse
{
	private static final Map<String, String> formNameToDescription = new HashMap<String, String>();

	static
	{
		formNameToDescription.put("CloseAutoSubscriptionPayment", "Подтверждение клиентом закрытия подписки через pos–терминал");
		formNameToDescription.put("DelayAutoSubscriptionPayment", "Подтверждение клиентом приостановки подписки через pos–терминал");
		formNameToDescription.put("RecoveryAutoSubscriptionPayment", "Подтверждение клиентом восстановления исполнения подписки через pos–терминал");
		formNameToDescription.put("EditAutoSubscriptionPayment", "Подтверждение клиентом редактирования подписки через pos–терминал");
		formNameToDescription.put("RurPayJurSB", "Подтверждение клиентом создания подписки через pos–терминал");
	}

	public CardClientConfirmResponse(Map<String, Object> map)
	{
		cardNumber = (String) map.get(Constants.CONFIRM_PLASTIC_CARD_NUMBER_FIELD);
		cardType = (String) map.get(Constants.CONFIRM_PLASTIC_CARD_TYPE_FIELD);
		transactionDate = (String) map.get(Constants.CONFIRM_PLASTIC_TRANSACTION_DATE_FIELD);
		transactionTime = (String) map.get(Constants.CONFIRM_PLASTIC_TRANSACTION_TIME_FIELD);
		terminalNumber = (String) map.get(Constants.CONFIRM_PLASTIC_TERMINAL_NUMBER_FIELD);
		employeeLogin = (String) map.get(Constants.CONFIRM_PLASTIC_EMPLOYEE_LOGIN_FIELD);
		formDescription = formNameToDescription.get(map.get(Constants.CONFIRM_PLASTIC_FORM_NAME_FIELD));
	}

	/**
	 * Номер карты.
	 */
	private final String cardNumber;
	/**
	 * Тип карты.
	 */
	private final String cardType;
	/**
	 * Номер терминала, через который производится операция.
	 */
	private final String terminalNumber;
	/**
	 * Дата транзакции.
	 */
	private final String transactionDate;
	/**
	 * Время транзакции.
	 */
	private final String transactionTime;
	/**
	 * Сообщение для логов завязывается на типе формы.
	 */
	private final String formDescription;
	/**
	 * Логин клиента.
	 */
	private final String employeeLogin;

	public String getCardType()
	{
		return cardType;
	}

	public String getTerminalNumber()
	{
		return terminalNumber;
	}

	public String getTransactionDate()
	{
		return transactionDate;
	}

	public String getTransactionTime()
	{
		return transactionTime;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public String getEmployeeLogin()
	{
		return employeeLogin;
	}

	public String getFormDescription()
	{
		return formDescription;
	}
}
