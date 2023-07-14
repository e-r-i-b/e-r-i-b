package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.*;

/**
 * @author Evgrafov
 * @ created 26.07.2006
 * @ $Author: hudyakov $
 * @ $Revision: 10511 $
 */

//todo ��������� ����������������� ������ RemovePersonOperation 
public class DeleteEmpoweredPersonOperation extends RemovePersonOperation
{
	private static PersonService personService = new PersonService();
	private ActivePerson person;

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		ActivePerson temp = (ActivePerson) personService.findByIdNotNull(personId);
		PersonOperationBase.checkRestriction(getRestriction(), temp);
		if (temp.getTrustingPersonId() == null)
			throw new BusinessException("������������ � id " + personId + " �� �������� ��������������");

		person = temp;
	}

	public void remove() throws BusinessException
	{
		personService.markDeleted(person);
	}

	public ActivePerson getEntity()
	{
		return person;
	}
}