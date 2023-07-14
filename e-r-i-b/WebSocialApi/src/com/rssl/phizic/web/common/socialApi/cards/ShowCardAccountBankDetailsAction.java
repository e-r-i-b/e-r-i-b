package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.socialApi.accounts.ShowAccountBankDetailsAction;

/**
 * Получение реквизитов счета карты
 *
 * @ author: Gololobov
 * @ created: 10.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ShowCardAccountBankDetailsAction extends ShowAccountBankDetailsAction
{
	protected Class getLinkClassType()
	{
		return CardLink.class;
	}
}
