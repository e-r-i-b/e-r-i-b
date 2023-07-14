package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.clients.Client;

import java.util.Arrays;
import java.util.List;

/**
 * @author akrenev
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * составной клиентский рестрикшен
 */
public class CompositeClientRestricnion implements ClientRestriction
{
	private final List<ClientRestriction> restrictions;

	/**
	 * @param restrictions клиентские рестрикшены
	 */
	public CompositeClientRestricnion(ClientRestriction... restrictions)
	{
		this.restrictions = Arrays.asList(restrictions);
	}

	public boolean accept(Client client) throws BusinessException
	{
		for (ClientRestriction restriction : restrictions)
			if (!restriction.accept(client))
				return false;

		return true;
	}
}
