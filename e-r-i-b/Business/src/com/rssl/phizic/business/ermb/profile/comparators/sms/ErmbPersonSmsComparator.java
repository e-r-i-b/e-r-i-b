package com.rssl.phizic.business.ermb.profile.comparators.sms;

import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.time.DateUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author EgorovaA
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор, проверяющий, изменились ли определенные сведения о клиента
 * (изменение этих данных должно приводить к отправке смс с измененными полями)
 */
public class ErmbPersonSmsComparator implements ErmbSmsChangesComparator<Person>
{
	/**
	 * Сравнивает 2 профиля и возвращает название измененных параметров
	 *
	 * @param oldPerson старый профиль
	 * @param newPerson новый профиль
	 * @return Измененный параметры
	 */
	public Set<String> compare(Person oldPerson, Person newPerson)
	{
		Set<String> resultFields = new HashSet<String>();

		if (oldPerson == null || newPerson == null)
			return resultFields;

		if (!StringHelper.equalsNullIgnore(oldPerson.getSurName(), newPerson.getSurName())
				|| !StringHelper.equalsNullIgnore(oldPerson.getFirstName(), newPerson.getFirstName())
				|| !StringHelper.equalsNullIgnore(oldPerson.getPatrName(), newPerson.getPatrName()))
			resultFields.add(FIO);

		if (!DateUtils.isSameDay(oldPerson.getBirthDay(), newPerson.getBirthDay()))
			resultFields.add(DR);

		PersonDocument oldDoc = PersonHelper.getMainPersonDocument(oldPerson);
		PersonDocument newDoc = PersonHelper.getMainPersonDocument(newPerson);
		if (!(oldDoc == null && newDoc == null))
		{
			if (oldDoc == null ^ newDoc == null
					|| !oldDoc.getId().equals(newDoc.getId())
					|| !oldDoc.getDocumentType().equals(newDoc.getDocumentType())
					|| !StringHelper.equals(oldDoc.getDocumentNumber(), newDoc.getDocumentNumber())
					|| !StringHelper.equals(oldDoc.getDocumentSeries(), newDoc.getDocumentSeries()))
				resultFields.add(DUL);
		}

		return resultFields;
	}
}
