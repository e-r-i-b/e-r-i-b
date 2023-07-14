package com.rssl.phizicgate.sbrf.bankroll;

import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.bankroll.CardOperation;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.Currency;

import java.util.Calendar;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 14.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class SBRFCardAbstract implements CardAbstract
{
    private Calendar fromDate;
    private Calendar            toDate;
    private Money openingBalance;
    private Money               closingBalance;
    private List<CardOperation> cardOperations;
    private Currency currency;
	private List<CardOperation> unsettledOperations;

	SBRFCardAbstract()
	{
		
	}

    SBRFCardAbstract(Currency currency, Calendar fromDate, Calendar toDate)
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

    void setClosingBalance(Money closingBalance)
    {
        this.closingBalance = closingBalance;
    }

    public List<CardOperation> getOperations()
    {
        return cardOperations;
    }

	public void setCardOperations(List<CardOperation> cardOperations)
    {
        this.cardOperations = cardOperations;
    }

	public List<CardOperation> getUnsettledOperations()
	{
		return unsettledOperations;
	}

	public void setUnsettledOperations(List<CardOperation> unsettledOperations)
    {
        this.unsettledOperations = unsettledOperations;
    }

	public List<TransactionBase> getTransactions()
	{
		return null;
	}
}
