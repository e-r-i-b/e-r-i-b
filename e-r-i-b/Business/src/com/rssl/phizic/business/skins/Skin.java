package com.rssl.phizic.business.skins;

import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.common.types.AbstractEntity;
import com.rssl.phizic.utils.StringHelper;

import java.util.Set;

/**
 * @author egorova
 * @ created 12.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class Skin extends AbstractEntity
{
	private String name;
	private String url;
	private String systemName;
	private boolean client;
	private boolean admin;
	private boolean common = true;
	private Category category;

	/**
	 * ������, ������� �������� ���� �����
	 */
	private Set<Group> groups;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getSystemName()
	{
		return systemName;
	}

	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}

	/**
	 * @return true, ���� ��� ��������� �����
	 */
	public boolean isSystem()
	{
		return !StringHelper.isEmpty(systemName);
	}

	/**
	 * @return true, ���� ��� ������������� ����� ��� ����������� ���������� ���� ��� ���������� ����������
	 */
	public boolean isDefaultSkin()
	{
		return client || admin;
	}

	/**
	 * @return true, ���� ��� ������������� ����� ����������� ����������
	 */
	public boolean isClientDefaultSkin()
	{
		return client;
	}

	public void setClientDefaultSkin(boolean client)
	{
		this.client = client;
	}

	/**
	 * @return true, ���� ��� ������������� ����� ���������� ����������
	 */
	public boolean isAdminDefaultSkin()
	{
		return admin;
	}

	public void setAdminDefaultSkin(boolean admin)
	{
		this.admin = admin;
	}

	/**
	 * ���������� ������� �������� �� ����� �������������
	 * @return boolean
	 */
	public boolean isCommon()
	{
		return common;
	}

	public void setCommon(boolean common)
	{
		this.common = common;
	}

	/**
	 * ���������� ��������� � ������� ��������� ����
	 * @return Category
	 */
	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	public Set<Group> getGroups()
	{
		return groups;
	}

	public void setGroups(Set<Group> groups)
	{
		this.groups = groups;
	}
}
