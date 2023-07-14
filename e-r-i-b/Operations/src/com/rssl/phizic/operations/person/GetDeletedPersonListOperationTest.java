package com.rssl.phizic.operations.person;

import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.test.MockEmployeeDataProvider;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 26.01.2007
 * @ $Author: krenev $
 * @ $Revision: 5386 $
 */
@SuppressWarnings({"JavaDoc"})
public class GetDeletedPersonListOperationTest extends BusinessTestCaseBase
{
	public void testGetDeletedPersonListOperation() throws DataAccessException
	{
		EmployeeContext.setEmployeeDataProvider(new MockEmployeeDataProvider());
		GetDeletedPersonListOperation operation = new GetDeletedPersonListOperation();

		Query query = operation.createQuery("list");
		query.setMaxResults(10);
		List personsExt = query.executeList();

		assertNotNull(personsExt);

		if (personsExt.size() == 0)
			return;

	}
}
