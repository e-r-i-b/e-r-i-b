package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonContext;

import java.util.List;

/**
 * @author gladishev
 * @ created 28.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanRestrictionImpl implements LoanRestriction
{
	private List<Loan> loans;

	public LoanRestrictionImpl(List<Loan> loans)
	{
		this.loans = loans;
	}

	public boolean accept(Loan loan)
	{
		return loans.contains(loan);
	}
}
