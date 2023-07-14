package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Calendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Gainanov
 * @ created 15.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class CurrentDateSource implements EntityListSource
{
	private EntityListBuilder getEntityListBuilder(Map<String, String> params)
	{
		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();
		builder.openEntityTag("year");
		Calendar currentDate = DateHelper.getCurrentDate();
		builder.appentField("value", String.valueOf(currentDate.get(Calendar.YEAR)));
		builder.closeEntityTag();
		builder.openEntityTag("month");
		builder.appentField("value", String.valueOf(currentDate.get(Calendar.MONTH)));
		builder.closeEntityTag();
		builder.openEntityTag("day");
		builder.appentField("value", String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));
		builder.closeEntityTag();
		builder.closeEntityListTag();
		return builder;
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
}
