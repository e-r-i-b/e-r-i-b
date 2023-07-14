package com.rssl.phizicgate.manager.services.routable.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class RegistartionClientServiceImpl extends RoutableServiceBase implements RegistartionClientService
{
	private RegistartionClientService gateDelegate;

	public RegistartionClientServiceImpl(GateFactory factory)
	{
		super(factory);
		gateDelegate = (RegistartionClientService) getDelegate(RegistartionClientService.class.getName() + GATE_DELEGATE_KEY);
	}

	public void register(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		RegistartionClientService delegate = getDelegateFactory(office).service(RegistartionClientService.class);
		delegate.register(office, registerRequest);
	}

	public void update(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		RegistartionClientService delegate = getDelegateFactory(office).service(RegistartionClientService.class);
		delegate.update(office, registerRequest);
	}

	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException
	{
		RegistartionClientService delegate = getDelegateFactory(client).service(RegistartionClientService.class);
		return delegate.cancellation(client, trustingClientId, date, type, reason);
	}

	public void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException
	{
		gateDelegate.update(client, lastUpdateDate, isNew, user);
	}
}
