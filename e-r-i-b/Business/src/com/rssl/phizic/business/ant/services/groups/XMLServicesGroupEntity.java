package com.rssl.phizic.business.ant.services.groups;

import com.rssl.phizic.business.services.groups.ServiceInformation;
import com.rssl.phizic.business.services.groups.ServicesGroupCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * сущность группы сервисов, полученная парсингом xml
 */

class XMLServicesGroupEntity
{
	private String key;
	private String name;
	private ServicesGroupCategory category;
	private List<ServiceInformation> services = new ArrayList<ServiceInformation>();
	private boolean isAction;
	private long order;
	private List<XMLServicesGroupEntity> children = new ArrayList<XMLServicesGroupEntity>();

	/**
	 * @return ключ
	 */
	String getKey()
	{
		return key;
	}

	/**
	 * задать ключ
	 * @param key ключ
	 */
	void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * @return название
	 */
	String getName()
	{
		return name;
	}

	/**
	 * задать название
	 * @param name название
	 */
	void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return категрия
	 */
	ServicesGroupCategory getCategory()
	{
		return category;
	}

	/**
	 * задать категрию
	 * @param category категрия
	 */
	void setCategory(ServicesGroupCategory category)
	{
		this.category = category;
	}

	/**
	 * @return сервисы
	 */
	List<ServiceInformation> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * добавить сервис
	 * @param service сервис
	 */
	void addService(ServiceInformation service)
	{
		services.add(service);
	}

	boolean isAction()
	{
		return isAction;
	}

	void setAction(boolean action)
	{
		isAction = action;
	}

	/**
	 * @return дочерние группы
	 */
	List<XMLServicesGroupEntity> getChildren()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return children;
	}

	/**
	 * добавить дочернюю группу
	 * @param group группа
	 */
	void addChild(XMLServicesGroupEntity group)
	{
		children.add(group);

	}

	/**
	 * добавить дочерние группы
	 * @param groups группы
	 */
	void addChildren(List<XMLServicesGroupEntity> groups)
	{
		if (groups == null)
			return;

		for (XMLServicesGroupEntity group : groups)
			addChild(group);
	}

	/**
	 * @return порядок
	 */
	long getOrder()
	{
		return order;
	}

	/**
	 * задать порядок
	 * @param order порядок
	 */
	void setOrder(long order)
	{
		this.order = order;
	}
}
