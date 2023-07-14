package com.rssl.phizicgate.ips;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.ips.IPSCardOperation;
import com.rssl.phizic.utils.DateHelper;
import static com.rssl.phizic.utils.MoneyHelper.formatMoney;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */
class IPSCardOperationImpl implements IPSCardOperation
{
	private String documentNumber;

	private Card operationCard;

	private Calendar date;

	private Calendar operationDate;

	private Money debitSum;

	private Money creditSum;

	private Money accountDebitSum;

	private Money accountCreditSum;

	private Money balance = null; // баланс нам не известен

	private String recipient = ""; // получатель (контрагент) нам не известен

	private String description;

	private String shopInfo; // здесь передаётся device-number

	private Client owner;

	private long mccCode;

	private boolean cash;

	private String authCode;

	///////////////////////////////////////////////////////////////////////////

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public Card getOperationCard()
	{
		return operationCard;
	}

	void setOperationCard(Card operationCard)
	{
		this.operationCard = operationCard;
	}

	public Calendar getDate()
	{
		return date;
	}

	void setDate(Calendar date)
	{
		this.date = date;
	}

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public Money getDebitSum()
	{
		return debitSum;
	}

	void setDebitSum(Money debitSum)
	{
		this.debitSum = debitSum;
	}

	public Money getCreditSum()
	{
		return creditSum;
	}

	void setCreditSum(Money creditSum)
	{
		this.creditSum = creditSum;
	}

	public Money getAccountDebitSum()
	{
		return accountDebitSum;
	}

	void setAccountDebitSum(Money accountDebitSum)
	{
		this.accountDebitSum = accountDebitSum;
	}

	public Money getAccountCreditSum()
	{
		return accountCreditSum;
	}

	void setAccountCreditSum(Money accountCreditSum)
	{
		this.accountCreditSum = accountCreditSum;
	}

	public Money getBalance()
	{
		return balance;
	}

	void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public String getDescription()
	{
		return description;
	}

	void setDescription(String description)
	{
		this.description = description;
	}

	public String getShopInfo()
	{
		return shopInfo;
	}

	void setShopInfo(String shopInfo)
	{
		this.shopInfo = shopInfo;
	}

	public String getRecipient()
	{
		return recipient;
	}

	void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public Client getOwner()
	{
		return owner;
	}

	void setOwner(Client owner)
	{
		this.owner = owner;
	}

	public long getMccCode()
	{
		return mccCode;
	}

	void setMccCode(long mccCode)
	{
		this.mccCode = mccCode;
	}

	public boolean isCash()
	{
		return cash;
	}

	void setCash(boolean cash)
	{
		this.cash = cash;
	}

	public String getAuthCode()
	{
		return authCode;
	}

	void setAuthCode(String authCode)
	{
		this.authCode = authCode;
	}

	public String toString()
	{
		return "IPSCardOperationImpl{" +
				"docNumber='" + documentNumber + '\'' +
				", card=" + hideCardNumber(operationCard) +
				", date=" + DateHelper.toISO8601DateFormat(date) +
				", debit=" + formatMoney(debitSum) +
				", credit=" + formatMoney(creditSum) +
				", accDebit=" + formatMoney(accountDebitSum) +
				", accCredit=" + formatMoney(accountCreditSum) +
				", cash=" + cash +
				", mcc=" + mccCode +
				", desc='" + description + '\'' +
				", shopInfo='" + shopInfo + '\'' +
				'}';
	}
}
