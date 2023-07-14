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

@Deprecated //���������� EntityList, Entity, Field
public class EntityBuilder
{
	private Entity entity;

	/**
	 * ��������� ��� entity � ��������� key
	 * @param key �������
	 */
	public void openEntityTag(String key)
	{
		entity = new Entity(key, null);
	}

	/**
	 * ��������� ��� entity � ��������� key � description
	 * @param key ���� ������
	 * @param entityDescription - �������� ������
	 */
	public void openNamedEntityTag(String key, String entityDescription)
	{
		entity = new Entity(key, entityDescription);
	}

	/**
	 *  ��������� ��� field � ��������� name
	 * @param name �������
	 * @param value ��������
	 */
	public void appentField(String name, String value)
	{
		entity.addField(new Field(name, value));
	}

	/**
	 * ��������� ��������� ����� field
	 * @param item ����� ��� name - value
	 * @param prefix ������� ��� ���� ����������� �����
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
	 * �������� �ntity
	 * @return �ntity
	 */
	public Entity getEntity()
	{
		return entity;
	}

	public void closeEntityTag()
	{}
}
