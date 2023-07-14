package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.socialApi.accounts.ShowAccountBankDetailsAction;

/**
 * ��������� ���������� ����� �����
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
