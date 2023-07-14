package com.rssl.phizic.web.common.mobile.cards;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.mobile.accounts.ShowAccountBankDetailsAction;

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
