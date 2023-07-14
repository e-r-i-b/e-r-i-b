package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.auth.GuestLogin;
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
 * ���������� BusinessDocumentOwner ��� �������� ������.
 */
@Immutable
public class GuestBusinessDocumentOwner extends BusinessDocumentOwnerBase
{
	private final GuestLogin guestLogin;

	private final GuestPerson guestPerson;

	/**
	 * ctor
	 * @param guestLogin - ������ �����
	 * @param guestPerson - ������ �����
	 */
	public GuestBusinessDocumentOwner(GuestLogin guestLogin, GuestPerson guestPerson)
	{
		if (guestLogin == null)
		    throw new IllegalArgumentException("�� ������ guestLogin");
		if (guestPerson == null)
		    throw new IllegalArgumentException("�� ������ guestPerson");

		this.guestLogin = guestLogin;
		this.guestPerson = guestPerson;
	}

	public Login getLogin()
	{
		return guestLogin;
	}

	public ActivePerson getPerson()
	{
		return guestPerson;
	}

	public boolean isGuest()
	{
		return true;
	}

	public String getSynchKey()
	{
		return makeGuestSynchKey(guestLogin);
	}
}
