package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.io.StringReader;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author osminin
 * @ created 11.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class DepositDepartmentsListSource implements EntityListSource
{
	private Set<Department> departments;

	public DepositDepartmentsListSource(Set<Department> departments)
	{
		this.departments = departments;
	}

	public Source getSource(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		try
		{
			return XmlHelper.parse(builder.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private EntityListBuilder getEntityListBuilder(Map<String, String> params)
	{
		EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();

		for (Department department : departments)
		{
			builder.openEntityTag(Long.toString( department.getId() ) );
				builder.appentField("name", department.getName());
			builder.closeEntityTag();
		}

	    builder.closeEntityListTag();

	    return builder;
	}
}
