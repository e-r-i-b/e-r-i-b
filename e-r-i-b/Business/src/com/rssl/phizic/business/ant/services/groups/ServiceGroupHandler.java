package com.rssl.phizic.business.ant.services.groups;

import com.rssl.phizic.business.services.groups.ServiceInformation;
import com.rssl.phizic.business.services.groups.ServiceMode;
import com.rssl.phizic.business.services.groups.ServicesGroupCategory;
import com.rssl.phizic.utils.StringHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Stack;

/**
 * Хендлер для парсинга operations-tree.xml
 * @author komarov
 * @ created 29.04.2015
 * @ $Author$
 * @ $Revision$
 */
public class ServiceGroupHandler extends DefaultHandler
{

	private static final String SERVICES_GROUPS     = "services-groups";
	private static final String SERVICES_GROUP      = "services-group";
	private static final String SERVICE_REF         = "service-ref";
	private static final String ACTION              = "action";

	private static final String SERVICES_GROUP_KEY      = "key";
	private static final String SERVICES_GROUP_NAME     = "name";
	private static final String SERVICES_GROUP_CATEGORY = "category";

	private static final String SERVICE_REF_KEY  = "key";
	private static final String SERVICE_REF_MODE = "mode";

	private final Stack<XMLServicesGroupEntity> groups = new Stack<XMLServicesGroupEntity>();

	/**
	 * Конструктор
	 */
	public ServiceGroupHandler()
	{
		super();
		groups.push(new XMLServicesGroupEntity());
	}

	/**
	 * Возвращает результат
	 * @return результат
	 */
	public List<XMLServicesGroupEntity> getResult()
	{
		return groups.pop().getChildren();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if(SERVICES_GROUP.equals(qName))
		{
			ServicesGroupCategory category = null;
			if(StringHelper.isNotEmpty(attributes.getValue(SERVICES_GROUP_CATEGORY)))
				category = ServicesGroupCategory.valueOf(attributes.getValue(SERVICES_GROUP_CATEGORY));
			else
				category = groups.peek().getCategory();
			XMLServicesGroupEntity entry = createEntry(attributes, category, false, groups.peek().getChildren().size());
			groups.peek().addChild(entry);
			groups.push(entry);
		}
		else if(ACTION.equals(qName))
		{
			XMLServicesGroupEntity entry = createEntry(attributes, groups.peek().getCategory(), true,  groups.peek().getChildren().size());
			groups.peek().addChild(entry);
			groups.push(entry);
		}
		else if(SERVICE_REF.equals(qName))
		{
			ServiceInformation service = new ServiceInformation();
			groups.peek().getServices().add(service);
			service.setKey(attributes.getValue(SERVICE_REF_KEY));
			service.setMode(ServiceMode.valueOf(attributes.getValue(SERVICE_REF_MODE)));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if(SERVICES_GROUP.equals(qName))
		{
			groups.pop();
		}
		else if(ACTION.equals(qName))
		{
			groups.pop();
		}
		else if(SERVICES_GROUPS.equals(qName))
		{
			if(groups.size() != 1)
				throw new SAXException("Неверный формат xml");
		}
	}

	private XMLServicesGroupEntity createEntry(Attributes attributes, ServicesGroupCategory category, boolean isAction, long order)
	{
		XMLServicesGroupEntity group = new XMLServicesGroupEntity();
		group.setKey(attributes.getValue(SERVICES_GROUP_KEY));
		group.setName(attributes.getValue(SERVICES_GROUP_NAME));
		group.setCategory(category);
		group.setAction(isAction);
		group.setOrder(order);
		return group;
	}
}
