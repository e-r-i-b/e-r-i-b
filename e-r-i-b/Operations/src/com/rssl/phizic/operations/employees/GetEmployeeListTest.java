package com.rssl.phizic.operations.employees;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.business.BusinessException;

import java.util.List;
import java.util.Date;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class GetEmployeeListTest extends BusinessTestCaseBase
{
    public void testGetEmployeeList() throws BusinessException, DataAccessException
    {
        GetEmployeeListOperation operation = new GetEmployeeListOperation();

        Query query = operation.createQuery("getEmployeeAllowedDepartments");

        query.setParameter("firstName","Семен");
        query.setParameter("patrName", "Семен");
        query.setParameter("surName",  "Семен");
        query.setParameter("info",     "");

	    query.setParameter("blocked", 0);
	    query.setParameter("blockedUntil", new Date());
        query.setParameter("branchCode", "");
        query.setParameter("departmentCode", "");

        List list = query.executeList();

        assertNotNull(list);
    }
}
