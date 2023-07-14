package com.rssl.phizic.business.payments;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;

/**
 * @author gladishev
 * @ created 26.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class ABSSystemNameResolver implements DocumentSystemNameResolver
{
	private static final AdapterService adapterService = new AdapterService();

	public String getSystemName(StateObject document) throws GateException
	{
		if (!(document instanceof GateExecutableDocument))
			return "";

		GateExecutableDocument gateDocument = (GateExecutableDocument) document;

		try
		{
			String uuid = adapterService.getAdapterUUIDByOffice(gateDocument.getOffice());
			Adapter adapter = adapterService.getAdapterByUUID(uuid);
			return adapter.getName();
		}
		catch (GateException e)
		{
			throw new GateException("Ошибка при установке внешней системы для документа id=" + gateDocument.getId(), e);
		}
	}
}
