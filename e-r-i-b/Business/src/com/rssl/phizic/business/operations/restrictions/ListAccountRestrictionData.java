package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AccountLink;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс данных ограничений для счетов.
 *
 * @author Roshka
 * @ created 17.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class ListAccountRestrictionData extends RestrictionData<ListAccountRestriction>
{
	private List<AccountLink> accountLinks;

	public List<AccountLink> getAccountLinks()
	{
		return accountLinks;
	}

	public void setAccountLinks(List<AccountLink> accountLinks)
	{
		this.accountLinks = accountLinks;
	}

	public ListAccountRestriction buildRestriction() throws BusinessException
	{
		Set<String> accounts = new HashSet<String>();

		for (AccountLink accountLink : accountLinks)
			accounts.add(accountLink.getExternalId());

		return new ListAccountRestriction(accounts);
	}
}