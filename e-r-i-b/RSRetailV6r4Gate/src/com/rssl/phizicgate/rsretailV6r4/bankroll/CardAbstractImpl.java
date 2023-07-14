package com.rssl.phizicgate.rsretailV6r4.bankroll;

import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.bankroll.CardOperation;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.Currency;

import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 18.10.2005
 * Time: 20:50:47
 * Класс "выписка по карте"
 */
public class CardAbstractImpl implements CardAbstract
{
    private Calendar            fromDate;
    private Calendar            toDate;
    private Money               openingBalance;
    private Money               closingBalance;
    private List<TransactionBase> transactions;
    private Currency            currency;
	private List<CardOperation> unsettledOperations;

    CardAbstractImpl(Currency currency, Calendar fromDate, Calendar toDate)
    {
        this.fromDate = fromDate;
        this.toDate   = toDate;
        this.currency = currency;
    }

    public Calendar getFromDate()
    {
        return fromDate;
    }

    void setFromDate(Calendar fromDate)
    {
        this.fromDate = fromDate;
    }

    public Calendar getToDate()
    {
        return toDate;
    }

    void setToDate(Calendar toDate)
    {
        this.toDate = toDate;
    }

    public Money getOpeningBalance()
    {
        return openingBalance;
    }

    void setOpeningBalance(Money openingBalance)
    {
        this.openingBalance = openingBalance;
    }

    public Money getClosingBalance()
    {
        return closingBalance;
    }

	public List<TransactionBase> getTransactions()
	{
		return transactions;
	}

	public void setTransactions(List<TransactionBase> transactions)
    {
        this.transactions = transactions;
    }

	void setClosingBalance(Money closingBalance)
    {
        this.closingBalance = closingBalance;
    }

	public List<CardOperation> getUnsettledOperations()
	{
		return unsettledOperations;
	}

	public void setUnsettledOperations(List<CardOperation> unsettledOperations)
    {
        this.unsettledOperations = unsettledOperations;
    }
}
