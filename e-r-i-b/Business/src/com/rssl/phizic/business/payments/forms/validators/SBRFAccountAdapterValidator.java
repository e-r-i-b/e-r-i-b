package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.routing.AdapterService;

import java.util.Map;

/**
 * @ author: filimonova
 * @ created: 27.01.2011
 * @ $Author$
 * @ $Revision$
 * Счет у клиента СБОЛ должен находится в том же ОСБ, что и сам клиент: проверяется по адаптеру
 */
public class SBRFAccountAdapterValidator extends AccountPresenceValidator
{
	private static final PersonService personService = new PersonService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final AdapterService adapterService = new AdapterService();
	
	public boolean validate(Map values) throws TemporalDocumentException
	{
		String accountId = (String) retrieveFieldValue(FIELD_O1, values);
	    String clientId = (String) retrieveFieldValue(FIELD_O2, values);


		Department department = null;
		ActivePerson person = null;
		try
		{
			person = personService.findByClientId(clientId);
			department = departmentService.findById(person.getDepartmentId());
		}
		catch (BusinessException ex)
		{
			throw new RuntimeException(ex);
		}
		if (person.getCreationType() == CreationType.SBOL)
		{
			try{
				Account account = getAccountByNumber(accountId, department, person);
				if (account!=null)
				{
					String clientAdapter = adapterService.getAdapterUUIDByOffice(department);
					String accountAdapter = adapterService.getAdapterUUIDByOffice(account.getOffice());
					if (!clientAdapter.equals(accountAdapter))
					{
						currentMessage.set("Департамент, в котором заведен клиент не совпадает с департаментом счета");
						return false;
					}
				}
			}catch (GateException e)
			{
				throw new RuntimeException(e);
			}
		}
		return true;
	}

}
