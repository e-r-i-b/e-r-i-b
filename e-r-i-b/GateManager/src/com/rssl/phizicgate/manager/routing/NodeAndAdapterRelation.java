package com.rssl.phizicgate.manager.routing;

/**
 * @author akrenev
 * @ created 07.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class NodeAndAdapterRelation
{
	private Long    id;
	private Adapter adapter;
	private Node    node;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Adapter getAdapter()
	{
		return adapter;
	}

	public void setAdapter(Adapter adapter)
	{
		this.adapter = adapter;
	}

	public Node getNode()
	{
		return node;
	}

	public void setNode(Node node)
	{
		this.node = node;
	}
}
