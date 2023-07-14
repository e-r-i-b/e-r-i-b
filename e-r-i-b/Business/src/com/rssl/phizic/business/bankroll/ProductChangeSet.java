package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.business.persons.PersonTiming;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.loans.Loan;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Коллектор изменений в составе продуктов
 */
class ProductChangeSet
{
	/**
	 * Перечень классов изменённых продуктов
	 */
	private final Set<Class> changes = new HashSet<Class>();

	private final PersonTiming timings;

	private final Calendar timestamp;

	///////////////////////////////////////////////////////////////////////////

	ProductChangeSet(PersonTiming timings, Calendar timestamp)
	{
		this.timings = timings;
		this.timestamp = timestamp;
	}

	Calendar getTimestamp()
	{
		return timestamp;
	}

	Calendar getAccountListLastUpdate()
	{
		return timings.getAccountListLastUpdate();
	}

	void markAccountListOutdated()
	{
		timings.setAccountListLastUpdate(timestamp);
		changes.add(Account.class);
	}

	Calendar getCardListLastUpdate()
	{
		return timings.getCardListLastUpdate();
	}

	void markCardListOutdated()
	{
		timings.setCardListLastUpdate(timestamp);
		changes.add(Card.class);
	}

	Calendar getLoanListLastUpdate()
	{
		return timings.getLoanListLastUpdate();
	}

	void markLoanListOutdated()
	{
		timings.setLoanListLastUpdate(timestamp);
		changes.add(Loan.class);
	}

	boolean isEmpty()
	{
		return changes.isEmpty();
	}

	Class[] getChanges()
	{
		return changes.toArray(new Class[changes.size()]);
	}
}
