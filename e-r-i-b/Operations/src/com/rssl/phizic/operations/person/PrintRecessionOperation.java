package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;

/**
 * User: Omeliyanchuk
 * Date: 01.09.2006
 * Time: 10:25:37
 */
public class PrintRecessionOperation extends PersonOperationBase
{
	protected static final PersonService service = new PersonService();
	/**
	 * ѕрверка ограничений
	 * @param restriction ограничение
	 * @param person пользователь
	 * @throws com.rssl.phizic.business.operations.restrictions.RestrictionViolationException если пользователь не проходит ограничени€
	 */
	public static void checkRestriction(UserRestriction restriction,  ActivePerson person) throws RestrictionViolationException, BusinessException
	{
		if(!restriction.accept(person))
			throw new RestrictionViolationException(" ѕользователь ID= " + person.getDepartmentId());
	}

	public ActivePerson getPerson(Long id) throws BusinessException, BusinessLogicException
	{
		setPersonId(id);

		ActivePerson temp = (ActivePerson) service.findById(id, getInstanceName());

		if (temp == null)
			throw new BusinessException("ѕользователь с id" + id + " не найден");

		checkRestriction(getRestriction(), temp);

		return temp;
	}
}
