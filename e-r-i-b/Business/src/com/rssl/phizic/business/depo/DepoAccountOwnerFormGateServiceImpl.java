package com.rssl.phizic.business.depo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.depo.DepoAccountOwnerForm;
import com.rssl.phizic.gate.depo.DepoAccountOwnerFormGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;

/**
 * @author lukina
 * @ created 13.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountOwnerFormGateServiceImpl extends AbstractService implements DepoAccountOwnerFormGateService
{

	protected DepoAccountOwnerFormGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void update(DepoAccountOwnerForm form) throws GateException, GateLogicException
	{
		try
		{
			DepoAccountOwnerFormService   depoService = new DepoAccountOwnerFormService();
			depoService.addOrUpdate(form);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);	
		}
	}
}
