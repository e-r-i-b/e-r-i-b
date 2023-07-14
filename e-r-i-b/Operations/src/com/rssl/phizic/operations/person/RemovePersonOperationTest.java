package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.operations.restrictions.defaults.NullUserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;

/**
 * @author Kidyaev
 * @ created 12.12.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"}) @ExcludeTest
public class RemovePersonOperationTest extends BusinessTestCaseBase
{
	private ActivePerson person;

	protected void setUp() throws Exception
	{
		super.setUp();
		person  = PersonServiceTest.getTestPerson();
	}

	protected void tearDown() throws Exception
	{
		if ( person != null )
			new PersonService().markDeleted(person);

		super.tearDown();		
	}

	// todo: Ќе удал€ютс€ персоны, у которых есть платежи с дополнительными пол€ми (см. Payments.hbm.xml <map name="attributes">).
	// Hibernate некорректно строит план HQL-запроса.
	// —сылка по теме: http://forum.hibernate.org/viewtopic.php?t=957857&start=0&postdays=0&postorder=asc&highlight=&sid=ed681d23f73aa0519ae93df56f83e55c
	public void testRemovePersonOperation() throws Exception
	{
		RemovePersonOperation operation = new RemovePersonOperation();

		operation.setRestriction(NullUserRestriction.INSTANCE);
		operation.initialize(person.getId());
		operation.remove();
		person = null;
	}
}
