package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.loans.Loan;

/**
 * ������ ��������� ����������� �������
 * �.�. ����� accept ������ true, ���� ������ ������������������
 * @author gladishev
 * @ created 06.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class DifferentLoanFilter extends LoanFilterBase
{
	public boolean accept(Loan loan)
	{
		return !loan.getIsAnnuity();
	}
}
