package com.rssl.phizic.business.services.groups;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 22.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Информация о группе сервисов
 */

public class ServicesGroupInformation
{
	private Long id;
	private String key;
	private String name;
	private Long parentId;
	private ServicesGroupCategory category;
	private long order;
	private long level;
	private boolean action;
	private List<ServicesGroupInformation> subgroups;
	private List<ServicesGroupInformation> actions;
	private List<ServiceInformation> services;

	ServicesGroupInformation(ServicesGroup servicesGroup)
	{
		this.id = servicesGroup.getId();
		this.key = servicesGroup.getKey();
		this.name = servicesGroup.getName();
		this.order = servicesGroup.getOrder();
		this.subgroups = new ArrayList<ServicesGroupInformation>();
		this.actions = new ArrayList<ServicesGroupInformation>();
		this.services = new ArrayList<ServiceInformation>(servicesGroup.getServices());
		this.category = servicesGroup.getCategory();
		this.action = servicesGroup.getIsAction();
		this.parentId = servicesGroup.getParentId();
	}

	/**
	 * @return идентификатор группы
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return ключ группы
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @return название группы
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return категория группы
	 */
	public ServicesGroupCategory getCategory()
	{
		return category;
	}

	/**
	 * @return порядок группы
	 */
	public long getOrder()
	{
		return order;
	}

	/**
	 * @return подгруппы
	 */
	public List<ServicesGroupInformation> getSubgroups()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return subgroups;
	}

	/**
	 * добавить дочернюю группу
	 * @param subgroup группа
	 */
	void addSubgroup(ServicesGroupInformation subgroup)
	{
		subgroups.add(subgroup);
	}

	/**
	 * добавить дочерние группы
	 * @param subgroups группы
	 */
	void addSubgroups(List<ServicesGroupInformation> subgroups)
	{
		if (subgroups != null)
			this.subgroups.addAll(subgroups);
	}

	/**
	 * @return экшены
	 */
	public List<ServicesGroupInformation> getActions()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return actions;
	}

	/**
	 * добавить действие
	 * @param action действие
	 */
	void addAction(ServicesGroupInformation action)
	{
		actions.add(action);
	}

	/**
	 * добавить действия
	 * @param actions действия
	 */
	void addActions(List<ServicesGroupInformation> actions)
	{
		if (actions != null)
			this.actions.addAll(actions);
	}

	/**
	 * @return сервисы
	 */
	public List<ServiceInformation> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * @return уровень иерархии группы
	 */
	public long getLevel()
	{
		return level;
	}

	/**
	 * задать уровень иерархии группы
	 * @param level уровень
	 */
	public void setLevel(long level)
	{
		this.level = level;
	}

	/**
	 * @return признак "действие" (true - "действие", false - "не действие")
	 */
	public boolean isAction()
	{
		return action;
	}

	/**
	 * @return идентификатор родительской группы
	 */
	public Long getParentId()
	{
		return parentId;
	}
}
