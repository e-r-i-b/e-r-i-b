package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.auth.CommonLogin;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 9:50:41 To change this template use
 * File | Settings | File Templates.
 */
public class ModifyPersonGroupsOperation  extends OperationBase<UserRestriction> implements RemoveEntityOperation<Group, UserRestriction>
{
	private final static GroupService groupService = new GroupService();
	private final static PersonService personService = new PersonService();
	private Person person;
	private Group group;

	public void initialize(Long groupId) throws BusinessException
	{
		Group temp =groupService.findGroupById( groupId );
		if( temp == null )
			throw new BusinessException("Не установлена группа, для работы со списком пользователей");

		group = temp;
	}

	public void setDeletedPerson(Long id) throws BusinessException
	{
		Person temp = personService.findById(id);
		if (temp == null)
			throw new BusinessException("Клиент с id " + id + "нне найден");

		person = temp;
	}

	public Group getEntity()
	{
		return group;
	}

	@Transactional
	public void addPerson(Long id) throws BusinessException
	{
		Person person = personService.findById(id);
		if (person == null)
			throw new BusinessException("Клиент с id = " + id + " не найден ");

		groupService.addElementToGroup(group, person.getLogin());
	}

	@Transactional
	public void remove() throws BusinessException
	{
		groupService.deleteElementFromGroup(group, person.getLogin());
	}

	//TODO постараться использовать void remove(), после исправления EditPersonAction
	public void deletePersons(List<Long> personsIds) throws BusinessException
	{
		Set<CommonLogin> logins = formLoginsList( personsIds );
		groupService.deleteElementsFromGroup(group, logins);
	}

	public void deletePersonsFromAll(List<Long> personsIds) throws BusinessException
	{
		Set<CommonLogin> logins = formLoginsList( personsIds );
		groupService.deleteElements(logins);
	}
	private Set<CommonLogin> formLoginsList( List<Long> personsIds ) throws BusinessException
	{
		Set<CommonLogin> logins = new HashSet<CommonLogin>();
		for(Long id: personsIds)
		{
			Person person = personService.findById(id);
			logins.add( person.getLogin() );
		}
		return logins;
	}
}
