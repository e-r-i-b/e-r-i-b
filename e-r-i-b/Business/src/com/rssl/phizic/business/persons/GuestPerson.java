package com.rssl.phizic.business.persons;

import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.person.*;

import java.util.*;

/**
 * Гостевой профиль
 * @author niculichev
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */
public class GuestPerson extends ActivePerson
{

	public GuestPerson()
	{
		super();
		setCheckLoginCount(0L);
	}

	private boolean haveMBKConnection;

	public String getStatus()
	{
		return null;
	}

	public void setStatus(String status)
	{
	}

	public String getDiscriminator()
	{
		return null;
	}

	public void setId(Long id)
	{
		throw new UnsupportedOperationException("В гостевой сессии не поддерживается");
	}

	public Long getId()
	{
		throw new UnsupportedOperationException("В гостевой сессии не поддерживается");
	}

	public void setLogin(Login login)
	{
	    if (login instanceof GuestLogin)
		    super.setLogin(login);
	}

	public boolean isHaveMBKConnection()
	{
		return haveMBKConnection;
	}

	public void setHaveMBKConnection(boolean haveMBKConnection)
	{
		this.haveMBKConnection = haveMBKConnection;
	}

	public TranslitMode getSMSFormat()
	{
		return TranslitMode.DEFAULT;
	}

	@Override
	public Client asClient() throws BusinessException
	{
		List<ClientDocument> clientDocuments = new ArrayList<ClientDocument>();
		for (PersonDocument personDocument : this.getPersonDocuments())
		{
			clientDocuments.add(new ClientDocumentImpl(personDocument));
		}

		ClientImpl client = new ClientImpl(getFirstName(), getPatrName(), getSurName() );
		client.setOffice(new OfficeImpl());
		client.setBirthDay(getBirthDay());
		client.setDocuments(clientDocuments);
		return client;
	}
}
