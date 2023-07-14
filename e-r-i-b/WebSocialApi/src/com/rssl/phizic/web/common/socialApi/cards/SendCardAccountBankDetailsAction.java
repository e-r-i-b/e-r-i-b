package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.socialApi.accounts.SendAccountBankDetailsAction;

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
