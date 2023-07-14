package com.rssl.phizic.tool;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.config.build.AppServerType;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Erkin
 * @ created 22.05.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Загружает makefile из buildIKFL.properties
 */
class MakefileLoader
{
	private File projectRoot;

	///////////////////////////////////////////////////////////////////////////

	@MandatoryParameter
	void setProjectRoot(File projectRoot)
	{
		this.projectRoot = projectRoot;
	}

	Makefile loadMakefile()
	{
		try
		{
			File buildIKFLFile = new File(projectRoot, "Settings/configs/sbrf/buildIKFL.properties");
			BuildIKFLReader buildIKFLReader = new BuildIKFLReader(buildIKFLFile);

			final AppServerType applicationServer = buildIKFLReader.readAppServerType();
			final Collection<String> applications = buildIKFLReader.readNewBuildAppList();

			// noinspection AnonymousInnerClassWithTooManyMethods
			return new Makefile()
			{
				public AppServerType getAppServerType()
				{
					return applicationServer;
				}

				public Collection<String> getApplications()
				{
					return Collections.unmodifiableCollection(applications);
				}
			};
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
