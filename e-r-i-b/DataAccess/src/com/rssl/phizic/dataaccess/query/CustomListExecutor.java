package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Интерфейс запроса данных грида
 */
public interface CustomListExecutor<E>
{
	/**
	 * @param parameters - параметры фильтрации данных
	 * @param size - размер коллекции
	 * @param offset - смещение
	 * @param orderParameters - параметры сортировки данных
	 * @return данные для грида
	 */
	public List<E> getList(Map<String,Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException;
}
