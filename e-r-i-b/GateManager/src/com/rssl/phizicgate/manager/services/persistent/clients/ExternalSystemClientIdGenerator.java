package com.rssl.phizicgate.manager.services.persistent.clients;

import com.rssl.phizic.gate.clients.ClientIdGenerator;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.manager.services.IDHelper;
import com.rssl.phizicgate.manager.services.objects.OfficeWithoutRouteInfo;

/**
 * @author hudyakov
 * @ created 13.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class ExternalSystemClientIdGenerator extends ClientIdGenerator
{
	public String generate(Office office) throws GateLogicException, GateException
	{
		String info = new OfficeWithoutRouteInfo(office).getRouteInfo();
		String newId = new RandomGUID().getStringValue();

		return IDHelper.storeRouteInfo(newId, info);
	}
}
