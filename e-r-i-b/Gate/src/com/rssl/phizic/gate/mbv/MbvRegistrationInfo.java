package com.rssl.phizic.gate.mbv;

import java.util.List;
import java.util.Set;

/**
 * Информация по подключению МБВ клиента
 * @author Puzikov
 * @ created 04.09.14
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings("ALL")
public class MbvRegistrationInfo
{
	private List<MbvClientIdentity> identities;
	private Set<String> phones;
	private Set<String> accounts;

	public List<MbvClientIdentity> getIdentities()
	{
		return identities;
	}

	public void setIdentities(List<MbvClientIdentity> identities)
	{
		this.identities = identities;
	}

	public Set<String> getPhones()
	{
		return phones;
	}

	public void setPhones(Set<String> phones)
	{
		this.phones = phones;
	}

	public Set<String> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(Set<String> accounts)
	{
		this.accounts = accounts;
	}
}
