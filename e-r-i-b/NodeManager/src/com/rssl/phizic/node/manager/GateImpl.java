package com.rssl.phizic.node.manager;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.node.NodeFactory;

/**
 * @author osminin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */
public class GateImpl implements Gate
{
	private NodeInfo nodeInfo;
	private GateFactory factory;

	/**
	 * ctor
	 * @param nodeInfo информация о блоке
	 */
	public GateImpl(NodeInfo nodeInfo)
	{
		this.nodeInfo = nodeInfo;
	}

	public GateFactory getFactory()
	{
		return factory;
	}

	public void initialize() throws GateException
	{
		NodeFactory nodeFactory = new NodeFactoryImpl(nodeInfo.getListenerHost());
		nodeFactory.initialize();
		factory = nodeFactory;
	}
}