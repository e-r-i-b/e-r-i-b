package com.rssl.phizicgate.manager.services.persistent.clients;

import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;
import com.rssl.phizic.gate.clients.UpdatePersonService;
import com.rssl.phizic.gate.clients.ClientState;
import com.rssl.phizic.gate.clients.CancelationCallBack;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.common.types.Money;

import java.util.Date;

/**
 * @author krenev
 * @ created 31.03.2010
 * @ $Author$
 * @ $Revision$
 */
public class UpdatePersonServiceImpl extends PersistentServiceBase<UpdatePersonService> implements UpdatePersonService
{
	public UpdatePersonServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void updateState(String clientId, ClientState newState) throws GateException, GateLogicException
	{
		delegate.updateState(storeRouteInfo(clientId),newState);
	}

	public void updateState(CancelationCallBack callback, ClientState newState) throws GateException, GateLogicException
	{
		delegate.updateState(storeRouteInfo(callback),newState);
	}

	public void lockOrUnlock(String clientId, Date lockDate, Boolean islock, Money liability) throws GateException, GateLogicException
	{
		delegate.lockOrUnlock(storeRouteInfo(clientId),lockDate, islock, liability);
	}
}
