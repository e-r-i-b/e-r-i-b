package com.rssl.phizic.business.xslt.lists.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 12.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class EntityList implements Entityes
{
	private static final String OPEN_TAG  = "<entity-list>";
	private static final String CLOSE_TAG = "</entity-list>";
	private List<Entity> entityList;

	/**
	 * ����������� ��� �������� ������ ���������
	 */
	public EntityList()
	{
		entityList = new ArrayList<Entity>();
	}

	public void addEntity(Entity entity)
	{
		entityList.add(entity);
	}

	/**
	 * �������� ������ ������
	 * @param entityList ������ ������
	 */
	public void addEntityList(EntityList entityList)
	{
		this.entityList.addAll(entityList.entityList);
	}

	/**
	 * @return ���� �� ������ ���������
	 */
	public boolean isEmpty()
	{
		return entityList.isEmpty();
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append(OPEN_TAG);
		for (Entity entity: entityList)
		{
			buf.append(entity.toString());
		}
		buf.append(CLOSE_TAG);
		return buf.toString();
	}
}
