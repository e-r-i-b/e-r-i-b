package com.rssl.phizic.business.ermb.profile.events;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.BeanHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сущность для передачи наблюдателю данных об измененном клиенте
 */
public class ErmbPersonEvent
{
	private ErmbProfileImpl profile;
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private Person oldPerson;
	private Person newPerson;

	public ErmbPersonEvent(Long id) throws BusinessException
	{
		this.profile = findProfile(id);
	}

	public ErmbPersonEvent(ErmbProfileImpl profile)
	{
		this.profile = profile;
	}

	private ActivePerson copyResourse(Person person) throws BusinessException
	{
		if (person == null)
			return null;

		Map<Class, Class> types = new HashMap<Class,Class>();
		types.put(ActivePerson.class, ActivePerson.class);
		types.put(PersonDocumentImpl.class, PersonDocumentImpl.class);
		try
		{
			return BeanHelper.copyObject(person, types);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Person getOldPerson()
	{
		return oldPerson;
	}

	public void setOldPerson(Person oldPerson) throws BusinessException
	{
		this.oldPerson = copyResourse(oldPerson);
	}

	public Person getNewPerson()
	{
		return newPerson;
	}

	public void setNewPerson(Person newPerson) throws BusinessException
	{
		this.newPerson = newPerson;
		profile = findProfile(newPerson.getId());
	}

	public ErmbProfileImpl getProfile()
	{
		return profile;
	}

	private ErmbProfileImpl findProfile(Long personId) throws BusinessException
	{
		return profileService.findByPersonId(personId);
	}
}
