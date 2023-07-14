package com.rssl.phizic.operations.dictionaries.routing.node;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.routing.Node;
import com.rssl.phizicgate.manager.routing.NodeService;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class RemoveNodeOperation extends OperationBase implements RemoveEntityOperation
{
	private Node node;
	private static final NodeService nodeService = new NodeService();

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		try
		{
			node = nodeService.getNodeById(id);
			if (node == null)
			{
				throw new BusinessLogicException("”зла с id=" + id + " не найдено.");
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		try
		{
			nodeService.remove(node);
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

	public Node getEntity()
	{
		return node;
	}
}
