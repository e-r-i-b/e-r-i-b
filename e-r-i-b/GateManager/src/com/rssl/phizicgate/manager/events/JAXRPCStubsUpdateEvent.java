package com.rssl.phizicgate.manager.events;

import com.rssl.phizic.events.Event;

/**
 * ������� ���������� ������� jaxrpc ��������, ������������ ������� �� �����
 * @author gladishev
 * @ created 24.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class JAXRPCStubsUpdateEvent implements Event
{
	public String getStringForLog()
	{
		return "JAXRPCStubsUpdateEvent";
	}
}
