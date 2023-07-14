package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 11:37:04 To change this template use
 * File | Settings | File Templates.
 */
public class EditPersonGroupsOperation  extends OperationBase<UserRestriction>
{
	private static final GroupService groupService = new GroupService();
		private final static PersonService personService = new PersonService();

	private ActivePerson person;

	public void initialize(Long personId) throws BusinessException
	{
		this.person = (ActivePerson) personService.findById( personId );
	}

	@Transactional
	public void modifyPersonGroups(String[] groupIds) throws BusinessException
	{
		List<Group> groups = getPersonGroups();

		for(String groupId: groupIds)
		{
			Boolean exist = false;
			Long id = Long.parseLong(groupId);
			for(Group group: groups)
			{
				if(group.getId().equals(id))
				{
					exist = true;
					groups.remove(group);//можно,так как break
					break;
				}
			}
			if(!exist)
			{
				Group group = groupService.findGroupById( id );
				groupService.addElementToGroup(group, getActivePerson().getLogin() );
			}
		}
		for(Group group: groups)
		{
			groupService.deleteElementFromGroup(group, getActivePerson().getLogin());
		}
		
	}

	private List<Group> getPersonGroups() throws BusinessException
	{
		return groupService.getLoginContainGroup( getActivePerson().getLogin() );
	}

	public ActivePerson getActivePerson() throws BusinessException
	{
		checkPerson();
		return person;
	}

	private void checkPerson() throws BusinessException
	{
		if(person == null)
			throw new BusinessException("Ќе установлен пользователь");
	}

}
