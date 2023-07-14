package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.Entity;

import java.util.*;

/**
 * @author Erkin
 * @ created 23.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class EntityUtils
{
	/**
	 * �������� ��� �� ��������������� ��������� ���������
	 * @param entities - ��������� ���������
	 * @return ��� � ���������������� ��������� (never null)
	 */
	public static <T extends Entity> Set<Long> collectEntityIds(Collection<T> entities)
	{
		Set<Long> ids = new LinkedHashSet<Long>(entities.size());
		for (T entity : entities)
			ids.add(entity.getId());
		return ids;
	}

	/**
	 * �������� ��� "id -> entity" �� ��������� ���������
	 * @param entities - ��������� ���������
	 * @return ��� "id -> entity"
	 */
	public static <T extends Entity> Map<Long, T> mapEntitiesById(Collection<T> entities)
	{
		Map<Long, T> map = new HashMap<Long,T>(entities.size());
		for (T entity : entities)
			map.put(entity.getId(), entity);
		return map;
	}

	/**
	 * ���� �������� �� ID � ���������
	 * @param entities - ��������� ���������
	 * @param id - ID ��������
	 * @return �������� ���� null, ���� �� �������
	 */
	public static <T extends Entity> T findById(Collection<T> entities, long id)
	{
		for (T entity : entities) {
			if (entity.getId() == id)
				return entity;
		}
		return null;
	}

	/**
	 * ��������� ��� �������� �� �������������� �� ID
	 * @param e1 - �������� ���
	 * @param e2 - �������� ���
	 * @return true - ID �������� ��� ����� ID �������� ���
	 */
	public static boolean equalsById(Entity e1, Entity e2)
	{
		Long id1 = e1.getId();
		Long id2 = e2.getId();
		if (id1 == null || id2 == null)
			return false;
		return id1.equals(id2);
	}
}
