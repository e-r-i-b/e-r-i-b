package com.rssl.phizicgate.rsretailV6r20.bankroll;

import com.rssl.phizic.gate.bankroll.CardOperation;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Kidyaev
 * @ created 18.10.2005
 * @ $Author: danilov $
 * @ $Revision$
 */

public class CardOperationImpl implements CardOperation, Serializable
{
	private String   operationId;                    // Идентификатор транзакции
	private Calendar operationDate;                  // Дата операции

	private Money    debitSum;                       // Сумма расхода
    private Money    creditSum;                      // Сумма прихода
	private Money    accountDebitSum;                // Сумма расхода
    private Money    accountCreditSum;               // Сумма прихода

	private Calendar date;                           // Дата-время списания средств

	private String   description;                    // Описание операции
	private Card     operationCard;                  // Карта, по которой совершена операция
	private String   operationCardId;                // Идентификатор карты, по которой совершена операция
	private Money balance;                           // баланс
	private String shopInfo;                       //Информация о торговой точке
	private String recipient;                      // Наименование получателя/плательщик

	public Card getOperationCard()
	{
		return operationCard;
	}

	public void setOperationCard(Card operationCard)
	{
		this.operationCard = operationCard;
	}

	public Calendar getOperationDate()
    {
        return operationDate;
    }

    public void setOperationDate(Calendar operationDate)
    {
        this.operationDate = operationDate;
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

	public Calendar getDate()
    {
		return date;
    }

	public void setDate(Calendar date)
    {
		this.date = date;
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

	public String getDocumentNumber()
    {
        return operationId;
    }

	public Money getBalance()
	{
		return balance;
	}

	public void setBalance(Money balance)
	{
		this.balance = balance;
	}

	public String getOperationId()
    {
        return operationId;
    }

    public void setOperationId(String operationId)
    {
        this.operationId = operationId;
    }

	public String getOperationCardId()
	{
		return operationCardId;
	}

	public void setOperationCardId(String operationCardId)
	{
		this.operationCardId = operationCardId;
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
