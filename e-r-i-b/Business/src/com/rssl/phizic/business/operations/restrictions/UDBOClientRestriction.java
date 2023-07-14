package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.clients.Client;

/**
 * @author akrenev
 * @ created 16.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Ограничение на признак УДБО
 */

public class UDBOClientRestriction implements ClientRestriction
{
	public boolean accept(Client client) throws BusinessException
	{
		return client != null && client.isUDBO();
	}
}
