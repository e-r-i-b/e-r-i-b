package com.rssl.phizic.business.operations;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 23.09.2005
 * Time: 18:00:11
 */
public class OperationDescriptor implements Serializable
{
	private Long   id;
	private String key;
	private String operationClassName;
	private String name;
	private String restrictionInterfaceName;
	private String defaultRestrictionClassName;
	private String strategy;

	public String getStrategy()
	{
		return strategy;
	}

	public void setStrategy(String strategy)
	{
		this.strategy = strategy;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getOperationClassName()
	{
		return operationClassName;
	}

	public void setOperationClassName(String operationClassName)
	{
		this.operationClassName = operationClassName;
	}

	public String getRestrictionInterfaceName()
	{
		return restrictionInterfaceName;
	}

	public void setRestrictionInterfaceName(String restrictionInterfaceName)
	{
		this.restrictionInterfaceName = restrictionInterfaceName;
	}

	public String getDefaultRestrictionClassName()
	{
		return defaultRestrictionClassName;
	}

	public void setDefaultRestrictionClassName(String defaultRestrictionClassName)
	{
		this.defaultRestrictionClassName = defaultRestrictionClassName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return "[" + key + "] " + name;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		OperationDescriptor that = (OperationDescriptor) o;

		if (!key.equals(that.key))
			return false;

		return true;
	}

	public int hashCode()
	{
		return key.hashCode();
	}
}
