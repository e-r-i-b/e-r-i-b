package com.rssl.phizicgate.manager.services.persistent.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;
import org.apache.commons.lang.ArrayUtils;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 04.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class BackRefClientServiceImpl extends PersistentServiceBase<BackRefClientService> implements BackRefClientService
{
	private static final String SEPARATOR = "\\|";

	public BackRefClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Client getClientById(Long id) throws GateLogicException, GateException
	{
		return removeRouteInfo(delegate.getClientById(id));
	}

	public Client getClientByFIOAndDoc(String firstName, String lastName, String middleName, String docSeries, String docNumber, Calendar birthDate, String tb) throws GateLogicException, GateException
	{
		return removeRouteInfo(delegate.getClientByFIOAndDoc(firstName, lastName, middleName, docSeries, docNumber, birthDate, tb));
	}

	public String getClientDepartmentCode(Long loginId) throws GateLogicException, GateException
	{
		String[] codeArray = delegate.getClientDepartmentCode(loginId).split(SEPARATOR);
		return ArrayUtils.isEmpty(codeArray) ? null : codeArray[0];
	}

	public String getClientCreationType(String clientId) throws GateLogicException, GateException
	{
		return delegate.getClientCreationType(storeRouteInfo(clientId));
	}
}
