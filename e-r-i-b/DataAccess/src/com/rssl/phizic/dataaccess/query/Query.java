package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.utils.ListTransformer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 25.11.2005
 * @ $Author$
 * @ $Rev$
 */

public interface Query
{
    /**
     * Установить параметр запроса
     * @param name   имя параметра
     * @param value  значение параметрв
     * @return сам себя
     */
    Query  setParameter(String name, Object value);

    /**
     * Установить параметр запроса
     * @param parameters key - параметр, value - значение
     * @return сам себя
     */
    Query setParameters(Map<String,Object> parameters);

	/**
	 * @param name имя параметра
	 * @return параметр запроса или null, елси он не установлен
	 */
	Object getParameter(String name);

	 /**
     * Получить параметры запроса
     * @return именованые параметры запроса
     */
    public List<String> getNamedParameters()  throws DataAccessException;

	/**
	 * Установить параметр запросы
	 * @param name - имя параметра
	 * @param vals - список значений
	 * @return сам себя
	 */
	public Query setParameterList(String name, Collection vals);

	/**
	 * Установить параметр запросы
	 * @param name - имя параметра
	 * @param  vals - список значений
	 * @return сам себя
	 */
	Query setParameterList(String name, Object[] vals);

	/**
	 * Установить параметры в запросы.
	 * Устанваливается в запросы count параметров с именами prefix+'1', prefix+'2'.. prefix+ count.
	 * Значения берутся из values. если count больше количества значений values, то оставшиеся параметры устанавливаются в null.
	 * Дополнительно устанвливается параметр 'empty_'+prefix, отвечающий на вопрос пуста ли коллекция.
	 * @param prefix префикс именованных параметров
	 * @param values значения параметров
	 * @param count количество параметров
	 * @return сам себя
	 */
	public Query setListParameters(String prefix, Object[] values, int count);

	/**
	 * Установить параметры в запросы.
	 * Устанваливается в запросы count параметров с именами prefix+'1', prefix+'2'.. prefix+ count.
	 * Значения берутся из values. если count больше количества значений values, то оставшиеся параметры устанавливаются в null.
	 * Дополнительно устанвливается параметр 'empty_'+prefix, отвечающий на вопрос пуста ли коллекция.
	 * @param prefix префикс именованных параметров
	 * @param values значения параметров
	 * @param count количество параметров
	 * @return сам себя
	 */
	public Query setListParameters(String prefix, List values, int count);
    /**
     * Установить максимальное количество возвращфемых записей
     * @param val максимальное количество возвращфемых записей
     * @return сам себя
     */
    Query setMaxResults(int val);

    /**
     * Возвращать записи начиная с заданной
     * @param val номер первой возвращаемой записи
     * @return сам себя
     */
    Query setFirstResult(int val);

    /**
     * Выполнить запрос и вернуть список резутьтатов
     * @return список результатов или пустой список
     */
    <T> List<T> executeList() throws DataAccessException;

	/**
	 * Выполнить запрос и вернуть итератор резутьтатов
	 *
	 * @return итератор результатов
	 */
	<T> Iterator<T> executeIterator() throws DataAccessException;

	/**
     * Выполнить запрос и вернуть резутьтат
     * @return результат или null
     */
    <T> T executeUnique() throws DataAccessException;

	/**
	 * TODO закончить реализацию передачи списка элементов в качестве параметра в ENH035791
	 * Выполнить запрос на изменение БД.
	 * @return число обновленных строк (не null)
	 * @throws DataAccessException
	 */
	int executeUpdate() throws DataAccessException;
    /**
     * Установить фильтр определенный в *.hbm
     * @param filterName - имя фильтра
     * @return Filter
     */
    Filter enableFilter(String filterName);

	/**
	 * Ограничения на выборку в виде фильтра
	 * @param restriction - ограничения на выборку в виде фильтра
	 */
	void setFilterRestriction(FilterRestriction restriction);

	/**
	 * Устанавить преобразователь результата
	 * @param transformer - преобразователь списка
	 * NOTE: Размер выходного списка должен быть равен размеру входного списка!
	 */
	<Output, Input> void setListTransformer(ListTransformer<Output, Input> transformer);
}
