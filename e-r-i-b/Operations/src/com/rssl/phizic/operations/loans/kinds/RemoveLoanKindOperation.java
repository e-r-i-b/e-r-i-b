package com.rssl.phizic.operations.loans.kinds;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author gladishev
 * @ created 19.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class RemoveLoanKindOperation extends OperationBase implements RemoveEntityOperation
{
	private static final LoanKindService loanKindService = new LoanKindService();

	private LoanKind loanKind;

	/**
	 * Инициализация
	 * @param kindId ID вида кредита для удаления
	 */
	public void initialize(Long kindId) throws BusinessException
	{
		loanKind = loanKindService.findById(kindId);

		if (loanKind == null)
			throw new BusinessException("Вид кредита с Id = " + kindId + " не найден");
	}

	/**
	 * Удалить вид кредита
	 */
	public void remove() throws BusinessException
	{
		loanKindService.remove(loanKind);
	}

	public LoanKind getEntity()
	{
		return loanKind;
	}
}
