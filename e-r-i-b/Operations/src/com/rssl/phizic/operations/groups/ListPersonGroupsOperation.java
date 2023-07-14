package com.rssl.phizic.operations.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 15.11.2006 Time: 11:38:00 To change this template use
 * File | Settings | File Templates.
 */
public class ListPersonGroupsOperation  extends OperationBase<UserRestriction> implements ListEntitiesOperation<UserRestriction>
{
	private static final GroupService groupService = new GroupService();
	private final static PersonService personService = new PersonService();

	private ActivePerson person;

	public void initialize(Long personId) throws BusinessException
	{
		this.person = (ActivePerson) personService.findById( personId );
	}

	public ActivePerson getActivePerson() throws BusinessException
	{
		checkPerson();
		return person;
	}

	public List<Group> getPersonGroups() throws BusinessException
	{
		return groupService.getLoginContainGroup( getActivePerson().getLogin() );
	}

	public List<Group> getAllGroups() throws BusinessException
	{
		return groupService.getDepartmentGroups( getCurrentDepartmentId(), AccessCategory.CATEGORY_CLIENT);
	}

	private void checkPerson() throws BusinessException
	{
		if(person == null)
			throw new BusinessException("Не установлен пользователь");
	}

	private Long getCurrentDepartmentId() throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	    return employeeData.getEmployee().getDepartmentId();
	}
}
