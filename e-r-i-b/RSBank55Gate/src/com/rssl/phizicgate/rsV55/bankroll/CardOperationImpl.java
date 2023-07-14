package com.rssl.phizicgate.rsV55.bankroll;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.CardOperation;
import com.rssl.phizic.gate.bankroll.Card;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Kidyaev
 * @ created 18.10.2005
 * @ $Author: mihaylov $
 * @ $Revision: 4703 $
 */

public class CardOperationImpl implements CardOperation, Serializable
{
	private String   operationId;                    // ������������� ����������
	private Calendar operationDate;                  // ���� ��������

	private Money    debitSum;                       // ����� �������
    private Money    creditSum;                      // ����� �������
	private Money    accountDebitSum;                // ����� �������
    private Money    accountCreditSum;               // ����� �������

	private Calendar date;                     // ����-����� �������� �������

	private String   description;                    // �������� ��������
	private long     transactionFlag;                // ����, ������� �� �������� � �����������
	private long     transactionKind;                // ��� �������� ( 2 - ������, ����� - ������)
    private long     listTransfer;                   // ������ �� ���������� ???
	private Card operationCard;
	private Money balance;
	private String shopInfo;                       //���������� � �������� �����
	private String recipient;                      // ������������ ����������/����������

	public Card getOperationCard()
	{
		return operationCard;
	}

	void setOperationCard(Card operationCard)
	{
		this.operationCard = operationCard;
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

	public String getDescription()
    {
        return description;
    }

	public Calendar getDate()
    {
		return date;
    }

	void setDate(Calendar date)
    {
		this.date = date;
    }

	void setDescription(String description)
    {
        this.description = description;
    }

	private String getOperationId()
    {
        return operationId;
    }

    void setOperationId(String operationId)
    {
        this.operationId = operationId;
    }

    private long getTransactionFlag()
    {
        return transactionFlag;
    }

    void setTransactionFlag(long transactionFlag)
    {
        this.transactionFlag = transactionFlag;
    }

	private long getTransactionKind()
    {
        return transactionKind;
    }

    void setTransactionKind(long transactionKind)
    {
        this.transactionKind = transactionKind;
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

    long getListTransfer()
    {
        return listTransfer;
    }

    void setListTransfer(long listTransfer)
    {
        this.listTransfer = listTransfer;
    }

	public String getDocumentNumber()
	{
		return operationId;
	}

	public Money getBalance()
	{
		return balance;
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
