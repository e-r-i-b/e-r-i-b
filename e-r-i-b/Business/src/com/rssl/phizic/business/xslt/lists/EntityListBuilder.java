package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Entityes;
import com.rssl.phizic.business.xslt.lists.builder.Field;

import java.util.Stack;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: niculichev $
 * @ $Revision: 49478 $
 */
//TODO ≈сли в EntityListSource метод Source getSource исчезнет,
//то необходимо сразу генерить DOM

@Deprecated //используем EntityList, Entity, Field 
public class EntityListBuilder
{
	private Stack<Entityes> entityesStack;

	/**
	 * конструктор инициализирующий билдер
	 */
	public EntityListBuilder()
	{
		entityesStack = new Stack<Entityes>();
	}

	/**
	 * добавление в лист одной сущности
	 * @param entityBuilder - добавл€ема€ entity
	 */
	public void addEntity(EntityBuilder entityBuilder)
	{
		Entity entity = entityBuilder.getEntity();
		entityesStack.peek().addEntity(entity);
	}

	public void closeEntityListTag()
	{
		if (entityesStack.size() > 1)
		{
			entityesStack.pop();
		}
	}

	public void openEntityListTag()
	{
		EntityList entityList = new EntityList();
		if (entityesStack.size() > 1)
		{
			Entityes entityes = entityesStack.peek();
			if (entityes instanceof Entity)
			{
				entityList = ((Entity)entityes).getEntityList();
			}
		}
		entityesStack.push(entityList);
	}
	
	public void closeEntityTag()
	{
		entityesStack.pop();
	}

	public void openEntityTag(String key)
	{
		Entity entity = new Entity(key, null);
		entityesStack.peek().addEntity(entity);
		entityesStack.push(entity);
	}

	public void openNamedEntityTag(String key, String entityDescription)
	{
		Entity entity = new Entity(key, entityDescription);
		entityesStack.peek().addEntity(entity);
		entityesStack.push(entity);
	}

	public void appentField(String name, String value)
	{
		appentField(name, value, null);
	}

	public void appentField(String name, String value, String prefix)
	{
		((Entity) entityesStack.peek()).addField(new Field(name, value, prefix));
	}

	public void appendObject(Object item, String prefix)
	{
		((Entity) entityesStack.peek()).appendObject(item, prefix);
	}

	public String toString()
	{
		return entityesStack.peek().toString();
	}
}