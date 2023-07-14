package com.rssl.phizic.web.common.client.ext.sbrf;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.employee.EmployeeService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lepihina
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Установка значений в PersonContext при входе клиента
 */
public class SetupPersonContextFieldsAction implements AthenticationCompleteAction
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void execute(AuthenticationContext context) throws SecurityException
	{
		setManager();
	}

	/**
	 * Установка менеджера, привязанного к клиенту
	 */
	private void setManager()
	{
		try
		{
			PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
			if (personDataProvider == null)
				return;
			PersonData data = personDataProvider.getPersonData();
			if (data == null)
				return;
			ActivePerson currentPerson = data.getPerson();
			if (currentPerson == null)
				return;

			String managerId = currentPerson.getManagerId();
			if (StringHelper.isNotEmpty(managerId))
				data.setManager(getEmployeeService().getManagerInfo(managerId));
		}
		catch (Exception e)
		{
			log.error("Ошибка получения сотрудника по managerId", e);
		}
	}

	private EmployeeService getEmployeeService()
	{
		return GateSingleton.getFactory().service(EmployeeService.class);
	}
}
