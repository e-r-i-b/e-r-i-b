package com.rssl.phizic.web.common.socialApi.security;

import com.rssl.phizic.business.StaticSocialPersonData;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.security.auth.ClientAthenticationCompleteAction;
import com.rssl.phizic.context.PersonContext;

/**
 * @author lukina
 * @ created 14.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ClientAthenticationCompleteSocialApiAction extends ClientAthenticationCompleteAction
{
	protected void updateContext(ActivePerson person)
	{
		PersonContext.getPersonDataProvider().setPersonData(new StaticSocialPersonData(person));
	}
}
