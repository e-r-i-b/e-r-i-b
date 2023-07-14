package com.rssl.phizicgate.manager.events;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.events.EventHandler;
import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.routing.Node;

/**
 * @author Krenev
 * @ created 01.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class NodeEventHandler implements EventHandler<NodeEvent>
{
	private ConfigFactory configFactory = null;

	public NodeEventHandler()
	{
	}

	public void process(NodeEvent event) throws Exception
	{
		Node node = event.getNode();
		switch (event.getType())
		{
			case REGISTER:
				GateManager.getInstance().buildGate(node);
				break;
			case UNREGISTER:
				GateManager.getInstance().dropGate(node);
				break;
			case UPDATE:
				GateManager.getInstance().dropGate(node);
				GateManager.getInstance().buildGate(node);
				break;
			default:
				throw new IllegalArgumentException("Illegal event type " + event.getType());
		}
	}
}
