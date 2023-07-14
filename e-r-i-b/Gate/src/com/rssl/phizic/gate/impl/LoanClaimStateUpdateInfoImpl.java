package com.rssl.phizic.gate.impl;

import com.rssl.phizic.gate.documents.LoanClaimStateUpdateInfo;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.documents.State;

import java.util.Calendar;

/**
 * @author Maleyev
 * @ created 08.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimStateUpdateInfoImpl implements LoanClaimStateUpdateInfo
{
	private LoanOpeningClaim claim;

	public LoanClaimStateUpdateInfoImpl(LoanOpeningClaim _claim)
	{
		claim = _claim;
		assert claim != null : "Заявка не должна быть null";
	}

	public Money getApprovedAmount()
	{
		return claim.getApprovedAmount();
	}

	public DateSpan getApprovedDuration()
	{
		return claim.getApprovedDuration();
	}

	public State getState()
	{
		return claim.getState();
	}

	public Calendar getNextProcessDate()
	{
		throw new UnsupportedOperationException();
	}
}
