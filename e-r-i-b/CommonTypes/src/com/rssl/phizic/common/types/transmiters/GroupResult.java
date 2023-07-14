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
 * ��������� ��� ����������� ��������� ��������.
 * K - ����� ����� ��� ������� ��������� ��������
 * V - ��������� ��������� ��������.
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
	  * �������� �������� ��������� �� �����.
	  * null - ���� ���������� ���� �������� �� ����� ���� ��������� ���������.
	  *
	  * @param key ���� ��� ������ ����������
	  * @return ��������� �������� ��� null, ���� �������� ���� ��������� ���������
	  */
	 public V getResult(K key) {
		return results.get(key);
	 }

	 /**     
	  * �������� GroupResult
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
	  * �������� ���������� ��� ��������� �������� �� �����.
	  *
	  * @param key ���� ��� ������ ����������
	  * @return ����������, ������� ���� ��� ���������� �������� ��� null, ���� �������� ���� ��������� �������
	  */
	 public IKFLException getException(K key) {
	    return exceptions.get(key);
	 }

	 /**
	  * �������� ��� ����� ��������� ��������.
	  *
	  * @return ��� ����� ��������� ��������
	  */
	 public List<K> getKeys() {
		List<K> list = new ArrayList<K>();
		list.addAll(results.keySet());
		list.addAll(exceptions.keySet());
	    return list;
	 }

	/**
	 * ��������� ������� �����
	 * @param key ����
	 * @return true - ����, false - ���
	 */
	public boolean containsKey(K key)
	{
		return results.containsKey(key) || exceptions.containsKey(key);
	}

	 /**
	  * �������� ��� ���������� �������� �������� � �������������� �� �����.
	  *
	  * @return map �������� ��� ������ ���, ���� ��� �������� ���� ���������� � �������.
	  */
	 public Map<K,V> getResults() {
	    return results;
	 }

	 /**
	  * ���������� �������� ���������� ��������
	  *
	  * @param results �������� ���������� ��������.
	  */
	 public void setResults(Map<K,V> results)
	 {
		 this.results = results;
	 }

	 /**
	  * �������� ��� ���������� ��������
	  *
	  * @return map ���������� ��� ������ ���.
	  */
	 public Map<K,IKFLException> getExceptions()
	 {
	    return exceptions;
	 }

	 /**
	  * ���������� ���������� ��������.
	  *
	  * @param exceptions ������ ��� ��������� ���������
	  */
	 public void setExceptions(Map<K,IKFLException> exceptions)
	 {
	    this.exceptions = exceptions;
	 }

	 /**
	  * ��������� �������� ���������.
	  *
	  * @param key ����
	  * @param value ���������
	  * @exception DublicateKeyException
	  */
	 public void putResult(K key, V value) throws DublicateKeyException
	 {
		 if (results.containsKey(key) || exceptions.containsKey(key)) throw new DublicateKeyException("Dublicate Key in GroupResult");
		 results.put(key,value);
	 }

	 /**
	  *
	  * @param key ����
	  * @param exception ������ ��� ���������� ��������
	  * @exception DublicateKeyException
	  */
	 public void putException(K key, IKFLException exception) throws DublicateKeyException
	 {
		 if (exceptions.containsKey(key) || results.containsKey(key)) throw new DublicateKeyException("Dublicate Key in GroupResult");
		 exceptions.put(key, exception);
	 }

	 /**
	  * ��������� �� ������ ��� ���� ���������?
	  *
	  * @return true - ���� ������ ���������, null - ���� ������ ���
	  */
	 public Boolean isOneError()
	 {
	    return isOneError;
	 }

	 /**
	  * ���������� ��������� �� ������ ��� ���� ���������?
	  *
	  * @param isOneError ��������� �� ������ ��� ���� ���������?
	  */
	 public void setOneError(Boolean isOneError)
	 {
	    this.isOneError = isOneError;
	 }

	/**
	 * ���������� ���������� ������ ��� ���� ���������
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
	 * ���������� ������ ����������
	 * @return ���������� ����������� + ���������� ����������
	 */
	public int size()
	{
		return results.size() + exceptions.size();
	}

	/**
	 * ������� ����������
	 */
	public void clear()
	{
		results.clear();
		exceptions.clear();
		isOneError = false;
	}
}
