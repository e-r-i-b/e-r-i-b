package com.rssl.phizic.web.common.mobile.accounts;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author EgorovaA
 * @ created 27.11.13
 * @ $Author$
 * @ $Revision$
 */
public class ShowAccountBankDetailsForm extends EditFormBase
{
	private AccountLink accountLink;
	private CardLink cardLink;

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}
}
