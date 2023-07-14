package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonDataForEmployee;
import com.rssl.phizic.business.csa.IncognitoService;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.operations.EditEntityOperation;

/**
 * редактирование статуса приватности клиентов
 * @author basharin
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 */

public class EditIncognitoStateOperation extends PersonExtendedInfoOperationBase implements EditEntityOperation<ActivePerson, UserRestriction>
{
	private IncognitoState state;

	public void initialize(IncognitoState state) throws BusinessException
	{
		this.state = state;
	}

	public ActivePerson getEntity() throws BusinessException
	{
		return getPerson();
	}
	/**
	 * сохранение настройки "Инкогнито" для текущего клиента
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		if (getClientIncognito() == state)
			return;

		PersonContext.getPersonDataProvider().setPersonData(new StaticPersonDataForEmployee(getPerson()));
		IncognitoService.changeIncognitoSetting(getPerson(), state);
		PersonContext.getPersonDataProvider().setPersonData(null);
	}
}
