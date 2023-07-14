package com.rssl.phizic.web.common.mobile.security;

import com.rssl.phizic.business.StaticMobilePersonData;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.security.auth.ClientAthenticationCompleteAction;
import com.rssl.phizic.context.PersonContext;

/**
 * @author lukina
 * @ created 14.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ClientAthenticationCompleteMobileAction extends ClientAthenticationCompleteAction
{
	protected void updateContext(ActivePerson person)
	{
		PersonContext.getPersonDataProvider().setPersonData(new StaticMobilePersonData(person));
	}
}
