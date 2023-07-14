package com.rssl.phizic.business.news;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.departments.Department;

import java.io.Serializable;

/**
 * @author lukina
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class NewsDepartments implements Serializable
{
	private Department department;
	private News news;

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public News getNews()
	{
		return news;
	}

	public void setNews(News news)
	{
		this.news = news;
	}
}

