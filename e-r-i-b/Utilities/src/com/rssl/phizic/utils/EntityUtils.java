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
	 * Собирает сет из идентификаторов указанных сущностей
	 * @param entities - коллекция сущностей
	 * @return сет с идентификаторами сущностей (never null)
	 */
	public static <T extends Entity> Set<Long> collectEntityIds(Collection<T> entities)
	{
		Set<Long> ids = new LinkedHashSet<Long>(entities.size());
		for (T entity : entities)
			ids.add(entity.getId());
		return ids;
	}

	/**
	 * Собирает мап "id -> entity" из указанных сущностей
	 * @param entities - коллекция сущностей
	 * @return мап "id -> entity"
	 */
	public static <T extends Entity> Map<Long, T> mapEntitiesById(Collection<T> entities)
	{
		Map<Long, T> map = new HashMap<Long,T>(entities.size());
		for (T entity : entities)
			map.put(entity.getId(), entity);
		return map;
	}

	/**
	 * Ищет сущность по ID в коллекции
	 * @param entities - коллекция сущностей
	 * @param id - ID сущности
	 * @return сущность либо null, если не найдено
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
	 * Проверяет две сущности на эквивалетность по ID
	 * @param e1 - сущность раз
	 * @param e2 - сущность два
	 * @return true - ID сущности раз равен ID сущности два
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
