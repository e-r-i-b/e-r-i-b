package com.rssl.phizic.web.servlet;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * @author Erkin
 * @ created 27.04.12
 * @ $Author$
 * @ $Revision$
 */

public final class WebContextPath
{
	private final String applicationPath;

	private final String folderPath;

	public WebContextPath(String applicationPath)
	{
		this(applicationPath, null);
	}

	public WebContextPath(String applicationPath, String folderPath)
	{
		this.applicationPath = adaptPath(applicationPath);
		this.folderPath = adaptPath(folderPath);
	}

	public String getApplicationPath()
	{
		return applicationPath;
	}

	public String getFolderPath()
	{
		return folderPath;
	}

	public String toString()
	{
		return applicationPath + folderPath;
	}

	private static String adaptPath(String path)
	{
		if (StringHelper.isEmpty(path))
			return "";
		return "/" + StringUtils.strip(path, "/");
	}

	public int hashCode()
	{
		int result = applicationPath.hashCode();
		result = 31 * result + folderPath.hashCode();
		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		WebContextPath that = (WebContextPath) o;

		if (!applicationPath.equals(that.applicationPath))
			return false;
		if (!folderPath.equals(that.folderPath))
			return false;

		return true;
	}
}
