package com.rssl.phizic.web.common.mobile.cards;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.mobile.accounts.SendAccountBankDetailsAction;

/**
 * @ author: Gololobov
 * @ created: 10.01.14
 * @ $Author$
 * @ $Revision$
 */
public class SendCardAccountBankDetailsAction extends SendAccountBankDetailsAction
{
	protected Class getLinkClassType()
	{
		return CardLink.class;
	}
}
