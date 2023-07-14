package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.*;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 13.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class TransactionImpl implements AccountTransaction, CardOperation, DepositTransaction
{
	private String counteragent;
	private String counteragentAccount;
	private String counteragentBank;
	private String counteragentBankName;
	private String bookAccount;
	private String counteragentCorAccount;
	private String operationCode;
	private Calendar operationDate;
	private Calendar date;
	private Card operationCard;
	private Money accountDebitSum;
	private Money accountCreditSum;
	private Money debitSum;
	private Money creditSum;
	private Money balance;
	private String description;
	private String documentNumber;
	private String shopInfo;
	private String recipient;
	private CardUseData cardUseData;

	public String getCounteragent()
	{
		return counteragent;
	}

	public String getCounteragentAccount()
	{
		return counteragentAccount;
	}

	public String getCounteragentBank()
	{
		return counteragentBank;
	}

	public String getCounteragentBankName()
	{
		return counteragentBankName;
	}

	public String getBookAccount()
	{
		return bookAccount;
	}

	public String getCounteragentCorAccount()
	{
		return counteragentCorAccount;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public Calendar getDate()
	{
		return date;
	}

	public Card getOperationCard()
	{
		return operationCard;
	}

	public Money getAccountDebitSum()
	{
		return accountDebitSum;
	}

	public Money getAccountCreditSum()
	{
		return accountCreditSum;
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

	public CardUseData getCardUseData()
	{
		return cardUseData;
	}

	public String getShopInfo()
	{
		return shopInfo;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setShopInfo(String shopInfo)
	{
		this.shopInfo = shopInfo;
	}

	public void setCounteragent(String counteragent)
	{
		this.counteragent = counteragent;
	}

	public void setCounteragentAccount(String counteragentAccount)
	{
		this.counteragentAccount = counteragentAccount;
	}

	public void setCounteragentBank(String counteragentBank)
	{
		this.counteragentBank = counteragentBank;
	}

	public void setCounteragentBankName(String counteragentBankName)
	{
		this.counteragentBankName = counteragentBankName;
	}

	public void setBookAccount(String bookAccount)
	{
		this.bookAccount = bookAccount;
	}

	public void setCounteragentCorAccount(String counteragentCorAccount)
	{
		this.counteragentCorAccount = counteragentCorAccount;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public void setOperationCard(Card operationCard)
	{
		this.operationCard = operationCard;
	}

	public void setAccountDebitSum(Money accountDebitSum)
	{
		this.accountDebitSum = accountDebitSum;
	}

	public void setAccountCreditSum(Money accountCreditSum)
	{
		this.accountCreditSum = accountCreditSum;
	}

	public void setDebitSum(Money debitSum)
	{
		this.debitSum = debitSum;
	}

	public void setCreditSum(Money creditSum)
	{
		this.creditSum = creditSum;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public void setCardUseData(CardUseData cardUseData)
	{
		this.cardUseData = cardUseData;
	}
}
