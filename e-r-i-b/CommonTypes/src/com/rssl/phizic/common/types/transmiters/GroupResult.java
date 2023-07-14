package com.rssl.phizic.common.types.transmiters;

/**
 * @ author: filimonova
 * @ created: 20.04.2010
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.common.types.exceptions.IKFLException;

import java.util.*;
import java.io.Serializable;

/**
 * Контейнер для результатов групповой операции.
 * K - класс ключа при запросе групповой операции
 * V - результат групповой операции.
 */

public class GroupResult<K,V> implements Serializable
{
	 private Map<K,V> results;
	 private Map<K,IKFLException> exceptions;
	 private boolean isOneError;

	 public GroupResult()
	 {
		  results = new LinkedHashMap<K,V>();
		  exceptions = new LinkedHashMap<K, IKFLException>();
	 }

	 public GroupResult(GroupResult<K,V> groupResult)
	 {
		 if (groupResult!=null)
		 {
			 this.results = groupResult.getResults();
			 this.exceptions = groupResult.getExceptions();
			 this.isOneError = groupResult.isOneError();
		 }
	 }

	/**
	  * Получить успешный результат по ключу.
	  * null - если выполнение этой операции по ключу было выполнено неуспешно.
	  *
	  * @param key Ключ для поиска результата
	  * @return результат операции или null, если операция была выполнена неуспешно
	  */
	 public V getResult(K key) {
		return results.get(key);
	 }

	 /**     
	  * Добавить GroupResult
	  *
	  * @param groupResult
	  * @return
	  */
	 public void add(GroupResult<K,V> groupResult)
	 {
		 if (groupResult!=null)
		 {
			 this.results.putAll(groupResult.getResults());
			 this.exceptions.putAll(groupResult.getExceptions());
		 }
	 }

	 /**
	  * Получить исключение для неудачной операции по ключу.
	  *
	  * @param key Ключ для поиска результата
	  * @return исключение, которое было при выполнении операции или null, если операция была выполнена успешно
	  */
	 public IKFLException getException(K key) {
	    return exceptions.get(key);
	 }

	 /**
	  * Получить все ключи групповой операции.
	  *
	  * @return все ключи групповой операции
	  */
	 public List<K> getKeys() {
		List<K> list = new ArrayList<K>();
		list.addAll(results.keySet());
		list.addAll(exceptions.keySet());
	    return list;
	 }

	/**
	 * Проверить наличие ключа
	 * @param key ключ
	 * @return true - есть, false - нет
	 */
	public boolean containsKey(K key)
	{
		return results.containsKey(key) || exceptions.containsKey(key);
	}

	 /**
	  * Получить все результаты успешных операций и соотвествующие им ключи.
	  *
	  * @return map значений или пустой мап, если все операции были выоплненны с ошибкой.
	  */
	 public Map<K,V> getResults() {
	    return results;
	 }

	 /**
	  * Установить успешные результаты операций
	  *
	  * @param results Успешные результаты операций.
	  */
	 public void setResults(Map<K,V> results)
	 {
		 this.results = results;
	 }

	 /**
	  * Получить все неуспешные операции
	  *
	  * @return map исключений или пустой мап.
	  */
	 public Map<K,IKFLException> getExceptions()
	 {
	    return exceptions;
	 }

	 /**
	  * Установить неуспешные операции.
	  *
	  * @param exceptions Ошибки при неудачных операциях
	  */
	 public void setExceptions(Map<K,IKFLException> exceptions)
	 {
	    this.exceptions = exceptions;
	 }

	 /**
	  * Сохранить успешный результат.
	  *
	  * @param key Ключ
	  * @param value Результат
	  * @exception DublicateKeyException
	  */
	 public void putResult(K key, V value) throws DublicateKeyException
	 {
		 if (results.containsKey(key) || exceptions.containsKey(key)) throw new DublicateKeyException("Dublicate Key in GroupResult");
		 results.put(key,value);
	 }

	 /**
	  *
	  * @param key Ключ
	  * @param exception Ошибка при выполнении операции
	  * @exception DublicateKeyException
	  */
	 public void putException(K key, IKFLException exception) throws DublicateKeyException
	 {
		 if (exceptions.containsKey(key) || results.containsKey(key)) throw new DublicateKeyException("Dublicate Key in GroupResult");
		 exceptions.put(key, exception);
	 }

	 /**
	  * Одинакова ли ошибка для всех сущностей?
	  *
	  * @return true - если ошибка одинакова, null - если ошибок нет
	  */
	 public Boolean isOneError()
	 {
	    return isOneError;
	 }

	 /**
	  * Установить одинакова ли ошибка для всех сущностей?
	  *
	  * @param isOneError Одинакова ли ошибка для всех сущностей?
	  */
	 public void setOneError(Boolean isOneError)
	 {
	    this.isOneError = isOneError;
	 }

	/**
	 * Возвращает одинаковую ошибку для всех сущностей
	 *
	 * @return
	 * @throws IllegalStateException
	 */
	public IKFLException getOneErrorException() throws IllegalStateException
	{
		if (isOneError)
			return exceptions.values().iterator().next();
		throw new IllegalStateException("GroupResult contains not equal errors");
	}

	/**
	 * Возвращает размер контейнера
	 * @return количество результатов + количество исключений
	 */
	public int size()
	{
		return results.size() + exceptions.size();
	}

	/**
	 * Очистка контейнера
	 */
	public void clear()
	{
		results.clear();
		exceptions.clear();
		isOneError = false;
	}
}
