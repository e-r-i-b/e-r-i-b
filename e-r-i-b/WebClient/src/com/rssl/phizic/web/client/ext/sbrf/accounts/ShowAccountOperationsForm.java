package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author lukina
 * @ created 24.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountOperationsForm extends EditFormBase
{
	public static final Form FILTER_FORM = new AccountOperationsFilterFormBuilder().build();

	private AccountLink accountLink;
	private AccountTarget target;
	private AccountAbstract accountAbstract;
	private List<AccountLink> otherAccounts;
    //Сообщение об ощибке для отображения пользователю
	private String abstractMsgError;

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public AccountTarget getTarget()
	{
		return target;
	}

	public void setTarget(AccountTarget target)
	{
		this.target = target;
	}

	public AccountAbstract getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(AccountAbstract accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public List<AccountLink> getOtherAccounts()
	{
		return otherAccounts;
	}

	public void setOtherAccounts(List<AccountLink> otherAccounts)
	{
		this.otherAccounts = otherAccounts;
	}

	public String getAbstractMsgError()
	{
		return abstractMsgError;
	}

	public void setAbstractMsgError(String abstractMsgError)
	{
		this.abstractMsgError = abstractMsgError;
	}
}
