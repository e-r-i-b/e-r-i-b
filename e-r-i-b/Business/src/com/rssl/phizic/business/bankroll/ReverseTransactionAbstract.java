package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.bankroll.Trustee;

import java.util.*;

/**
 * Класс предназначен для представления выписки в обратном порядке.
 * @author niculichev
 * @ created 21.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class ReverseTransactionAbstract implements AccountAbstract
{
	AccountAbstract original;
	List<TransactionBase> reverseTransaction;

	public List<TransactionBase> getReverseTransaction()
	{
		return reverseTransaction;
	}

	public ReverseTransactionAbstract(AccountAbstract original)
	{
		this.original = original;
		reverseTransaction = new ArrayList<TransactionBase>();

		List<TransactionBase> originalTransaction = original.getTransactions();
		for (int i = originalTransaction.size() - 1; i >= 0; i--)
			reverseTransaction.add(originalTransaction.get(i));
	}

	public Calendar getPreviousOperationDate()
	{
		return original.getPreviousOperationDate();
	}

	public List<Trustee> getTrusteesDocuments()
	{
		return original.getTrusteesDocuments();
	}

	public Calendar getClosedDate()
	{
		return original.getClosedDate();
	}

	public Money getClosedSum()
	{
		return original.getClosedSum();
	}

	public String getAdditionalInformation()
	{
		return original.getAdditionalInformation();
	}

	public Calendar getFromDate()
	{
		return original.getFromDate();
	}

	public Calendar getToDate()
	{
		return original.getToDate();
	}

	public Money getOpeningBalance()
	{
		return original.getOpeningBalance();
	}

	public Money getClosingBalance()
	{
		return original.getClosingBalance();
	}

	public List<TransactionBase> getTransactions()
	{
		return reverseTransaction;
	}
}
