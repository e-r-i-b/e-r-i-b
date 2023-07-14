package com.rssl.phizic.web.client.ext.sbrf;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.xslt.lists.EntityListBuilder;
import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.struts.forms.ActionMessagesManager;

import java.util.Map;
import java.util.Set;
import java.io.StringReader;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author mihaylov
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ValidationErrorSource  implements EntityListSource
{

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

	private EntityListBuilder getEntityListBuilder(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		Map<String,String> errors = ActionMessagesManager.getFieldsValidationError(ActionMessagesManager.COMMON_BUNDLE,
																					ActionMessagesManager.ERROR);
		Set<String> fields = errors.keySet();
		for(String field: fields)
		{
			builder.openEntityTag(field);
			builder.appentField("text",errors.get(field));
			builder.closeEntityTag();
		}
		
		builder.closeEntityListTag();
		return builder;
	}

}
