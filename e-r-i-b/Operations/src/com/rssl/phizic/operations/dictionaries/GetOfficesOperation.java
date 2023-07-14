package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.Bank;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 18.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class GetOfficesOperation  extends OperationBase implements ListEntitiesOperation
{
	private static OfficeGateService service = GateSingleton.getFactory().service(OfficeGateService.class);

	public List<Office> getOffices(Office template, int firstResult, int listLimit) throws BusinessException, BusinessLogicException
	{
		try
		{
			return service.getAll(template, firstResult, listLimit);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
