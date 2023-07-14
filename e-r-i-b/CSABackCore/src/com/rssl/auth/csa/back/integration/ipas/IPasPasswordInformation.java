package com.rssl.auth.csa.back.integration.ipas;

/**
 * @author akrenev
 * @ created 07.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Информация о подтверждении одноразовым паролем
 */

public class IPasPasswordInformation
{
	private String SID;
	private String passwordNo;
	private String receiptNo;
	private Integer passwordsLeft;
	private Integer lastAtempts;

	/**
	 * конструктор (для гибернета)
	 */
	public IPasPasswordInformation()
	{}

	/**
	 * конструктор
	 * @param SID сид
	 * @param passwordNo номер пароля
	 * @param receiptNo номер карты
	 * @param passwordsLeft паролей осталось
	 */
	public IPasPasswordInformation(String SID, String passwordNo, String receiptNo, Integer passwordsLeft)
	{
		this.SID = SID;
		this.passwordNo = passwordNo;
		this.receiptNo = receiptNo;
		this.passwordsLeft = passwordsLeft;
	}

	/**
	 * @return сид
	 */
	public String getSID()
	{
		return SID;
	}

	/**
	 * задать сид (для гибернета)
	 * @param SID сид
	 */
	public void setSID(String SID)
	{
		this.SID = SID;
	}

	/**
	 * @return номер пароля на чеке
	 */
	public String getPasswordNo()
	{
		return passwordNo;
	}

	/**
	 * задать номер пароля на чеке (для гибернета)
	 * @param passwordNo номер пароля на чеке
	 */
	public void setPasswordNo(String passwordNo)
	{
		this.passwordNo = passwordNo;
	}

	/**
	 * @return номер чека
	 */
	public String getReceiptNo()
	{
		return receiptNo;
	}

	/**
	 * задать номер чека (для гибернета)
	 * @param receiptNo номер чека
	 */
	public void setReceiptNo(String receiptNo)
	{
		this.receiptNo = receiptNo;
	}

	/**
	 * @return оставшееся количество паролей на чеке
	 */
	public Integer getPasswordsLeft()
	{
		return passwordsLeft;
	}

	/**
	 * задать оставшееся количество паролей на чеке (для гибернета)
	 * @param passwordsLeft оставшееся количество паролей на чеке
	 */
	public void setPasswordsLeft(Integer passwordsLeft)
	{
		this.passwordsLeft = passwordsLeft;
	}

	/**
	 * @return оставшееся количество попыток (приходит только в случае ошибочного ответа)
	 */
	public Integer getLastAtempts()
	{
		return lastAtempts;
	}

	/**
	 * задать оставшееся количество попыток
	 * @param lastAtempts оставшееся количество попыток
	 */
	public void setLastAtempts(Integer lastAtempts)
	{
		this.lastAtempts = lastAtempts;
	}
}

