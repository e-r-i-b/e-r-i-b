package com.rssl.phizic.web.client.ext.sbrf.loans;

import org.apache.struts.action.ActionForm;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author mihaylov
 * @ created 01.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class PrintLoanInfoForm extends EditFormBase
{
	private LoanLink loanLink;
	private ActivePerson user;    // текущий пользователь

	public LoanLink getLoanLink()
	{
		return loanLink;
	}

	public void setLoanLink(LoanLink loanLink)
	{
		this.loanLink = loanLink;
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}
}
