package com.rssl.phizic.web.client.component;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.web.component.WidgetForm;

/**
 @author: Egorovaa
 @ created: 04.07.2012
 @ $Author$
 @ $Revision$
 */
public class MobileBalanceWidgetForm extends WidgetForm
{
	private String phoneNumber;
	private String password;
	private Money balance;
	private String provider;
	private String paymentUrl;
	private Boolean isLowBalance = false;

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

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

	public String getPaymentUrl()
	{
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl)
	{
		this.paymentUrl = paymentUrl;
	}

	public Boolean getLowBalance()
	{
		return isLowBalance;
	}

	public void setLowBalance(Boolean lowBalance)
	{
		isLowBalance = lowBalance;
	}
}
