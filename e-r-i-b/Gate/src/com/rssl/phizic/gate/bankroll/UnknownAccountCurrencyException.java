package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Для счета не удалось определеить его валюту
 * @author Evgrafov
 * @ created 08.09.2006
 * @ $Author: Roshka $
 * @ $Revision: 2418 $
 */
public class UnknownAccountCurrencyException extends GateLogicException
{
	private String accountNumber;

	public UnknownAccountCurrencyException(String accountNumber)
	{
		super("Для счета № " + accountNumber + " не удалось определеить его валюту");
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}
}