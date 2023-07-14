package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.FilterRestriction;

/**
 * Ограничение на работу с пользователями
 * @author Evgrafov
 * @ created 24.08.2006
 * @ $Author: erkin $
 * @ $Revision: 48493 $
 */
public interface UserRestriction extends Restriction, FilterRestriction
{
	/**
	 * Проверка возможности работы с пользователем
	 * @param person пользователь для проверки
	 */
	boolean accept(Person person) throws BusinessException;

}
