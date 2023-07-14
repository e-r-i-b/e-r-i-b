package com.rssl.phizic.business.dictionaries.offices.extended.replication.tree;

import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.gate.dictionaries.officies.Code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Объектное представление дерева подразделений
 * @author niculichev
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentsTree
{
	//Сущности подразделений по их пути
	private Map<DepartmentPath, DepartmentEntity> entityByPath = new HashMap<DepartmentPath, DepartmentEntity>();
	// Путь до подразделения по его внешнему ключу(нужен для ритейла)
	private Map<Comparable, DepartmentPath> pathBySynchKey = new HashMap<Comparable, DepartmentPath>();

	/**
	 * Получить путь по коду подразделения
	 * @param code под подразделения
	 * @return путь в дереве подразделений
	 */
	public static DepartmentPath createPath(Code code)
	{
		return new DepartmentPath(code);
	}

	/**
	 * создать для дерева подразделений узел, у которого есть соответсвующая запись в БД
	 * @param department подразделение
	 * @return сущность узла
	 */
	public static DepartmentEntity createEntity(ExtendedDepartment department)
	{
		return new DepartmentEntity(department, DepartmentEntity.State.PERSISTENT);
	}

	/**
	 * создать узел для дерева подразделений
	 * @param department подразделение
	 * @param adapterUUID уид адаптера, к которому привязан департамент
	 * @return сущность узла
	 */
	public static DepartmentEntity createNewEntity(ExtendedDepartment department, String adapterUUID)
	{
		return new DepartmentEntity(department, adapterUUID);
	}

	/**
	 * Добавить узел в дерево
	 * @param path путь к узлу
	 * @param entity сущность узла
	 */
	public void addNode(DepartmentPath path, DepartmentEntity entity)
	{
		// добавляем в дерево
		entityByPath.put(path, entity);
	}

	/**
	 * Переместить узел в другую ветку
	 * @param oldPath старая ветка
	 * @param newPath новая ветка
	 */
	public void moveNode(DepartmentPath oldPath, DepartmentPath newPath)
	{
		// изменяем путь
		DepartmentEntity entity = entityByPath.remove(oldPath);
		entityByPath.put(newPath, entity);
	}

	/**
	 * Удалить сущности по ключу
	 * @param pathList
	 */
	public void removeEntities(List<DepartmentPath> pathList)
	{
		for (DepartmentPath path : pathList)
		{
			entityByPath.remove(path);
		}
	}

	/**
	 * Получить сущность подразделения по пути
	 * @param path путь
	 * @return сущность подразделения
	 */
	public DepartmentEntity getNode(DepartmentPath path)
	{
		return entityByPath.get(path);
	}

	/**
	 * Получить путь до подразделения по внешнему ключу
	 * @param synchKey внешний ключ
	 * @return путь до подразделения
	 */
	public DepartmentPath getPath(Comparable synchKey)
	{
		return pathBySynchKey.get(synchKey);
	}

	public Map<DepartmentPath, DepartmentEntity> getEntityList()
	{
		return entityByPath;
	}
}
