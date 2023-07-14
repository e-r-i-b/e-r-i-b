package com.rssl.phizic.business.documents.metadata.source;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Соурс на основе существующего,
 * при получении значения поле удаляется из соурса
 *
 * @author khudyakov
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class SimpleFieldValueSource implements FieldValuesSource
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final XPath xpath = XPathFactory.newInstance().newXPath();

	private Map<String, String> fieldsMap = new HashMap<String, String>();

	public SimpleFieldValueSource(FieldValuesSource source)
	{
		fieldsMap.putAll(source.getAllValues());
	}

	public SimpleFieldValueSource(Metadata metadata, Document document) throws BusinessException
	{
		for (Field field : metadata.getForm().getFields())
		{
			try
			{
				//TODO переделать
				if (isFieldPresent(getFieldSourceName(field.getSource()), document))
				{
					String source = field != null ? field.getSource() : String.format(BusinessDocument.EXTENDED_FIELD_SOURCE_PATTERN, field.getName());
					fieldsMap.put(field.getName(), xpath.compile(source).evaluate(document.getDocumentElement()));
				}
			}
			catch (XPathExpressionException e)
			{
				throw new BusinessException(e);
			}
		}
	}

	public String getValue(String name)
	{
		return fieldsMap.remove(name);
	}

	public Map<String, String> getAllValues()
	{
		return Collections.unmodifiableMap(fieldsMap);
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return MapUtils.isEmpty(fieldsMap);
	}

	public boolean isMasked(String name)
	{
		return false;
	}

	private boolean isFieldPresent(final String name, Document document)
	{
		if (XmlHelper.tagTest(name, document.getDocumentElement()))
		{
			return true;
		}

		try
		{
			NodeList nodeList = XmlHelper.selectNodeList(document.getDocumentElement(), "extra-parameters/parameter");
			if (nodeList == null)
			{
				return false;
			}

			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element element = (Element) nodeList.item(i);
				if (name.equals(element.getAttribute("name")))
				{
					return true;
				}
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
		return false;
	}

	private String getFieldSourceName(String source)
	{
		int before = source.indexOf("[@name='");
		int after  = source.indexOf("']/@value");
		if (before == -1 || after == -1)
		{
			return source;
		}

		String name = source.substring(before + 8, after);
		if (StringHelper.isEmpty(name))
		{
			return source;
		}
		return name;
	}

}
