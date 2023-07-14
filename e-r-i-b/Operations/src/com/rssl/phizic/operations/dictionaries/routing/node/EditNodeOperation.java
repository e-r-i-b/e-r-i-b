package com.rssl.phizic.operations.dictionaries.routing.node;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizicgate.manager.routing.Node;
import com.rssl.phizicgate.manager.routing.NodeService;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditNodeOperation extends OperationBase implements EditEntityOperation
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

	public void initializeNew()
	{
		node = new Node();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		try
		{
			if (node.getId() == null)
			{
				nodeService.add(node);
			}
			else
			{
				nodeService.update(node);
			}
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

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return node;
	}
}
