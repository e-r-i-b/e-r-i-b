package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 @author: Egorovaa
 @ created: 17.10.2012
 @ $Author$
 @ $Revision$
 */
public abstract class MobileActionBase extends OperationalActionBase
{
	protected static final String FORWARD_ERMB = "ermb";

	public boolean isErmbProfile()
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ErmbProfileImpl profile = ErmbHelper.getErmbProfileByPerson(personData.getPerson());
		return profile != null && profile.isServiceStatus() && !profile.getPhones().isEmpty();
	}
}
