package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author filimonova
 * @ created 10.03.2011
 * @ $Author$
 * @ $Revision$
 *
 *  Список шаблонов и платежей по счету\вкладу
 */
public class AccountPaymentsForm extends EditFormBase
{
	private AccountLink accountLink;
	private AccountTarget target;
	private List<AccountLink> otherAccounts;
	private List<TemplateDocument> templates;
	private boolean cardOperation;
	private boolean accountOperation;

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

	public List<AccountLink> getOtherAccounts()
	{
		return otherAccounts;
	}

	public void setOtherAccounts(List<AccountLink> otherAccounts)
	{
		this.otherAccounts = otherAccounts;
	}

	public List<TemplateDocument> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<TemplateDocument> templates)
	{
		this.templates = templates;
	}

	public boolean isCardOperation()
	{
		return cardOperation;
	}

	public void setCardOperation(boolean cardOperation)
	{
		this.cardOperation = cardOperation;
	}

	public boolean isAccountOperation()
	{
		return accountOperation;
	}

	public void setAccountOperation(boolean accountOperation)
	{
		this.accountOperation = accountOperation;
	}
}
