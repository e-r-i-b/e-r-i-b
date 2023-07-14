package com.rssl.phizic.config.ant;

import com.rssl.phizic.config.build.BuildConfig;
import com.rssl.phizic.config.build.BusinessModule;
import com.rssl.phizic.config.build.EjbModule;
import com.rssl.phizic.config.build.WebModule;
import org.apache.tools.ant.BuildException;

/**
 * @author Barinov
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class BuildSingleModuleTask extends BuildModulesTask
{
	private String moduleName;

	/**
	 *
	 * @param moduleName - имя модуля, который требуется собрать
	 */
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	@Override
	protected void buildModules(BuildConfig buildConfig)
	{
		WebModule webModule = buildConfig.getWebModule(moduleName);
		if (webModule != null)
		{
			for (BusinessModule businessModule : webModule.getBusinessModules())
				buildBusinessModule(businessModule);
			buildWebModule(webModule);
			return;
		}

		EjbModule ejbModule = buildConfig.getEjbModule(moduleName);
		if (ejbModule != null)
		{
			for (BusinessModule businessModule : ejbModule.getBusinessModules())
				buildBusinessModule(businessModule);
			buildEjbModule(ejbModule);
			return;
		}

		throw new BuildException("Не найден модуль " + moduleName);
	}
}

