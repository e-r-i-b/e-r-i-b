package com.rssl.phizic.config.build;

import com.rssl.phizic.config.build.generated.EjbModuleDescriptor;
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

/**
 * EJB-модуль
 */
public class EjbModule extends Module
{
	private final String jarName;

	private final String settingsPath;

	private final Set<BusinessModule> businessModules;

	EjbModule(EjbModuleDescriptor desc, Collection<BusinessModule> businessModules)
	{
		super(desc);
		this.jarName = StringUtils.defaultIfEmpty(desc.getJarName(), getName() + ".jar");
		this.settingsPath = desc.getSettingsPath();
		this.businessModules = new HashSet<BusinessModule>(businessModules);
	}

	/**
	 * @return имя jar-файла
	 */
	public String getJarName()
	{
		return jarName;
	}

	/**
	 * @return путь к директории настроек модуля относительно Settings/configs/${конфигурация}/${приложение}
	 */
	public String getSettingsPath()
	{
		return settingsPath;
	}

	/**
	 * @return бизнес-модули, которые должны быть добавлены в папку WEB-INF/lib
	 */
	public Collection<BusinessModule> getBusinessModules()
	{
		return Collections.unmodifiableSet(businessModules);
	}
}
