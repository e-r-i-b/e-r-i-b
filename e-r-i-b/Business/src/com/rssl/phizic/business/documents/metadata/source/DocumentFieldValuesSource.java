package com.rssl.phizic.business.documents.metadata.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.sun.org.apache.xerces.internal.dom.AttrImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.*;

/**
 * @author Krenev
 * @ created 01.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class DocumentFieldValuesSource implements FieldValuesSource
{
	private static XPathFactory xPathFactory = XPathFactory.newInstance();
	private Map<String, Field> fieldsMap;
	private Document document;

	public DocumentFieldValuesSource(Metadata metadata, BusinessDocument document) throws BusinessException
	{
		try
		{
			this.document = document.convertToDom();
			initData(metadata);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	private void initData(Metadata metadata)
	{
		fieldsMap = new HashMap<String, Field>();

		for (Field field : metadata.getForm().getFields())
		{
			fieldsMap.put(field.getName(), field);
		}
	}

	/**
	 * ¬озвращает значение пол€ по его имени
	 * @param name им€ пол€
	 * @return значение пол€
	 */
	public String getValue(String name)
	{
		Field field = fieldsMap.get(name);
		String xPath = null;
		if (field != null)
		{
			xPath = field.getSource();
		}
		else
		{
			//поле не найдено. пробуем найти его в расширенных атрибутах из предположени€,
			//что сорц используетс€ как источник данных полей
			//при загрузке расширенных метаданных. см BUG021183  
			xPath = String.format(BusinessDocument.EXTENDED_FIELD_SOURCE_PATTERN, name);
		}
		XPath xpath = xPathFactory.newXPath();
		try
		{
			XPathExpression expression = xpath.compile(xPath);
			// Evaluate XPath expression
			Element element = document.getDocumentElement();

			return expression.evaluate(element);
		}
		catch (XPathExpressionException e)
		{
			throw new FormException(e);
		}
	}

	public Map<String, String> getAllValues()
	{
		Map<String, String> values = new HashMap<String, String>(fieldsMap.size());
		for (String name : fieldsMap.keySet()) {
			String value = getValue(name);
			if (value != null)
				values.put(name, value);
		}
		return values;
	}

	public boolean isChanged(String name)
	{
		Field field = fieldsMap.get(name);
		if (field == null)
		{
			throw new FormException("Ќе найдено поле " + name);
		}
		XPath xpath = xPathFactory.newXPath();
		String xPath = field.getSource();
		XPathExpression expression = null;
		try
		{
			expression = xpath.compile(xPath);
			// Evaluate XPath expression
			Element element = document.getDocumentElement();
			Node node = (Node)expression.evaluate(element, XPathConstants.NODE);
			String isChanged = "false";
			if(node instanceof AttrImpl)
			{
				AttrImpl attr = (AttrImpl)node;
				isChanged = attr.getOwnerElement().getAttribute("isChanged");
			}
			if(node instanceof Element)
			{
				Element el = (Element)node;
				isChanged = el.getAttribute("isChanged");;
			}
			if(isChanged == null || isChanged.equals(""))
				return false;
			return Boolean.parseBoolean(isChanged);
		}
		catch (XPathExpressionException e)
		{
			return false;			
// throw new FormException(e);
		}
	}

	public boolean isEmpty()
	{
		return fieldsMap.isEmpty();
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
