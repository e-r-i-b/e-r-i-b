package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.template.PackageService;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Krenev
 * @ created 31.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PackageTemplateSource implements EntityListSource
{
	private static final PackageService packageService = new PackageService();

	public Source getSource(final Map<String, String> params) throws BusinessException
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

	private EntityListBuilder getEntityListBuilder(Map<String, String> params) throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		for (PackageTemplate packageTemplate : packageService.getDepartmentPackages(employeeData.getEmployee().getDepartmentId()))
		{
			builder.openEntityTag(Long.toString(packageTemplate.getId()));
			builder.appentField("name", packageTemplate.getName());
			builder.appentField("description", packageTemplate.getDescription());
			builder.closeEntityTag();
		}
		builder.closeEntityListTag();
		return builder;
	}
}
