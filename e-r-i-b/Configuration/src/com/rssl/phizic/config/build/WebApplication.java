package com.rssl.phizic.config.build;

import com.rssl.phizic.config.build.generated.WebApplicationDescriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Erkin
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class WebApplication
{
	private final String name;

	private final String contextPath;

	private final Collection<WebModuleBinding> webModuleBindings;

	WebApplication(WebApplicationDescriptor desc, Collection<WebModuleBinding> webModuleBindings)
	{
		this.name = desc.getName();
		this.contextPath = desc.getContextPath();
		this.webModuleBindings = new ArrayList<WebModuleBinding>(webModuleBindings);
	}

	public String getName()
	{
		return name;
	}

	public String getContextPath()
	{
		return contextPath;
	}

	public Collection<WebModuleBinding> getWebModuleBindings()
	{
		return Collections.unmodifiableCollection(webModuleBindings);
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
		if (o == null || getClass() != o.getClass())
			return false;

		WebApplication that = (WebApplication) o;

		return name.equals(that.name);
	}
}
