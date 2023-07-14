package com.rssl.phizic.web.atm.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.operations.finances.ShowCashAndCashlessVolumeOperation;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;

/**
 * @author Mescheryakova
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowCashAndCashlessVolumeATMAction extends FinanceFilterATMActionBase
{
	public FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		ShowCashAndCashlessVolumeOperation op = createOperation(ShowCashAndCashlessVolumeOperation.class);
		op.initialize();
		return op;
	}

	public void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		ShowCashAndCashlessVolumeOperation op = (ShowCashAndCashlessVolumeOperation) operation;
		//Данные для графика "Доля наличных операций в структуре финансовых потоков"
		frm.setData(op.getCashOperationsToFinancialStream(filterData));
	}
}
