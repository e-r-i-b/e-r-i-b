package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.gate.config.WSGateConfig;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 07.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class Node implements WSGateConfig, Serializable
{
	private Long id;
	private String name;
	private String URL;
	private NodeType type;
	private String prefix;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getURL()
	{
		return URL;
	}

	public void setURL(String URL)
	{
		this.URL = URL;
	}

	/**
	 * @return тип узла
	 */
	public NodeType getType()
	{
		return type;
	}

	public void setType(NodeType type)
	{
		this.type = type;
	}

	/**
	 * @return префикс для узлов с типом "София-ВМС"
	 */
	public String getPrefix()
	{
		return prefix;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Node that = (Node) o;

		if (!id.equals(that.id))
			return false;

		return true;
	}

	public int hashCode()
	{
		return id.hashCode();
	}
}
