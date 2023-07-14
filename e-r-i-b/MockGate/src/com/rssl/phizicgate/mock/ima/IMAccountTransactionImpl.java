package com.rssl.phizicgate.mock.ima;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.ima.IMAOperationType;
import com.rssl.phizic.gate.ima.IMAccountTransaction;

import java.util.Calendar;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountTransactionImpl implements IMAccountTransaction
{
	public String   documentNumber;
	public String   description;
	public String   operationCode;
	public String   operationNumber;
	public String   correspondentAccount;
	public Money    debitSumInPhizicalForm;
	public Money    creditSumInPhizicalForm;
	public Money    balanceInPhizicalForm;
	public Money    debitSum;
	public Money    creditSum;
	public Money    balance;
	public Calendar date;
	private IMAOperationType operationType;
	private Money operationRurSumm;

	public IMAccountTransactionImpl(
			String documentNumber,
			String description,
			String operationCode,
			String operationNumber,
			String correspondentAccount,
			Money debitSumInPhizicalForm,
			Money creditSumInPhizicalForm,
			Money balanceInPhizicalForm,
			Money debitSum,
			Money creditSum,
			Money balance,
			Calendar date)
	{
		this.documentNumber = documentNumber;
		this.description = description;
		this.operationCode = operationCode;
		this.operationNumber = operationNumber;
		this.correspondentAccount = correspondentAccount;
		this.debitSumInPhizicalForm = debitSumInPhizicalForm;
		this.creditSumInPhizicalForm = creditSumInPhizicalForm;
		this.balanceInPhizicalForm = balanceInPhizicalForm;
		this.debitSum = debitSum;
		this.creditSum = creditSum;
		this.balance = balance;
		this.date = date;
	}

	public String getCorrespondentAccount()
	{
		return correspondentAccount;
	}

	public Money getDebitSumInPhizicalForm()
	{
		return debitSumInPhizicalForm;
	}

	public Money getCreditSumInPhizicalForm()
	{
		return creditSumInPhizicalForm;
	}

	public Money getBalanceInPhizicalForm()
	{
		return balanceInPhizicalForm;
	}

	public String getOperationNumber()
	{
		return operationNumber;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	public Calendar getDate()
	{
		return date;
	}

	public Money getDebitSum()
	{
		return debitSum;
	}

	public Money getCreditSum()
	{
		return creditSum;
	}

	public Money getBalance()
	{
		return balance;
	}

	public String getDescription()
	{
		return description;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public String getRecipient()
	{
		return null; 
	}

	public IMAOperationType getOperationType()
	{
		return operationType;
	}

	public void setOperationType(IMAOperationType operationType)
	{
		this.operationType = operationType;
	}

	public Money getOperationRurSumm()
	{
		return operationRurSumm;
	}

	public void setOperationRurSumm(Money operationRurSumm)
	{
		this.operationRurSumm = operationRurSumm;
	}
}
