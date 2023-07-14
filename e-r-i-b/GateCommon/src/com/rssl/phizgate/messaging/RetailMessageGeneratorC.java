package com.rssl.phizgate.messaging;

import com.rssl.phizic.utils.RandomGUID;

/**
 * @author Krenev
 * @ created 08.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class RetailMessageGeneratorC extends RetailMessageGeneratorBase
{
	protected String generateId()
	{
		return new RandomGUID().getStringValue();
	}
}
