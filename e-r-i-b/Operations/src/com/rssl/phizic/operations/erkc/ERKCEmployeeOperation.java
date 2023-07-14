package com.rssl.phizic.operations.erkc;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.csa.ProfileService;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeData;

/**
 * @author akrenev
 * @ created 11.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция работы сотрудника ЕРКЦ
 */

public class ERKCEmployeeOperation extends ERKCEmployeeOperationBase
{
	private static final ProfileService profileService = new ProfileService();

	private ActivePerson person;

	/**
	 * инициализация операции
	 * @param person текущий клиент
	 */
	public void initialize(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		this.person = person;
		if (!needUpdateContext(person))
			return;

		initializeFilterParams(person);
		initializeCSAPersonInfo(profileService.findProfileWithFullNodeInfo(getTemplateProfile()));
	}

	/**
	 * Создать контекст сотрудника ЕРКЦ
	 * @throws BusinessException
	 */
	public void createERKCContext() throws BusinessException, BusinessLogicException
	{
		if (!needUpdateContext(person))
			return;

		initializeERKCContext();
	}

	private boolean needUpdateContext(ActivePerson person)
	{
		ERKCEmployeeData employeeData = getErkcEmployeeData();
		Long userLoginId = employeeData.getUserLoginId();
		return userLoginId == null || !userLoginId.equals(person.getLogin().getId());
	}
}
