package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;

/**
 * @author akrenev
 * @ created 06.09.2011
 * @ $Author$
 * @ $Revision$
 */

@Deprecated //используем EntityList, Entity, Field
public class EntityBuilder
{
	private Entity entity;

	/**
	 * открывает таг entity с атрибутом key
	 * @param key атрибут
	 */
	public void openEntityTag(String key)
	{
		entity = new Entity(key, null);
	}

	/**
	 * открывает таг entity с атрибутом key и description
	 * @param key ключ записи
	 * @param entityDescription - описание записи
	 */
	public void openNamedEntityTag(String key, String entityDescription)
	{
		entity = new Entity(key, entityDescription);
	}

	/**
	 *  добавляет таг field с атрибутом name
	 * @param name атрибут
	 * @param value значение
	 */
	public void appentField(String name, String value)
	{
		entity.addField(new Field(name, value));
	}

	/**
	 * добавляет множество тагов field
	 * @param item набор пар name - value
	 * @param prefix префикс для имен добавляемых полей
	 */
	public void appendObject(Object item, String prefix)
	{
		entity.appendObject(item, prefix);
	}

	public void addEntity(Entity entity)
	{
		this.entity.addEntity(entity);
	}

	public void addEntityList(EntityList entityList)
	{
		this.entity.addEntityList(entityList);
	}

	/**
	 * получить еntity
	 * @return еntity
	 */
	public Entity getEntity()
	{
		return entity;
	}

	public void closeEntityTag()
	{}
}
