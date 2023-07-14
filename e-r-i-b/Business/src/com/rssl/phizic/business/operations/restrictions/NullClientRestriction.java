package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.gate.clients.Client;

/**
 * @author mihaylov
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class NullClientRestriction implements ClientRestriction
{
	public static final NullClientRestriction INSTANCE = new NullClientRestriction();

	public boolean accept(Client client)
	{
		//ничего не делаем
		return true;
	}
}
