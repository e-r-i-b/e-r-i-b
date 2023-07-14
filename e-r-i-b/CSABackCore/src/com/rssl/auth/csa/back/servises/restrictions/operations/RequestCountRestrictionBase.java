package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.exceptions.TooManyRequestException;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.OperationState;

/**
 * @author krenev
 * @ created 19.11.2012
 * @ $Author$
 * @ $Revision$
 * Базовый класс ограничений на количетсво неподтвержденных заявок конкретного типа
 */
public abstract class RequestCountRestrictionBase<T extends Operation> implements OperationRestriction<T>
{
	public void check(T operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		if (operation.getClass() != getOperationClass())
		{
			throw new IllegalArgumentException("Неверный тип операции: " + operation.getClass() + ". Ожидается: " + getOperationClass());
		}
		Long profileId = operation.getProfileId();
		int maxRequestCount = getMaxRequestCount();
		int requestCheckInterval = getRequestCheckInterval();
		int count = Operation.getCount(profileId, getOperationClass(), requestCheckInterval, OperationState.NEW, OperationState.REFUSED);
		if (count > maxRequestCount)
		{
			throw new TooManyRequestException(profileId);
		}
	}

	/**
	 * @return Класс подсчитываемых заявок.
	 */
	protected abstract Class<T> getOperationClass();

	/**
	 * @return количество секунд (глубина поиска, начиная от текущего времени) для проверки количество неподтвержденных запросов
	 */
	protected abstract int getRequestCheckInterval();

	/**
	 * @return максимально допустимое количество неподтвержденных запросов за getRequestCheckInterval сек
	 */
	protected abstract int getMaxRequestCount();
}
