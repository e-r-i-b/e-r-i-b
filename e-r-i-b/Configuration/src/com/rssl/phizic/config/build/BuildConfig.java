package com.rssl.phizic.config.build;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.build.generated.*;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 08.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class BuildConfig
{
	public static final String BUILD_CONFIG_XML = "build-config.xml";

	public static final String BUILD_CONFIG_JAXB_PACKAGE = "com.rssl.phizic.config.build.generated";

	private final Map<String, BusinessModule> businessModules = new HashMap<String, BusinessModule>();

	private final Map<String, EjbModule> ejbModules = new HashMap<String, EjbModule>();

	private final Map<String, WebModule> webModules = new HashMap<String, WebModule>();

	private final Map<String, WebApplication> webApplications = new HashMap<String, WebApplication>();

	private final Map<String, WebApplication> webAppByContextPath = new HashMap<String, WebApplication>();

	private final boolean debugMode;

	///////////////////////////////////////////////////////////////////////////

	BuildConfig(BuildConfigElement descriptor) throws ConfigurationException
	{
		debugMode = descriptor.isDebugMode();
		for (BusinessModuleDescriptor moduleDescriptor : descriptor.getBusinessModule())
		{
			BusinessModule module = createBusinessModule(moduleDescriptor);
			businessModules.put(module.getName(), module);
		}

		for (EjbModuleDescriptor moduleDescriptor : descriptor.getEjbModule())
		{
			EjbModule module = createEjbModule(moduleDescriptor);
			ejbModules.put(module.getName(), module);
		}

		for (WebModuleDescriptor moduleDescriptor : descriptor.getWebModule())
		{
			WebModule module = createWebModule(moduleDescriptor);
			webModules.put(module.getName(), module);
		}

		for (WebApplicationDescriptor appDescriptor : descriptor.getWebApplication())
		{
			WebApplication app = createWebApplication(appDescriptor);
			webApplications.put(app.getName(), app);
			webAppByContextPath.put(StringUtils.strip(app.getContextPath(), "/"), app);
		}
	}

	private BusinessModule createBusinessModule(BusinessModuleDescriptor descriptor) throws ConfigurationException
	{
		return new BusinessModule(descriptor);
	}

	private EjbModule createEjbModule(EjbModuleDescriptor descriptor)
	{
		Set<BusinessModule> ejbBusinessModules = new HashSet<BusinessModule>(businessModules.size());
		for (BusinessModuleRefDescriptor ref : descriptor.getBusinessModule())
		{
			BusinessModule businessModule = businessModules.get(ref.getName());
			if (businessModule == null)
				throw new ConfigurationException("Не найден бизнес-модуль " + ref.getName());

			ejbBusinessModules.add(businessModule);
		}

		return new EjbModule(descriptor, ejbBusinessModules);
	}

	private WebModule createWebModule(WebModuleDescriptor descriptor) throws ConfigurationException
	{
		Set<BusinessModule> webBusinessModules = new HashSet<BusinessModule>(businessModules.size());
		for (BusinessModuleRefDescriptor ref : descriptor.getBusinessModule())
		{
			BusinessModule businessModule = businessModules.get(ref.getName());
			if (businessModule == null)
				throw new ConfigurationException("Не найден бизнес-модуль " + ref.getName());

			webBusinessModules.add(businessModule);
		}

		return new WebModule(descriptor, webBusinessModules);
	}

	private WebApplication createWebApplication(WebApplicationDescriptor descriptor) throws ConfigurationException
	{
		List<WebModuleBinding> webModuleBindings = new LinkedList<WebModuleBinding>();
		for (WebModuleRefDescriptor ref : descriptor.getWebModule())
		{
			WebModule module = webModules.get(ref.getName());
			if (module == null)
				throw new ConfigurationException("Не найден веб-модуль " + ref.getName());

			webModuleBindings.add(new WebModuleBinding(module, ref.getUrlFolder()));
		}

		return new WebApplication(descriptor, webModuleBindings);
	}

	/**
	 * @return true, если сборка для отладки
	 */
	public boolean isDebugMode()
	{
		return debugMode;
	}

	public Collection<BusinessModule> getBusinessModules()
	{
		return businessModules.values();
	}

	public BusinessModule getBusinessModule(String name)
	{
		return businessModules.get(name);
	}

	public Collection<EjbModule> getEjbModules()
	{
		return ejbModules.values();
	}

	public EjbModule getEjbModule(String name)
	{
		return ejbModules.get(name);
	}

	public Collection<WebModule> getWebModules()
	{
		return webModules.values();
	}

	public WebModule getWebModule(String name)
	{
		return webModules.get(name);
	}

	public Collection<WebApplication> getWebApplications()
	{
		return webApplications.values();
	}

	/**
	 * Возвращает веб-приложение по имени
	 * @param name
	 * @return
	 */
	public WebApplication getWebApplication(String name)
	{
		return webApplications.get(name);
	}

	/**
	 * Возвращает веб-приложение по contextPath
	 * @param contextPath
	 * @return
	 */
	public WebApplication getWebApplicationByContextPath(String contextPath)
	{
		return webAppByContextPath.get(StringUtils.strip(contextPath, "/"));
	}
}
