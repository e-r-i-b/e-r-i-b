package com.rssl.phizic.ws.currency.sbrf.log;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.log.LogService;

/**
 * @author gladishev
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 */
public class LogServiceImpl implements LogService
{
	private GateFactory factory;

	public LogServiceImpl(GateFactory factory)
	{
		this.factory = factory;
	}

	public GateFactory getFactory()
	{
		return factory;
	}

	public Long getDepartmentIdByCode(String tb, String osb, String vsp) throws GateException, GateLogicException
	{
		return null;
	}

	public String getDepartmentNameByCode(String tb, String osb, String vsp) throws GateException, GateLogicException
	{
		return null;
	}
}
