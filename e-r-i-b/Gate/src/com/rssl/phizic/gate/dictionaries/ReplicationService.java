package com.rssl.phizic.gate.dictionaries;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author Krenev
 * @ created 27.05.2009
 * @ $Author$
 * @ $Revision$
 * Сервис используемый для репликации
 */
public interface ReplicationService<E extends DictionaryRecord> extends Service
{
	/**
	 * получить список сущностей в указанном диапазоне
	 * @param firstResult индекс первого элемента
	 * @param maxResults количество элементов
	 * @return список сущностей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	List<E> getAll(int firstResult, int maxResults) throws GateException, GateLogicException;

	/**
	 * получить список сущностей в соответсвии с данным шаблоном
	 * @param template шаблон лдя отбора
	 * @param listLimit количество элементов
	 * @return список сущностей
	 * @throws GateException
	 */
	List<E> getAll(E template, int firstResult, int listLimit) throws GateException, GateLogicException;
}
