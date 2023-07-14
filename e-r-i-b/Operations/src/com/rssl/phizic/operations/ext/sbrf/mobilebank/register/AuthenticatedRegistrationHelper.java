package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

import java.util.List;
import java.util.Collections;

/**
 * @author Erkin
 * @ created 17.09.2012
 * @ $Author$
 * @ $Revision$
 */
class AuthenticatedRegistrationHelper extends RegistrationHelper
{
	private final ActivePerson person;

	private final String loginCardNumber;

	private final List<CardLink> cardLinks;

	///////////////////////////////////////////////////////////////////////////

	AuthenticatedRegistrationHelper() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		person = personData.getPerson();
		loginCardNumber = person.getLogin().getLastLogonCardNumber();
		cardLinks = personData.getCards();
	}

	ActivePerson getPerson()
	{
		return person;
	}

	protected String getLoginCardNumber()
	{
		return loginCardNumber;
	}

	protected List<CardLink> getCardLinks()
	{
		return Collections.unmodifiableList(cardLinks);
	}
}
