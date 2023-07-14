package com.rssl.phizic.business.tree;

import java.util.List;

/**
 * Элемент дерева: вершина или лист
 * @author lepihina
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public interface TreeElement
{
	/**
	 * Возвращает заголовок
	 * @return заголовок
	 */
	String getName();

	/**
	 * @param name - заголовок
	 */
	void setName(String name);

	/**
	 * Является ли элемент дерева листом
	 * @return true - лист
	 */
	boolean isLeaf();

	/**
	 * Выбран ли текущий элемент дерева
	 * @return true - выбран
	 */
	boolean isSelected();

	/**
	 * Найти элемент дерева по selectedId и отметить, что он выбран
	 * @param selectedId - идентификатор элемента
	 */
	void setSelected(String selectedId);

	/**
	 * Возвращает список из идентификаторов листьев элемента дерева, которые отмечены как выбранные.
	 * Если в дереве выбрана вершина, то возвращаются идентификаторы всех листьев этой вершины.
	 * @return список из идентификаторов листьев дерева
	 */
	List<Long> getSelectedIds();

	/**
	 * Возвращает список всех идентификаторов листьев элемента дерева.
	 * @return список идентификаторов листьев
	 */
	List<Long> getAllIdentifiers();
}
