package com.rssl.phizic.web.client.ext.sbrf.loans;

import com.rssl.phizic.business.persons.ActivePerson;

/**
 * User: Moshenko
 * Date: 22.10.2010
 * Time: 11:49:42
 */
public class PrintLoanPaymentInfoForm extends ShowLoanPaymentInfoForm
{
	private ActivePerson user;

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}
}
