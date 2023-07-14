package com.rssl.phizic.business.ermb.profile.comparators.mss;

import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.time.DateUtils;

/**
 * @author EgorovaA
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор, проверяющий, изменились ли определенные сведения о клиенте,синхронизируемые с СОС
 * (изменение этих данных должно приводить к увеличению ЕРМБ-профиля клиента)
 */
public class ErmbPersonUpdateComparator
{
	/**
	 * @param oldPerson старый профиль
	 * @param newPerson новый профиль
	 * @return изменились ли поля для СОС
	 */
	public boolean changed(Person oldPerson, Person newPerson)
	{
		if (oldPerson == null || newPerson == null)
			return true;

		//ФИО
		if (!StringHelper.equalsNullIgnore(oldPerson.getSurName(), newPerson.getSurName())
				|| !StringHelper.equalsNullIgnore(oldPerson.getFirstName(), newPerson.getFirstName())
				|| !StringHelper.equalsNullIgnore(oldPerson.getPatrName(), newPerson.getPatrName()))
			return true;

		//др
		if (!DateUtils.isSameDay(oldPerson.getBirthDay(), newPerson.getBirthDay()))
			return true;

		//дул
		PersonDocument oldDoc = PersonHelper.getMainPersonDocument(oldPerson);
		PersonDocument newDoc = PersonHelper.getMainPersonDocument(newPerson);
		if (!(oldDoc == null && newDoc == null))
		{
			if (oldDoc == null ^ newDoc == null
					|| !oldDoc.getId().equals(newDoc.getId())
					|| !oldDoc.getDocumentType().equals(newDoc.getDocumentType())
					|| !StringHelper.equals(oldDoc.getDocumentNumber(), newDoc.getDocumentNumber())
					|| !StringHelper.equals(oldDoc.getDocumentSeries(), newDoc.getDocumentSeries()))
				return true;
		}

		//категория клиента
		if (!ErmbHelper.getMssClientCategoryCode(oldPerson).equals(ErmbHelper.getMssClientCategoryCode(newPerson)))
			return true;

		// клиент удбо
		boolean oldUdbo = oldPerson.getCreationType() == CreationType.UDBO;
		boolean newUdbo = newPerson.getCreationType() == CreationType.UDBO;
		if (oldUdbo ^ newUdbo)
			return true;

		return false;
	}
}
