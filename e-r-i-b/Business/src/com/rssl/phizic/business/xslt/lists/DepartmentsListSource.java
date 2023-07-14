package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.Map;
import java.util.List;
import java.io.StringReader;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

/**
 * @author Omeliyanchuk
 * @ created 01.02.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получение списка подразделений ИКФЛ
 */
public class DepartmentsListSource implements EntityListSource
{
	private static DepartmentService departmentService = new DepartmentService();

	public Source getSource( final Map<String,String> params ) throws BusinessException
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

	private EntityListBuilder getEntityListBuilder( Map<String,String> params ) throws BusinessException
	{
		List<Department> departments = departmentService.getAll();

		EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();

		for (Department department : departments)
		{
			builder.openEntityTag(Long.toString( department.getId() ) );
				builder.appentField("fullName", department.getFullName());
				builder.appentField("name", department.getName());
			builder.closeEntityTag();
		}

	    builder.closeEntityListTag();

	    return builder;
    }
}
