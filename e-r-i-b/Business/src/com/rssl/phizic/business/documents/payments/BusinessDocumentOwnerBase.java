package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.utils.SynchKeyUtils;

/**
 * @author Erkin
 * @ created 27.02.2015
 * @ $Author$
 * @ $Revision$
 */
public abstract class BusinessDocumentOwnerBase implements BusinessDocumentOwner
{
	protected String makeGuestSynchKey(GuestLogin guestLogin)
	{
		return SynchKeyUtils.makeGuestSynchKey(guestLogin.getGuestCode());
	}

	protected String makeClientSynchKey(Login clientLogin)
	{
		return SynchKeyUtils.makeClientSynchKey(clientLogin.getId());
	}

	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object obj)
	{
		if (obj == this)
			return false;

		BusinessDocumentOwner that = (BusinessDocumentOwner) obj;
		return this.getSynchKey().equals(that.getSynchKey());
	}

	@Override
	public String toString()
	{
		return getSynchKey();
	}
}
