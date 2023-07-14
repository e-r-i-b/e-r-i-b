package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ermb.profile.ErmbPersonListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbPersonEvent;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonBase;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.business.persons.PersonKey;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Created by IntelliJ IDEA. User: Roshka Date: 27.09.2005 Time: 16:05:40 */
public class EditPersonOperation extends PersonExtendedInfoOperationBase implements EditEntityOperation<ActivePerson, UserRestriction>
{
	private static final SimpleService simpleService = new SimpleService();

	private ErmbPersonEvent personEvent;
	private PersonKey oldPersonKey;

	public ActivePerson getEntity() throws BusinessException
	{
		return getPerson();
	}

	@Transactional
    public void save() throws BusinessException
    {
        personService.update(getPerson(),getInstanceName());
	    if (personEvent != null)
	    {
		    // Если у клиента нет ЕРМБ-профиля, то проверка не нужна
		    if (personEvent.getProfile() != null)
		    {
				personEvent.setNewPerson(getEntity());
				ErmbPersonListener listener = ErmbUpdateListener.getListener();
				listener.onPersonUpdate(personEvent);
		    }
	    }
	    if (oldPersonKey != null)
		    simpleService.add(oldPersonKey);
    }

	public void createEvent() throws BusinessException
	{
		personEvent = new ErmbPersonEvent(getEntity().getId());
		if (personEvent.getProfile() != null)
			personEvent.setOldPerson(getEntity());
	}

	public void setOldPersonKey(PersonBase oldPerson) throws BusinessException
	{
		if (isPersonKeyChanged(oldPerson))
			this.oldPersonKey = oldPerson.getPersonKey();
	}

	private boolean isPersonKeyChanged(Person oldPerson)
	{
		Person person = getPerson();
		if (!oldPerson.getFirstName().equals(person.getFirstName()))
			return true;
		if (!oldPerson.getSurName().equals(person.getSurName()))
			return true;
		if (!StringHelper.equalsNullIgnore(oldPerson.getPatrName(), person.getPatrName()))
			return true;
		if (oldPerson.getBirthDay().compareTo(person.getBirthDay()) != 0)
			return true;

		List<PersonDocument> oldPersonDocuments = new ArrayList<PersonDocument>(oldPerson.getPersonDocuments());
		Collections.sort(oldPersonDocuments, new PersonDocumentTypeComparator());
		PersonDocument oldPersonDocument = oldPersonDocuments.get(0);
		String oldPersonDocumentString = (StringHelper.getEmptyIfNull(oldPersonDocument.getDocumentSeries()) + StringHelper.getEmptyIfNull(oldPersonDocument.getDocumentNumber())).replaceAll(" ", "");

		List<PersonDocument> personDocuments = new ArrayList<PersonDocument>(person.getPersonDocuments());
		Collections.sort(personDocuments, new PersonDocumentTypeComparator());
		PersonDocument personDocument = personDocuments.get(0);
		String personDocumentString = (StringHelper.getEmptyIfNull(personDocument.getDocumentSeries()) + StringHelper.getEmptyIfNull(personDocument.getDocumentNumber())).replaceAll(" ", "");

		if (!StringHelper.equalsNullIgnore(oldPersonDocumentString, personDocumentString))
			return true;

		return false;
	}
}
