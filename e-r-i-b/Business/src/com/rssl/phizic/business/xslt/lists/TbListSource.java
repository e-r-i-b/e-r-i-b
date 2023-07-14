package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Возвращает все ТБ
 *
 * @author EgorovaA
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TbListSource implements EntityListSource
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

	private EntityListBuilder getEntityListBuilder( final Map<String,String> params ) throws BusinessException
	{
		List<Department> departments = departmentService.getTerbanks();

		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		for (Department department : departments)
		{
			ExtendedCodeImpl code = new ExtendedCodeImpl(department.getCode());
			builder.openEntityTag(code.getRegion());
			builder.appentField("name", department.getName());
			builder.closeEntityTag();
		}

		builder.closeEntityListTag();

		return builder;
	}
}
