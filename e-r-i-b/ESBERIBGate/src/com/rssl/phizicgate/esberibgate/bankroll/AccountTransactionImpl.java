package com.rssl.phizicgate.esberibgate.bankroll;

import com.rssl.phizic.gate.bankroll.AccountTransaction;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.CardUseData;

import java.util.Calendar;

/**
 * @ author: filimonova
 * @ created: 22.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountTransactionImpl implements AccountTransaction
{
	private Money balance;
	private String counteragent;
	private String counteragentAccount;
	private String counteragentBank;
	private Money creditSum;
	private Calendar date;
	private Money debitSum;
	private String description;
	private String counteragentCorAccount;
	private String bookAccount;        // ����� ����a ����������
	private String counteragentBankName; // ���� ��������������, ������������
	private String operationCode;
	private String documentNumber;
	private String recipient;

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public String getCounteragent()
	{
		return counteragent;
	}

	public void setCounteragent(String counteragent)
	{
		this.counteragent = counteragent;
	}

	public String getCounteragentAccount()
	{
		return counteragentAccount;
	}

	public void setCounteragentAccount(String counteragentAccount)
	{
		this.counteragentAccount = counteragentAccount;
	}

	public String getBookAccount()
	{
		return bookAccount;
	}

	void setBookAccount(String bookAccount)
	{
		this.bookAccount = bookAccount;
	}

	public String getCounteragentBank()
	{
		return counteragentBank;
	}

	public void setCounteragentBank(String counteragentBank)
	{
		this.counteragentBank = counteragentBank;
	}

	public String getCounteragentBankName()
	{
		return counteragentBankName;
	}

	void setCounteragentBankName(String counteragentBankName)
	{
		this.counteragentBankName = counteragentBankName;
	}

	public Money getCreditSum()
	{
		return creditSum;
	}

	public void setCreditSum(Money creditSum)
	{
		this.creditSum = creditSum;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public Money getDebitSum()
	{
		return debitSum;
	}

	public void setDebitSum(Money debitSum)
	{
		this.debitSum = debitSum;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getCounteragentCorAccount()
	{
		return counteragentCorAccount;
	}

	public void setCounteragentCorAccount(String counteragentCorAccount)
	{
		this.counteragentCorAccount = counteragentCorAccount;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public CardUseData getCardUseData()
	{
		return null;
	}

}
