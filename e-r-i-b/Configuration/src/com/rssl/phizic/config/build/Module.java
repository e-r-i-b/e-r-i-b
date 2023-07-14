package com.rssl.phizic.config.build;

import org.apache.commons.lang.StringUtils;
import com.rssl.phizic.config.build.generated.ModuleDescriptor;

/**
 * @author Erkin
 * @ created 08.04.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class Module
{
	private final String name;

	private final String base;

	protected Module(ModuleDescriptor desc)
	{
		this.name = desc.getName();
		this.base = StringUtils.defaultIfEmpty(desc.getBase(), getName());
	}

	public String getName()
	{
		return name;
	}

	public String getBase()
	{
		return base;
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof Module))
			return false;

		Module module = (Module) o;

		return name.equals(module.name);
	}
}
