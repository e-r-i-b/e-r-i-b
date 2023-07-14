package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;

/**
 * Фильтр на проверку: является ли платеж переводом со ВКЛАДА на СОЦИАЛЬНУЮ КАРТУ.
 *
 * @author bogdanov
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class ReceiverSocialCardFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) 
	{
		if (!(stateObject instanceof AbstractAccountsTransfer))
			return false;

		AbstractAccountsTransfer transfer = (AbstractAccountsTransfer) stateObject;
		if (transfer.getType() != AccountToCardTransfer.class)
			return false;
		
		return transfer.getReceiverCard().startsWith("6390");
	}
}
