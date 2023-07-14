package com.rssl.phizic.operations.csaadmin.node;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 15.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * Исключение, вызванное обращением к недоступному блоку
 */

public class NodeNotAvailableException extends BusinessLogicException
{
	private final NodeInfo nodeInfo;

	/**
	 * конструктор
	 * @param node информация о недоступном блоке
	 */
	public NodeNotAvailableException(NodeInfo node)
	{
		super("Блок \"" + node.getName() + "\" не доступен!");
		nodeInfo = node;
	}

	/**
	 * @return информация о недоступном блоке
	 */
	public NodeInfo getNodeInfo()
	{
		return nodeInfo;
	}
}
