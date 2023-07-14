package com.rssl.phizic.operations.groups;

import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.test.MockEmployeeDataProvider;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 20.03.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 3841 $
 */

@SuppressWarnings({"JavaDoc"})
public class ListGroupPersonsOperationTest extends BusinessTestCaseBase
{

	protected void setUp() throws Exception
	{
		super.setUp();
		EmployeeContext.setEmployeeDataProvider(new MockEmployeeDataProvider());
	}

	public void testListGroupPersonsOperationTest() throws Exception
	{
		ListGroupPersonsOperation operation = new ListGroupPersonsOperation();

		Query query = operation.createQuery("list");
		query.setParameter("firstName"        , null);
		query.setParameter("surName"          , "123");
		query.setParameter("patrName"         , null);
		query.setParameter("agreementNumber"  , null);
		query.setParameter("passportSeries"   , null);
		query.setParameter("passportNumber"   , null);
		query.setParameter("pinEnvelopeNumber", null);
		query.setParameter("blockedUntil"     , null);
		query.setParameter("blocked"          , null);
		query.setParameter("state"            , null);
		query.setParameter("identityType"     , null);

		List<Object> list = query.executeList();
		assertNotNull(list);
	}
}
