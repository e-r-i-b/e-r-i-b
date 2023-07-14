package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.ReceiverState;

/**
 * @author osminin
 * @ created 03.03.2009
 * @ $Author$
 * @ $Revision$
 */

public class ActiveReceiverFilter implements ReceiverFilter
{
	public boolean accept(PaymentReceiverBase receiver)
	{
		return receiver.getState().equals(ReceiverState.ACTIVE);
	}
}
