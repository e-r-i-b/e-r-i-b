package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.bki.CreditProfileService;
import com.rssl.phizic.business.bki.PersonCreditProfile;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.person.Person;

/**
 * @author Gulov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditReportShowCondition implements MenuLinkCondition
{
	private static final CreditProfileService service = new CreditProfileService();

	public boolean accept() throws BusinessException
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		PersonCreditProfile profile = service.findByPerson(person);
		return profile != null && profile.isConnected();
	}
}
