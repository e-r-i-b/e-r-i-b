package com.rssl.phizic.operations.loans.kinds;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author gladishev
 * @ created 19.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditLoanKindOperation extends OperationBase implements EditEntityOperation
{
	private static final LoanKindService loanKindService = new LoanKindService();

	private LoanKind loanKind;

	/**
	 * Инициализация новым продуктом
	 */
	public void initializeNew() throws BusinessException
	{
		loanKind = new LoanKind();
	}

	/**
	 * Инициализация
	 * @param kindId ID вида кредита
	 */
	public void initialize(Long kindId) throws BusinessException
	{
		loanKind = loanKindService.findById(kindId);

		if (loanKind == null)
			throw new BusinessException("Вид кредита с Id = " + kindId + " не найден");
	}

	public void setLoanKind(LoanKind loanKind)
	{
		this.loanKind = loanKind;
	}

	public LoanKind getEntity()
	{
		return loanKind;
	}

	public void save() throws BusinessException
	{
		loanKind.setUpperName(loanKind.getName().toUpperCase());
		loanKindService.addOrUpdate(loanKind);
	}
}
