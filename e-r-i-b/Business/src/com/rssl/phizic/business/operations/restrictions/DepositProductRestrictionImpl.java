package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.deposits.DepositProduct;

import java.util.Set;

/**
 * @author Evgrafov
 * @ created 24.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1377 $
 */

public class DepositProductRestrictionImpl implements DepositProductRestriction
{
	private Set<Long> depositsIds;

	public DepositProductRestrictionImpl(Set<Long> depositsIds)
	{
		this.depositsIds = depositsIds;
	}

	/**
	 * �������� �� ���� ��� ��������
	 *
	 * @param depositProduct
	 * @return true - ��������, false - �� ��������
	 */
	public boolean accept(DepositProduct depositProduct)
	{
		return depositsIds.contains(depositProduct.getId());
	}
}