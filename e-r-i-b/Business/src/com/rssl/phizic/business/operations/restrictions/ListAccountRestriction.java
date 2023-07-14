package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;

import java.util.Set;

/**
 * Класс реализующий проверку на ограничение для операций со счетами.
 *
 * @author Roshka
 * @ created 17.04.2006
 * @ $Author$
 * @ $Revision$
 */
public class ListAccountRestriction implements AccountFilter
{
	private Set<String> accounts;

	public ListAccountRestriction(Set<String> accounts)
	{
		this.accounts = accounts;
	}

	/**
	 * Подходит ли счет под критерии
	 *
	 * @param accountLink
	 * @return true - подходит, false - не подходит
	 */
	public boolean accept(AccountLink accountLink)
	{
		return accounts.contains(accountLink.getAccount().getId());
	}
}