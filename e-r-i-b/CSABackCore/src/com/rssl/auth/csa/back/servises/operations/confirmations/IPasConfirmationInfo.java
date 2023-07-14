package com.rssl.auth.csa.back.servises.operations.confirmations;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * детали подтверждения чековым паролем
 */

public class IPasConfirmationInfo implements ConfirmationInfo
{
	private String passwordNo;
	private String receiptNo;
	private Integer passwordsLeft;
	private Integer lastAtempts;

	/**
	 * конструктор
	 * @param passwordNo номер пароля
	 * @param receiptNo номер карты
	 * @param passwordsLeft паролей осталось
	 * @param lastAtempts оставшееся количество попыток
	 */
	public IPasConfirmationInfo(String passwordNo, String receiptNo, Integer passwordsLeft, Integer lastAtempts)
	{
		this.passwordNo = passwordNo;
		this.receiptNo = receiptNo;
		this.passwordsLeft = passwordsLeft;
		this.lastAtempts = lastAtempts;
	}

	/**
	 * @return номер пароля на чеке
	 */
	public String getPasswordNo()
	{
		return passwordNo;
	}

	/**
	 * @return номер чека
	 */
	public String getReceiptNo()
	{
		return receiptNo;
	}

	/**
	 * @return оставшееся количество паролей на чеке
	 */
	public Integer getPasswordsLeft()
	{
		return passwordsLeft;
	}

	/**
	 * @return оставшееся количество попыток (приходит только в случае ошибочного ответа)
	 */
	public Integer getLastAtempts()
	{
		return lastAtempts;
	}
}
