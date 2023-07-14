package com.rssl.phizic.tool;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.build.AppServerType;
import com.rssl.phizic.config.build.BuildContextConfig;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Erkin
 * @ created 22.05.2014
 * @ $Author$
 * @ $Revision$
 */
class BuildIKFLReader
{
	private final Properties buildIKFLProperties;

	///////////////////////////////////////////////////////////////////////////

	BuildIKFLReader(File buildIKFLFile) throws IOException
	{
		this.buildIKFLProperties = loadProperties(buildIKFLFile);
	}

	AppServerType readAppServerType()
	{
		if (BooleanUtils.toBoolean(buildIKFLProperties.getProperty(BuildContextConfig.APP_WEBSPHERE)))
			return AppServerType.websphere;

		if (BooleanUtils.toBoolean(buildIKFLProperties.getProperty(BuildContextConfig.APP_ORACLE)))
			return AppServerType.oracle;

		if (BooleanUtils.toBoolean(buildIKFLProperties.getProperty(BuildContextConfig.APP_JBOSS)))
			return AppServerType.jboss;

		throw new ConfigurationException("Не указан сервер приложений, для которого собирается проект");
	}

	List<String> readNewBuildAppList()
	{
		String appListProperty = buildIKFLProperties.getProperty("ikfl.new-build.app");
		if (StringUtils.isBlank(appListProperty))
			return Collections.emptyList();

		String[] appArray = StringUtils.split(appListProperty, ',');
		List<String> appList = new ArrayList<String>(appArray.length);
		for (String app : appArray)
		{
			app = StringUtils.stripToNull(app);
			if (app != null)
				appList.add(app);
		}

		return appList;
	}

	private Properties loadProperties(File file) throws IOException
	{
		InputStream is = new FileInputStream(file);
		try
		{
			Properties properties = new Properties();
			properties.load(is);
			return properties;
		}
		finally
		{
			is.close();
		}
	}
}
