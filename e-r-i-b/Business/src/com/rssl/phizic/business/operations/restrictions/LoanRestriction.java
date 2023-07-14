package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.gate.loans.Loan;

/**
 * @author gladishev
 * @ created 28.03.2008
 * @ $Author$
 * @ $Revision$
 */

public interface LoanRestriction extends Restriction
{
	/**
	 * �������� �� ������ ��� ��������
	 * @param loan - ������ �������
	 * @return true - ��������, false - �� ��������
	 */
	boolean accept(Loan loan);
}
