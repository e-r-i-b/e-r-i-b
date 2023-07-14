package com.rssl.phizic.web.client.ext.sbrf.cards;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.client.ext.sbrf.accounts.AccountBankDetailsAction;

/**
 * @ author: Gololobov
 * @ created: 19.12.13
 * @ $Author$
 * @ $Revision$
 */
public class CardAccountBankDetailsAction extends AccountBankDetailsAction
{
	protected Class getLinkClassType()
	{
		return CardLink.class;
	}
}