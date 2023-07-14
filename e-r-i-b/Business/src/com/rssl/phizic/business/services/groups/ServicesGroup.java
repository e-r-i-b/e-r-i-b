package com.rssl.phizic.business.services.groups;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Группа сервисов
 */

public class ServicesGroup
{
	private Long id;
	private String key;
	private String name;
	private Long parentId;
	private ServicesGroupCategory category;
	private List<ServiceInformation> services;
	private boolean isAction;
	private long order;

	/**
	 * @return идентификатор группы
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор группы
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ключ группы
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * задать ключ группы
	 * @param key ключ
	 */
	public void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * @return название группы
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название группы
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return идентификатор родительской группы
	 */
	public Long getParentId()
	{
		return parentId;
	}

	/**
	 * задать идентификатор родительской группы
	 * @param parentId идентификатор родительской группы
	 */
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	/**
	 *
	 * @return категория группы
	 */
	public ServicesGroupCategory getCategory()
	{
		return category;
	}

	/**
	 * задать категорию группы
	 * @param category категория
	 */
	public void setCategory(ServicesGroupCategory category)
	{
		this.category = category;
	}

	/**
	 * @return сервисы группы
	 */
	public List<ServiceInformation> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * задать сервисы группы
	 * @param services сервисы
	 */
	public void setServices(List<ServiceInformation> services)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.services = services;
	}

	/**
	 * обновить сервисы просмотра
	 * @param services сервисы
	 */
	public void updateServices(List<ServiceInformation> services)
	{
		if (this.services == null)
			this.services = new ArrayList<ServiceInformation>();
		else
			this.services.clear();

		this.services.addAll(services);
	}

	/**
	 * @return признак "действие" (true - "действие", false - "не действие")
	 */
	public boolean getIsAction()
	{
		return isAction;
	}

	/**
	 * задать признак "действие" для группы
	 * @param action наличие признака (true - "действие", false - "не действие")
	 */
	public void setIsAction(boolean action)
	{
		isAction = action;
	}

	/**
	 * @return порядок группы
	 */
	public long getOrder()
	{
		return order;
	}

	/**
	 * задать порядок группы
	 * @param order порядок
	 */
	public void setOrder(long order)
	{
		this.order = order;
	}
}
