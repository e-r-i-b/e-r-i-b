package com.rssl.phizic.config.build;

import com.rssl.phizic.config.build.generated.WebModuleDescriptor;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erkin
 * @ created 08.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class WebModule extends Module
{
	private final String warName;

	private final String settingsPath;

	private final String contextPath;

	private final Set<BusinessModule> businessModules;

	WebModule(WebModuleDescriptor desc, Collection<BusinessModule> businessModules)
	{
		super(desc);
		this.warName = StringUtils.defaultIfEmpty(desc.getWarName(), getName() + ".war");
		this.contextPath = desc.getContextPath();
		this.settingsPath = desc.getSettingsPath();
		this.businessModules = new HashSet<BusinessModule>(businessModules);
	}

	/**
	 * @return ��� war-�����
	 */
	public String getWarName()
	{
		return warName;
	}

	/**
	 * @return ���� � ���������� �������� ������ ������������ Settings/configs/${������������}/${����������}
	 */
	public String getSettingsPath()
	{
		return settingsPath;
	}

	public String getContextPath()
	{
		return contextPath;
	}

	/**
	 * @return ������-������, ������� ������ ���� ��������� � ����� WEB-INF/lib
	 */
	public Collection<BusinessModule> getBusinessModules()
	{
		return Collections.unmodifiableSet(businessModules);
	}
}
