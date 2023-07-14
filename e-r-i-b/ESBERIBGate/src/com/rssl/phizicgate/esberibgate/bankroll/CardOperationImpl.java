package com.rssl.phizicgate.esberibgate.bankroll;

import com.rssl.phizic.gate.bankroll.CardOperation;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Pakhomova
 * @ created 15.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardOperationImpl implements CardOperation, Serializable
{
	private String   documentNumber;               // Идентификатор транзакции
	private Calendar operationDate;               // Дата операции

	private Money debitSum;                       // Сумма расхода
    private Money    creditSum;                   // Сумма прихода
	private Money    accountDebitSum;             // Сумма расхода
    private Money    accountCreditSum;            // Сумма прихода

	private Calendar date;                        // Дата-время списания средств

	private String   description;                 // Описание операции
	private Card operationCard;                   // Карта, по которой совершена операция
	private Money balance;                        // баланс
	private String shopInfo;                       //Информация о торговой точке
	private String recipient;                      // Наименование получателя/плательщика

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public Card getOperationCard()
	{
		return operationCard;
	}

	public void setOperationCard(Card operationCard)
	{
		this.operationCard = operationCard;
	}

	public Money getAccountDebitSum()
	{
		return accountDebitSum;
	}

	public void setAccountDebitSum(Money accountDebitSum)
	{
		this.accountDebitSum = accountDebitSum;
	}

	public Money getAccountCreditSum()
	{
		return accountCreditSum;
	}

	public void setAccountCreditSum(Money accountCreditSum)
	{
		this.accountCreditSum = accountCreditSum;
	}

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getShopInfo()
	{
		return shopInfo;
	}

	public void setShopInfo(String shopInfo)
	{
		this.shopInfo = shopInfo;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}
}