package com.rssl.phizic.operations.loans.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 04.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanProductOperation extends ListLoanProductOperationBase
{
	public Map<LoanKind, List<ModifiableLoanProduct>> getProductsByKind(Map<String, Object> filterParams) throws BusinessException
	{
		Map<LoanKind, List<ModifiableLoanProduct>> map = new HashMap<LoanKind, List<ModifiableLoanProduct>>();

		for(LoanKind loanKind : loanKindService.findByFilter(filterParams))
			map.put(loanKind, new ArrayList<ModifiableLoanProduct>());

		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		for(ModifiableLoanProduct product : modifiableLoanProductService.findByFilter(filterParams,employeeData.isAllTbAccess(),employeeData.getEmployee().getLogin().getId()))
			map.get(product.getLoanKind()).add(product);

		return map;
	}
}
