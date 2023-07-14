package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.loans.products.LoanProduct;

import java.util.Set;

/**
 * @author gladishev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductRestrictionImpl implements LoanProductRestriction
{
	private Set<Long> loansIds;

	public LoanProductRestrictionImpl(Set<Long> loansIds)
	{
		this.loansIds = loansIds;
	}

	/**
	 * �������� �� ������� ��� ��������
	 *
	 * @param loanProduct
	 * @return true - ��������, false - �� ��������
	 */
	public boolean accept(LoanProduct loanProduct)
	{
		return loansIds.contains(loanProduct.getId());
	}
}
