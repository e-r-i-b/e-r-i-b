package com.rssl.phizic.operations.access;

import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeServiceTest;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 09.02.2007
 * @ $Author: krenev_a $
 * @ $Revision: 54593 $
 */

@SuppressWarnings({"JavaDoc"})
public class AssignEmployeeAccessOperationTest extends BusinessTestCaseBase
{
	private Employee testEmployee;

	protected void setUp() throws Exception
	{
		super.setUp();
		testEmployee = EmployeeServiceTest.getTestEmployee();
	}

	protected void tearDown() throws Exception
	{
		testEmployee = null;
		super.tearDown();
	}

	public void testAssignEmployeeAccessOperation() throws Exception
	{
		AssignEmployeeAccessOperation o1 = createOperation();
		// убрать схему вообще - никаких прав
		o1.setNewAccessSchemeId(null);
		o1.assign();

		AssignEmployeeAccessOperation o2 = createOperation();

		// назначить первую попавшуюся схему
		SharedAccessScheme scheme = findAnyScheme(o2);
		assertNotNull(scheme);
		o2.setNewAccessSchemeId(scheme.getId());
		o2.assign();

		AssignEmployeeAccessOperation o3 = createOperation();

		List<AssignAccessHelper> helpers = o2.getHelpers();

		for (AssignAccessHelper helper : helpers)
		{
			updateAndSave(helper);
		}
	}

	private SharedAccessScheme findAnyScheme(AssignEmployeeAccessOperation o) throws BusinessException
	{
		for (AssignAccessHelper helper : o.getHelpers())
		{
			List<SharedAccessScheme> list = helper.getSchemes();

			if(!list.isEmpty())
				return list.get(0);
		}
		fail("Не нашли ни одной схемы");
		return null;
	}

	private AssignEmployeeAccessOperation createOperation() throws Exception
	{

		AssignEmployeeAccessOperation operation = new AssignEmployeeAccessOperation();
		operation.initialize(testEmployee.getId());

		return operation;
	}

	private void updateAndSave(AssignAccessHelper helper) throws Exception
	{
		AssignEmployeeAccessOperation operation = createOperation();

		List<Service> services = helper.getServices();
		List<Long> serviceIds = new ArrayList<Long>();

		for (Service service : services)
		{
			serviceIds.add(service.getId());
		}

		operation.setPersonalScheme(helper.getCategory(), serviceIds);
		operation.assign();
	}
}
