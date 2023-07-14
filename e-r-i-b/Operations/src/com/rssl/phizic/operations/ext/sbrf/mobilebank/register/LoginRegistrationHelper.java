package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.Login;

import java.util.List;
import java.util.Collections;

/**
 * @author Erkin
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */
class LoginRegistrationHelper extends RegistrationHelper
{
	private static final PersonService personService = new PersonService();

	private static final ExternalResourceService resourceService = new ExternalResourceService();

	private final ActivePerson person;

	private final String loginCardNumber;

	private final List<CardLink> cardLinks;

	///////////////////////////////////////////////////////////////////////////

	LoginRegistrationHelper(AuthenticationContext authContext) throws BusinessException, BusinessLogicException
	{
		Login login = (Login) authContext.getLogin();

		person = personService.findByLogin(login);

		// К этому моменту getLastLogonCardNumber() вернёт карту текущего входа
		loginCardNumber = login.getLastLogonCardNumber();

		cardLinks = resourceService.getLinks(login, CardLink.class);
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
