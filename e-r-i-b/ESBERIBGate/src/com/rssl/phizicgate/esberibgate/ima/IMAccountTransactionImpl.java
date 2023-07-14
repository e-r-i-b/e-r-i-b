package com.rssl.phizicgate.esberibgate.ima;

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
	private String   documentNumber;
	private String   description;
	private String   operationCode;
	private String   operationNumber;
	private String   correspondentAccount;
	private String   recipient;

	private Money    debitSumInPhizicalForm;
	private Money    creditSumInPhizicalForm;
	private Money    balanceInPhizicalForm;
	private Money    debitSum;
	private Money    creditSum;
	private Money    balance;
	private Calendar date;
	private IMAOperationType operationType;
	private Money operationRurSumm;

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public String getOperationNumber()
	{
		return operationNumber;
	}

	public void setOperationNumber(String operationNumber)
	{
		this.operationNumber = operationNumber;
	}

	public String getCorrespondentAccount()
	{
		return correspondentAccount;
	}

	public void setCorrespondentAccount(String correspondentAccount)
	{
		this.correspondentAccount = correspondentAccount;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public Money getDebitSumInPhizicalForm()
	{
		return debitSumInPhizicalForm;
	}

	public void setDebitSumInPhizicalForm(Money debitSumInPhizicalForm)
	{
		this.debitSumInPhizicalForm = debitSumInPhizicalForm;
	}

	public Money getCreditSumInPhizicalForm()
	{
		return creditSumInPhizicalForm;
	}

	public void setCreditSumInPhizicalForm(Money creditSumInPhizicalForm)
	{
		this.creditSumInPhizicalForm = creditSumInPhizicalForm;
	}

	public Money getBalanceInPhizicalForm()
	{
		return balanceInPhizicalForm;
	}

	public void setBalanceInPhizicalForm(Money balanceInPhizicalForm)
	{
		this.balanceInPhizicalForm = balanceInPhizicalForm;
	}

	public Money getDebitSum()
	{
		return debitSum;
	}

	public void setDebitSum(Money debitSum)
	{
		this.debitSum = debitSum;
	}

	public Money getCreditSum()
	{
		return creditSum;
	}

	public void setCreditSum(Money creditSum)
	{
		this.creditSum = creditSum;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
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