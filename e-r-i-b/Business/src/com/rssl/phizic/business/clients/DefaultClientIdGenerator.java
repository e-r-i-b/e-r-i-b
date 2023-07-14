package com.rssl.phizic.business.clients;

import com.rssl.phizic.gate.clients.ClientIdGenerator;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.RandomGUID;

/**
 * @author hudyakov
 * @ created 13.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class DefaultClientIdGenerator extends ClientIdGenerator
{
	public String generate(Office office)
	{
		return new RandomGUID().getStringValue();
	}
}
