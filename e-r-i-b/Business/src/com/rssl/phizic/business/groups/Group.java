package com.rssl.phizic.business.groups;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.common.types.AbstractEntity;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 08.11.2006 Time: 14:57:11 To change this template use
 * File | Settings | File Templates.
 */

/**
 * группирование пользователей
 */
public class Group extends AbstractEntity
{
	private String category;// AccessCategory
	private Department department;
	private String name;
	private String description;
	private long priority;
	private String systemName;
	private Skin skin;

	/**
	 * @return департамент, который содержит пользователей из этой группы.
	 */
	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}
	/**
	 * @return тип объектов, который содержит группа
	 */
	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return имя группы
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return описание группы
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Приоритет. Чем выше значение, тем он выше
	 * @return Long
	 */
	public long getPriority()
	{
		return priority;
	}

	public void setPriority(long priority)
	{
		this.priority = priority;
	}

	/**
	 * Возвращает системное имя группы
	 * @return String
	 */
	public String getSystemName()
	{
		return systemName;
	}

	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}

	/**
	 * Получить скин установленный для группы по-умолчанию
	 * @return Skin стиль по-умолчанию
	 */
	public Skin getSkin()
	{
		return skin;
	}

	public void setSkin(Skin skin)
	{
		this.skin = skin;
	}
}
