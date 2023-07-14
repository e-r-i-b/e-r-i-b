package com.rssl.phizic.business.web;

/**
 @author: Egorovaa
 @ created: 04.07.2012
 @ $Author$
 @ $Revision$
 */
public class MobileBalanceWidget extends Widget
{
	/**
	 * Оператор связи
	 */
	private String provider;
	/**
	 * Номер телефона
	 */
	private String phoneNumber;
	/**
	 * Пароль для доступа в личный кабинет оператора
	 */
	private String password;
	/**
	 * Пороговое значение. Если сумма на счете ниже этого числа, виджет меняет цвет
	 */
	private String thresholdValue;
	/**
	 * Сумма на счете
	 */
	private transient String balance;
	/**
	 * Дата и время последнего получения данных о балансе
	 */
	private transient Long lastUpdateDate;

	///////////////////////////////////////////////////////////////////////////

	public String getProvider()
	{
		return provider;
	}

	public void setProvider(String provider)
	{
		this.provider = provider;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getThresholdValue()
	{
		return thresholdValue;
	}

	public void setThresholdValue(String thresholdValue)
	{
		this.thresholdValue = thresholdValue;
	}

	public String getBalance()
	{
		return balance;
	}

	public void setBalance(String balance)
	{
		this.balance = balance;
	}

	public Long getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Long lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	protected Widget clone()
	{
		return super.clone();
	}
}
