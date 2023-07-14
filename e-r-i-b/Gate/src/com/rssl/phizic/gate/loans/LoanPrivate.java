package com.rssl.phizic.gate.loans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Jatsky
 * @ created 25.08.15
 * @ $Author$
 * @ $Revision$
 */

public interface LoanPrivate extends Serializable
{

	public BigDecimal getRecPaymentAmount();

	public Calendar getClosestPaymentDate();
}
