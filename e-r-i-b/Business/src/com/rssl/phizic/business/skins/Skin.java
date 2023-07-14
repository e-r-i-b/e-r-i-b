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
	 * Группы, которым доступен этот стиль
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
	 * @return true, если это системный стиль
	 */
	public boolean isSystem()
	{
		return !StringHelper.isEmpty(systemName);
	}

	/**
	 * @return true, если это умолчательный стиль для клиентского приложения либо для приложения сотрудника
	 */
	public boolean isDefaultSkin()
	{
		return client || admin;
	}

	/**
	 * @return true, если это умолчательный стиль клиентского приложения
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
	 * @return true, если это умолчательный стиль приложения сотрудника
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
	 * Возвращает признак является ли стиль общедоступным
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
	 * Возвращает категорию к которой относится скин
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
