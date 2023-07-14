package com.rssl.phizicgate.mock.clients;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author Egorova
 * @ created 28.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class MockRegistartionClientService extends AbstractService implements RegistartionClientService
{
	public MockRegistartionClientService(GateFactory factory)
	{
		super(factory);
	}

	public void register(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("операция не поддерживается");
	}

	public void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("операция не поддерживается.");
	}

	public void update(Office office, Document registerRequest) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("операция не поддерживается");
	}

	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("операция не поддерживается");
	}
}
