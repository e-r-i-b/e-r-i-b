package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.common.types.annotation.Immutable;

/**
 * @author Erkin
 * @ created 26.02.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация BusinessDocumentOwner для клиентской сессии.
 */
@Immutable
public class ClientBusinessDocumentOwner extends BusinessDocumentOwnerBase
{
	private final ActivePerson clientPerson;

	private final Login clientLogin;

	/**
	 * ctor
	 * @param clientPerson - негостевая персона
	 */
	public ClientBusinessDocumentOwner(ActivePerson clientPerson)
	{
		if (clientPerson == null)
			throw new IllegalArgumentException("Не указана персона");

		if (clientPerson instanceof GuestPerson)
			throw new IllegalArgumentException("Гостевая персона тут не подходит: " + clientPerson);

		this.clientPerson = clientPerson;
		this.clientLogin = clientPerson.getLogin();
	}

	public Login getLogin()
	{
		return clientLogin;
	}

	public ActivePerson getPerson()
	{
		return clientPerson;
	}

	public boolean isGuest()
	{
		return false;
	}

	public String getSynchKey()
	{
		return makeClientSynchKey(clientLogin);
	}
}
