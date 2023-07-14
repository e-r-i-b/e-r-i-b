package com.rssl.phizic.locale.replicator;

import com.rssl.phizic.common.types.exceptions.SystemException;

import java.util.List;

/**
 * Интерфейс для репликатора
 * @author komarov
 * @ created 15.09.2014
 * @ $Author$
 * @ $Revision$
 */
public interface ReplicaDestenation<T> extends ReplicaSource<T>
{
	void add   (T newValue) throws SystemException;
	void remove(T oldValue) throws SystemException;
	void update(T value) throws SystemException;

	void add   (List<T> newValue) throws SystemException;
	void remove(List<T> oldValue) throws SystemException;
	void update(List<T> value) throws SystemException;
}
