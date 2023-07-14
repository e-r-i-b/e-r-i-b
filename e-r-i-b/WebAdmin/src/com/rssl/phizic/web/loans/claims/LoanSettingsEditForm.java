package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Nady
 * @ created 08.06.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * ‘орма редактировани€ настроек дл€ кредитов
 */
public class LoanSettingsEditForm extends ActionFormBase
{
	private boolean getCreditAtLogon;

	public boolean isGetCreditAtLogon()
	{
		return getCreditAtLogon;
	}

	public void setGetCreditAtLogon(boolean getCreditAtLogon)
	{
		this.getCreditAtLogon = getCreditAtLogon;
	}
}
