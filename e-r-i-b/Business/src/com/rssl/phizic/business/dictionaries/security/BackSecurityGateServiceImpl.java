package com.rssl.phizic.business.dictionaries.security;

import com.rssl.phizic.gate.depo.BackRefSecurityService;
import com.rssl.phizic.gate.depo.SecurityBase;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * @author mihaylov
 * @ created 23.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class BackSecurityGateServiceImpl extends AbstractService implements BackRefSecurityService
{
	public BackSecurityGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<SecurityBase> getSecuritiesByCodes(List<String> insideCodes) throws GateException
	{
		try
		{
			SecurityService securityService = new SecurityService();
			return securityService.getSecuritiesByCodes(insideCodes);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
