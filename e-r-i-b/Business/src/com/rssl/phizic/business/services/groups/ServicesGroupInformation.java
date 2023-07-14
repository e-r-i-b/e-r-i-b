package com.rssl.phizic.business.services.groups;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 22.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ������ ��������
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
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return ���� ������
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @return �������� ������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return ��������� ������
	 */
	public ServicesGroupCategory getCategory()
	{
		return category;
	}

	/**
	 * @return ������� ������
	 */
	public long getOrder()
	{
		return order;
	}

	/**
	 * @return ���������
	 */
	public List<ServicesGroupInformation> getSubgroups()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return subgroups;
	}

	/**
	 * �������� �������� ������
	 * @param subgroup ������
	 */
	void addSubgroup(ServicesGroupInformation subgroup)
	{
		subgroups.add(subgroup);
	}

	/**
	 * �������� �������� ������
	 * @param subgroups ������
	 */
	void addSubgroups(List<ServicesGroupInformation> subgroups)
	{
		if (subgroups != null)
			this.subgroups.addAll(subgroups);
	}

	/**
	 * @return ������
	 */
	public List<ServicesGroupInformation> getActions()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return actions;
	}

	/**
	 * �������� ��������
	 * @param action ��������
	 */
	void addAction(ServicesGroupInformation action)
	{
		actions.add(action);
	}

	/**
	 * �������� ��������
	 * @param actions ��������
	 */
	void addActions(List<ServicesGroupInformation> actions)
	{
		if (actions != null)
			this.actions.addAll(actions);
	}

	/**
	 * @return �������
	 */
	public List<ServiceInformation> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * @return ������� �������� ������
	 */
	public long getLevel()
	{
		return level;
	}

	/**
	 * ������ ������� �������� ������
	 * @param level �������
	 */
	public void setLevel(long level)
	{
		this.level = level;
	}

	/**
	 * @return ������� "��������" (true - "��������", false - "�� ��������")
	 */
	public boolean isAction()
	{
		return action;
	}

	/**
	 * @return ������������� ������������ ������
	 */
	public Long getParentId()
	{
		return parentId;
	}
}
