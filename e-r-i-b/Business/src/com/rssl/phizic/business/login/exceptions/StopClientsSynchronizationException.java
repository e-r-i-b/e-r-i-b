package com.rssl.phizic.business.login.exceptions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Исключение бросаемое при остановке синхронизации клиента
 * (в переменной ids хранятся идентификаторы найденных персон)
 *
 * @author khudyakov
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class StopClientsSynchronizationException extends BusinessException
{
	private static final String DEFAULT_EMPTY_COUNT_PERSONS_MESSAGE = "Не найдено ни одного клиента по заданным параметрам. Пожалуйста, проверьте указанные сведения, а также номер территориального банка, в котором обслуживается клиент.";
	private static final String DEFAULT_COUNT_PERSONS_MESSAGE = "Найдено более одного клиента и подразделения этих клиентов не поддерживают БП.";

	private List<Long> ids = new ArrayList<Long>();     //идентификаторы найденных персон
	private boolean showMessage;                        //показывать ли сообщение об ошибке клиенту


	public StopClientsSynchronizationException(String message)
	{
		super(message);
	}

	public StopClientsSynchronizationException(Throwable cause)
	{
		super(cause);
	}

	public StopClientsSynchronizationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public StopClientsSynchronizationException(List<ActivePerson> persons, String message)
	{
		super(message);

		if (CollectionUtils.isEmpty(persons))
			return;

		for (ActivePerson person : persons)
		{
			ids.add(person.getId());
		}
	}

	public StopClientsSynchronizationException(List<ActivePerson> persons, Throwable cause)
	{
		this(persons, cause.getMessage());
		showMessage = cause instanceof InactiveExternalSystemException;
	}

	/**
	 * @return идентификаторы найденных персон
	 */
	public List<Long> getIds()
	{
		return Collections.unmodifiableList(ids);
	}

	/**
	 * @return выводить ли сообщение об ошибке клиенту
	 */
	public boolean isShowMessage()
	{
		return showMessage;
	}

	/**
	 * @param persons список клиентов (персон)
	 * @return сообщение по умолчанию
	 */
	public static String getDefaultMessage(List<ActivePerson> persons)
	{
		return CollectionUtils.isEmpty(persons) ? DEFAULT_EMPTY_COUNT_PERSONS_MESSAGE : DEFAULT_COUNT_PERSONS_MESSAGE;
	}
}
