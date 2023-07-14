package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * @author Moshenko
 * @ created 30.01.2014
 * @ $Author$
 * @ $Revision$
 * ������ ������� ����� ��� ���� ����� �� �������� ��������� ��������� ��� ShortLoanOffer, ExtendedLoanClaim
 * ������ ������� ����� ��� ���� ����� �� �������� ��������� ��������� ��� ShortLoanOffer
 */
public class LoanClaimStategyCondition implements StrategyCondition
{
	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		/**
		 *  �������� ������ �� ������(����� ���������� ��������� ������������ ������)
		 */
		if (object instanceof ShortLoanClaim && ((ShortLoanClaim)object).getProcessingEnabled())
			return false;

		/**
		 * ����������� ������ �� ������ �� �������� ������������� �� ������ � ����
		 */
		if (object instanceof ExtendedLoanClaim)
			return false;

		return true;
	}
}
