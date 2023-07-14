package com.rssl.phizic.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.QueryParameter;

/**
 * @author Barinov
 * @ created 26.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListForEmployeeOperation<R extends Restriction> extends OperationBase<R>
{
	@Deprecated //TODO Удалить после изменения механизма работы с доступными подразделениями
	public Long getLoginId() throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	    return employeeData.getEmployee().getLogin().getId();
	}

	@QueryParameter
	public boolean isAllTbAccess()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().isAllTbAccess();
	}

	@QueryParameter
	public Long getEmployeeLoginId()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin().getId();
	}
}
