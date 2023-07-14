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
 * �������� ������ ��������, ���������� ��������� xml
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
	 * @return ����
	 */
	String getKey()
	{
		return key;
	}

	/**
	 * ������ ����
	 * @param key ����
	 */
	void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * @return ��������
	 */
	String getName()
	{
		return name;
	}

	/**
	 * ������ ��������
	 * @param name ��������
	 */
	void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ��������
	 */
	ServicesGroupCategory getCategory()
	{
		return category;
	}

	/**
	 * ������ ��������
	 * @param category ��������
	 */
	void setCategory(ServicesGroupCategory category)
	{
		this.category = category;
	}

	/**
	 * @return �������
	 */
	List<ServiceInformation> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * �������� ������
	 * @param service ������
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
	 * @return �������� ������
	 */
	List<XMLServicesGroupEntity> getChildren()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return children;
	}

	/**
	 * �������� �������� ������
	 * @param group ������
	 */
	void addChild(XMLServicesGroupEntity group)
	{
		children.add(group);

	}

	/**
	 * �������� �������� ������
	 * @param groups ������
	 */
	void addChildren(List<XMLServicesGroupEntity> groups)
	{
		if (groups == null)
			return;

		for (XMLServicesGroupEntity group : groups)
			addChild(group);
	}

	/**
	 * @return �������
	 */
	long getOrder()
	{
		return order;
	}

	/**
	 * ������ �������
	 * @param order �������
	 */
	void setOrder(long order)
	{
		this.order = order;
	}
}
