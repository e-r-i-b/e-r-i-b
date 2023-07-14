package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;

/**
 * @author Evgrafov
 * @ created 16.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1918 $
 */

public interface PersonOperation extends Operation<UserRestriction>
{
	Long getPersonId();

	ActivePerson getPerson();
}
