package com.rssl.phizic.business.services.groups;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��������
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
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���� ������
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * ������ ���� ������
	 * @param key ����
	 */
	public void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * @return �������� ������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ �������� ������
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������������� ������������ ������
	 */
	public Long getParentId()
	{
		return parentId;
	}

	/**
	 * ������ ������������� ������������ ������
	 * @param parentId ������������� ������������ ������
	 */
	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	/**
	 *
	 * @return ��������� ������
	 */
	public ServicesGroupCategory getCategory()
	{
		return category;
	}

	/**
	 * ������ ��������� ������
	 * @param category ���������
	 */
	public void setCategory(ServicesGroupCategory category)
	{
		this.category = category;
	}

	/**
	 * @return ������� ������
	 */
	public List<ServiceInformation> getServices()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return services;
	}

	/**
	 * ������ ������� ������
	 * @param services �������
	 */
	public void setServices(List<ServiceInformation> services)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.services = services;
	}

	/**
	 * �������� ������� ���������
	 * @param services �������
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
	 * @return ������� "��������" (true - "��������", false - "�� ��������")
	 */
	public boolean getIsAction()
	{
		return isAction;
	}

	/**
	 * ������ ������� "��������" ��� ������
	 * @param action ������� �������� (true - "��������", false - "�� ��������")
	 */
	public void setIsAction(boolean action)
	{
		isAction = action;
	}

	/**
	 * @return ������� ������
	 */
	public long getOrder()
	{
		return order;
	}

	/**
	 * ������ ������� ������
	 * @param order �������
	 */
	public void setOrder(long order)
	{
		this.order = order;
	}
}
