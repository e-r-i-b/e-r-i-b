package com.rssl.phizicgate.rsretailV6r4.bankroll;

import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.Currency;

import java.util.Calendar;
import java.util.List;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 14.10.2005
 * Time: 15:54:11
 * Класс "выписка по счету"
 */
public class AccountAbstractImpl implements AccountAbstract, DepositAbstract
{
    private Calendar                fromDate;
    private Calendar                toDate;
    private Money                   openingBalance;
    private Money                   closingBalance;
    private List<TransactionBase>   transactions;
    private Currency                currency;
	private Calendar                previousOperationDate;

	//дополнительная информация - необходима только для сбера
	//список доверенностей
	private List<Trustee> trustedDocuments;
	private Calendar closedDate;  //дата закрытия счета (=null, если счет еще открыт)
	private Money closedSum;      //сумма, выплаченная при закрытии счета (=null, если счет еще открыт)
	private String additionalInformation;
	
    AccountAbstractImpl(Currency currency, Calendar fromDate, Calendar toDate)
    {
        this.fromDate = fromDate;
        this.toDate   = toDate;
        this.currency = currency;
    }

    void setFromDate(Calendar fromDate)
    {
        this.fromDate = fromDate;
    }

    public Calendar getFromDate()
    {
        return fromDate;
    }

    void setToDate(Calendar toDate)
    {
        this.toDate = toDate;
    }

    public Calendar getToDate()
    {
        return toDate;
    }

    void setOpeningBalance(Money openingBalance)
    {
        this.openingBalance = openingBalance;
    }

    public Money getOpeningBalance()
    {
        return openingBalance;
    }

    void setClosingBalance(Money closingBalance)
    {
        this.closingBalance = closingBalance;
    }

    public Money getClosingBalance()
    {
        return closingBalance;
    }

	public List<TransactionBase> getTransactions()
	{
		return transactions;
	}

	void setTransactions(List<TransactionBase> transactions)
    {
        this.transactions = transactions;
    }
   
    public Money getTotalCredit() throws GateException
    {
        Money totalCredit = new Money(new BigDecimal(0.0), currency);

        for (TransactionBase transaction : transactions)
        {
            totalCredit = totalCredit.add(transaction.getCreditSum());
        }

        return totalCredit;
    }

    public Money getTotalDebit() throws GateException
    {
        Money totalDebit = new Money(new BigDecimal(0.0), currency);

        for (TransactionBase transaction : transactions)
        {
            totalDebit = totalDebit.add(transaction.getDebitSum());
        }

        return totalDebit;
    }

	public Calendar getPreviousOperationDate()
	{
		return previousOperationDate;
	}

	public void setPreviousOperationDate(Calendar previousOperationDate)
	{
		this.previousOperationDate = previousOperationDate;
	}

	public List<Trustee> getTrusteesDocuments()
	{
		return trustedDocuments;
	}

	public String getAdditionalInformation()
	{
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}

	public void setTrusteesDocuments(List<Trustee> trustees)
	{
		trustedDocuments = trustees;
	}

	public Calendar getClosedDate()
	{
		return closedDate;
	}

	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	public Money getClosedSum()
	{
		return closedSum;
	}

	public void setClosedSum(Money closedSum)
	{
		this.closedSum = closedSum;
	}
}
