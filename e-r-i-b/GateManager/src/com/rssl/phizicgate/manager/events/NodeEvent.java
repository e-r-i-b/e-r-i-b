package com.rssl.phizicgate.manager.events;

import com.rssl.phizic.events.Event;
import com.rssl.phizicgate.manager.routing.Node;

/**
 * @author Krenev
 * @ created 01.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class NodeEvent implements Event
{
	public enum Type
	{
		REGISTER,
		UNREGISTER,
		UPDATE
	}

	private Node node;
	private Type type;

	public NodeEvent(Node node, Type type)
	{
		this.node = node;
		this.type = type;
	}

	public Node getNode()
	{
		return node;
	}

	public Type getType()
	{
		return type;
	}
	public String getStringForLog()
	{
		StringBuilder stringForLog = new StringBuilder();
		return stringForLog.append("NodeEvent, наименование узла ").append(node.getName()).append(", адрес узла ").append(node.getURL()).append(", тип события ").append(type).toString();
	}
}
