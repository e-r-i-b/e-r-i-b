package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;

import java.util.Set;

/**
 * ����� ����������� �������� �� ����������� ��� �������� �� �������.
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
	 * �������� �� ���� ��� ��������
	 *
	 * @param accountLink
	 * @return true - ��������, false - �� ��������
	 */
	public boolean accept(AccountLink accountLink)
	{
		return accounts.contains(accountLink.getAccount().getId());
	}
}