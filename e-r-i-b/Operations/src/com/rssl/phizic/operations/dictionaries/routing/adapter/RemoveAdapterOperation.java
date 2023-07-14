package com.rssl.phizic.operations.dictionaries.routing.adapter;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizicgate.manager.routing.NodeAndAdapterRelationService;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class RemoveAdapterOperation extends OperationBase implements RemoveEntityOperation
{
	private Adapter adapter;
	private static final AdapterService adapterService = new AdapterService();
	private static final NodeAndAdapterRelationService nodeAndAdapterRelationService = new NodeAndAdapterRelationService();


	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		try
		{
			adapter = adapterService.getAdapterById(id);
			if (adapter == null)
			{
				throw new BusinessLogicException("Адаптер с id " + id + " не найден");
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public void remove() throws BusinessLogicException, BusinessException
	{
		try
		{
			adapterService.remove(adapter);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public Adapter getEntity()
	{
		return adapter;
	}
}
