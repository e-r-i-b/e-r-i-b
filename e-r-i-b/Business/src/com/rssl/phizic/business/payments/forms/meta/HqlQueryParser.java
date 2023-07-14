package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.xslt.FilteredEntityListSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: hudyakov $
 * @ $Revision: 19380 $
 */

class HqlQueryParser
{
	private static final Map<String, org.hibernate.type.Type> typesMap = initTypes();

	private static Map<String, org.hibernate.type.Type> initTypes()
	{
		Map<String, org.hibernate.type.Type> map = new HashMap<String, org.hibernate.type.Type>();

		map.put(BooleanType.INSTANCE.getName(), Hibernate.BOOLEAN);
		map.put(DateType.INSTANCE.getName()   , Hibernate.DATE);
		map.put(IntegerType.INSTANCE.getName(), Hibernate.BIG_INTEGER);
		map.put(MoneyType.INSTANCE.getName(), Hibernate.BIG_DECIMAL);
		map.put(StringType.INSTANCE.getName(), Hibernate.STRING);
		map.put(LongType.INSTANCE.getName(), Hibernate.LONG);

		return map;
	}

	private Element root;
	private HqlEntityListSource source;
	private Form filterForm;

	public Form getFilterForm()
	{
		return filterForm;
	}

	public void setFilterForm(Form filterForm)
	{
		this.filterForm = filterForm;
	}


	HqlQueryParser(Element root)
	{
		this.root = root;
	}

	FilteredEntityListSource getSource()
	{
		return source;
	}

	HqlQueryParser parse() throws TransformerException, BusinessException
	{
		Element queryTag = XmlHelper.selectSingleNode(root, "hql-query");
		String query = parseQuery(queryTag);
		Element returnKeyElem = XmlHelper.selectSingleNode(queryTag, "returnKey");
		if(returnKeyElem == null)
			throw new TransformerException("Element returnKey required");

		HqlEntityListSource.ReturnProperty returnKey = parseReturnProperty(returnKeyElem);
		HqlEntityListSource.ReturnProperty[] returnProperties = parseReturnProperties(queryTag);
		Map<String,Type> hibernateTypesMap = createHibernateTypesMap();
		source = new HqlEntityListSource(query, returnKey, returnProperties, hibernateTypesMap);

		return this;
	}

	private String parseQuery(Element queryTag)
	{
		return XmlHelper.getElementText(queryTag);
	}

	private HqlEntityListSource.ReturnProperty []  parseReturnProperties(Element queryTag) throws TransformerException
	{
		NodeList nodeList = XmlHelper.selectNodeList(queryTag, "return");
		HqlEntityListSource.ReturnProperty [] returnProperties =
				new HqlEntityListSource.ReturnProperty[ nodeList.getLength() ];

		for ( int i = 0; i < nodeList.getLength(); i++)
		{
			Element element = (Element) nodeList.item(i);
			returnProperties[i] = parseReturnProperty(element);
		}

		return returnProperties;
	}

	private HqlEntityListSource.ReturnProperty parseReturnProperty(Element element) throws TransformerException
	{
		HqlEntityListSource.ReturnProperty returnProperty = new HqlEntityListSource.ReturnProperty();

		String propertyAttr = element.getAttribute("property");
		if(propertyAttr == null)
			throw new TransformerException("Ќе установлен атрибут property у тега return");

		returnProperty.setProperty(propertyAttr);

		String nameAttr = element.getAttribute("name");
		if (!nameAttr.equals(""))
			returnProperty.setName(nameAttr);

		String indexAttr = element.getAttribute("index");

		if ( !indexAttr.equals(""))
			returnProperty.setIndex( Long.parseLong(indexAttr) );
		return returnProperty;
	}


	private Map<String, Type> createHibernateTypesMap()
	{
		Map<String,Type> hibernateTypes = new HashMap<String, Type>();

		for (Field field : filterForm.getFields())
		{
			hibernateTypes.put(field.getName(), toHibernateType(field.getType()));
		}

		return hibernateTypes;
	}

	private static Type toHibernateType(com.rssl.common.forms.types.Type type)
	{
		Type hibernateType = typesMap.get(type.getName());
		if(hibernateType == null)
			throw new RuntimeException("не найдено hibernate тип дл€ типа " + type.getName());
		return hibernateType;
	}
}