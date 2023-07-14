package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.gate.bankroll.CardOperation;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author: Pakhomova
 * @created: 10.06.2009
 * @ $Author$
 * @ $Revision$
 * copypaste from CardOperationImpl of retail6gate
 */
public class CardOperationImpl implements CardOperation, Serializable
{
	private String   operationId;                    // ������������� ����������
	private Calendar operationDate;                  // ���� ��������

	private Money debitSum;                       // ����� �������
    private Money    creditSum;                      // ����� �������
	private Money    accountDebitSum;                // ����� �������
    private Money    accountCreditSum;               // ����� �������

	private Calendar date;                           // ����-����� �������� �������

	private String   description;                    // �������� ��������
	private Card operationCard;                  // �����, �� ������� ��������� ��������
	private String   operationCardId;                // ������������� �����, �� ������� ��������� ��������
	private Money balance;                           // ������
	private String shopInfo;                       //���������� � �������� �����
	private String recipient;                      // ������������ ����������/����������

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