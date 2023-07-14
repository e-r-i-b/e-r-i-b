package com.rssl.phizic.business.documents.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.person.Person;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Обертка над Person для работы с ЕСУШ
 *
 * @author khudyakov
 * @ created 20.02.14
 * @ $Author$
 * @ $Revision$
 */
public class PersonWrapper
{
	private static final PersonService personService = new PersonService();

	/**
	 * Найти персону по клиенту (с учетом особенностей хранения сущности клиент в БД блока и ЕСУШ)
	 * @param client клиент
	 * @return персона
	 * @throws BusinessException
	 */
	public static Person getPerson(Client client) throws BusinessException
	{
		List<ActivePerson> persons = personService.getByClient(client);
		if (CollectionUtils.isEmpty(persons))
		{
			return null;
		}
		return persons.get(0);
	}
}
