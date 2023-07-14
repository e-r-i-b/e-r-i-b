package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.operations.restrictions.defaults.NullUserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Evgrafov
 * @ created 26.07.2006
 * @ $Author: hudyakov $
 * @ $Revision: 15515 $
 */

@SuppressWarnings({"JavaDoc"})
public class GetEmpoweredPersonsListOperationTest extends BusinessTestCaseBase
{
	public void test() throws Exception
	{
		ActivePerson testPerson = PersonServiceTest.getTestPerson();
		GetEmpoweredPersonsListOperation operation = new GetEmpoweredPersonsListOperation();
		operation.setRestriction(NullUserRestriction.INSTANCE);

		operation.initialize(testPerson.getId());
		operation.createQuery("list").executeList();
	}
}