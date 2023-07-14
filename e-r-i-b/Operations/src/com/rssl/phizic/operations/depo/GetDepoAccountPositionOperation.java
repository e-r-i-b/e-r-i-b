package com.rssl.phizic.operations.depo;

import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author lukina
 * @ created 31.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class GetDepoAccountPositionOperation extends GetDepoAccountLinkOperation
{
	public DepoAccountPosition getDepoAccountPositionInfo() throws BusinessLogicException
	{
		try
		{
			return getDepoAccountLink().getDepoAccountPositionInfo();
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
