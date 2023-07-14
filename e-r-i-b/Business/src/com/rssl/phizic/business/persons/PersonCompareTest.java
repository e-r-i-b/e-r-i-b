package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.person.Person;

import java.util.Collections;
import java.util.List;

/**
 * @author egorova
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class PersonCompareTest  extends BusinessTestCaseBase
{
	/**
	 * Проверка сортировки клиента
	 */
	public void test()
	{
		PersonService personService = new PersonService();
		SecurityService securityService = new SecurityService();
		try
		{
//			Login login = securityService.getClientLogin("5PS9923E");
//			ActivePerson person = personService.findByLogin(login);
			Person person = personService.findById(1745L);
			List<ActivePerson> persons = personService.getByFIOAndDoc(person.getSurName(), person.getFirstName(), person.getPatrName(), "4532", "657465", person.getBirthDay(), null);
			System.out.println("Получили клиентов. Количество клиентов = " + persons.size());
			System.out.println("------------------------------Клиенты ДО сортировки-----------------------------");
			writePersons(persons);
			Collections.sort(persons, new PersonAgreementTytpeAndDateComparator());
			System.out.println("-----------------------------Клиенты ПОСЛЕ сортировки---------------------------");
			writePersons(persons);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	private void writePersons(List<ActivePerson> persons)
	{
		int i = 1;
		for (ActivePerson person : persons)
		{
			System.out.println(i + ". ID = " + person.getId() + "; Тип создания:" + person.getCreationType() + "; Дата заключения договора:" + DateHelper.formatDateWithStringMonth(person.getAgreementDate()) + "; Дата начала обслуживания:" + DateHelper.formatDateWithStringMonth(person.getServiceInsertionDate()));
			i++;
		}
	}
}
