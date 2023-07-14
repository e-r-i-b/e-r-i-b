package com.rssl.phizic.operations.person.search;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.Map;

/**
 * Операция поиска клиента
 *
 * @author khudyakov
 * @ created 17.07.2012
 * @ $Author$
 * @ $Revision$
 */
public interface SearchPersonOperation<R extends Restriction> extends Operation<R>
{
	/**
	 * Инициализация операции
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	void initialize() throws BusinessException, BusinessLogicException;

	/**
	 * Продолжить поиск клиента. Используется в многоблочном поиске.
	 * @param userVisitingMode режим посещения
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	void continueSearch(UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException;

	/**
	 * Инициализация операции
	 * @param identityData - идентификационные данные клиента
     * @param userVisitingMode - режим входа клиента
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	void initialize(Map<String, Object> identityData, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException;

	/**
	 * @return персона
	 */
	ActivePerson getEntity() throws BusinessException, BusinessLogicException;
}
