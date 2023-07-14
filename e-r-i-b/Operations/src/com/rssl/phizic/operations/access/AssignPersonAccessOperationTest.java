package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Evgrafov
 * @ created 22.12.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 4221 $
 */
@SuppressWarnings({"JavaDoc"})
public class AssignPersonAccessOperationTest extends BusinessTestCaseBase
{
	public void testChangeMode() throws Exception
	{
		AssignPersonAccessOperation operation = new AssignPersonAccessOperation();

		PersonLoginSource loginSource   = new PersonLoginSource(PersonServiceTest.getTestPerson().getId());
		AssignAccessHelper helper = CategoryAssignAccessHelper.createClient();
		AccessType accessType = AccessType.secure;

		operation.initialize(loginSource, accessType, helper);
		operation.setNewAccessTypeAllowed(true);
		operation.assign();


		operation.initialize(loginSource, accessType, helper);
		assertTrue(operation.getAccessTypeAllowed());
		operation.setNewAccessTypeAllowed(false);
		operation.assign();

		operation.initialize(loginSource, accessType);
		assertFalse(operation.getAccessTypeAllowed());
	}
}