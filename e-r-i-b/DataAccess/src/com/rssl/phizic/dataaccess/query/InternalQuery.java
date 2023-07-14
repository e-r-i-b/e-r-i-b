package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;

import java.util.List;

/**
 * @author mihaylov
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ¬нутренний интерфейс квери. ¬ него вынесены методы, необходимые только дл€ внутренних механизмов грида.
 */
interface InternalQuery extends Query
{

	/**
	 * ѕолучить список. ¬ рамках данного метода выполн€етс€ сам запрос на получение данных
	 * @param <T> тип данных листа
	 * @return спиоск
	 * @throws DataAccessException
	 */
	public <T> List<T> executeListInternal() throws DataAccessException;

	/**
	 * ”становить параметры сортировки списка
	 * @param parameters - параметры сортировки
	 * @return текуща€ квер€
	 */
	public Query setOrderParameterList(List<OrderParameter> parameters);

}
