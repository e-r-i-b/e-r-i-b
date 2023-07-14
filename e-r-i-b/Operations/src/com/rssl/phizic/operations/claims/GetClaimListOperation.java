package com.rssl.phizic.operations.claims;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Kidyaev
 * @ created 12.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class GetClaimListOperation extends OperationBase
{
	private static DepartmentService departmentService = new DepartmentService();

	public Department getDepartment() throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	    Long departmentId = employeeData.getEmployee().getDepartmentId();
		return departmentService.findById( departmentId );
	}

	public Office getClaimOffice(BusinessDocument claim) throws BusinessException
	{
		if(claim instanceof GateExecutableDocument)
		{
			GateExecutableDocument claimBase = (GateExecutableDocument)claim;

			return claimBase.getOffice() == null ? null : claimBase.getOffice();
		}
		return null;
	}
}
