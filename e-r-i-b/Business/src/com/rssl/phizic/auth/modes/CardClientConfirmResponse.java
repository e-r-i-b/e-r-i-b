package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.config.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * ������������ ����� ������� �� �������������.
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
		formNameToDescription.put("CloseAutoSubscriptionPayment", "������������� �������� �������� �������� ����� pos���������");
		formNameToDescription.put("DelayAutoSubscriptionPayment", "������������� �������� ������������ �������� ����� pos���������");
		formNameToDescription.put("RecoveryAutoSubscriptionPayment", "������������� �������� �������������� ���������� �������� ����� pos���������");
		formNameToDescription.put("EditAutoSubscriptionPayment", "������������� �������� �������������� �������� ����� pos���������");
		formNameToDescription.put("RurPayJurSB", "������������� �������� �������� �������� ����� pos���������");
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
	 * ����� �����.
	 */
	private final String cardNumber;
	/**
	 * ��� �����.
	 */
	private final String cardType;
	/**
	 * ����� ���������, ����� ������� ������������ ��������.
	 */
	private final String terminalNumber;
	/**
	 * ���� ����������.
	 */
	private final String transactionDate;
	/**
	 * ����� ����������.
	 */
	private final String transactionTime;
	/**
	 * ��������� ��� ����� ������������ �� ���� �����.
	 */
	private final String formDescription;
	/**
	 * ����� �������.
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
