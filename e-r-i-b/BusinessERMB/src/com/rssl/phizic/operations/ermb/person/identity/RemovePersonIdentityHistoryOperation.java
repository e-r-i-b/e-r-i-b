package com.rssl.phizic.operations.ermb.person.identity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.business.persons.oldIdentity.PersonIdentityService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import static com.rssl.phizic.operations.ermb.person.identity.PersonIdentityHistoryListOperation.CURRENT_IDENTITY_MOCK_ID;

/**
 * User: moshenko
 * Date: 25.03.2013
 * Time: 15:47:52
 *
 */
public class RemovePersonIdentityHistoryOperation extends OperationBase implements RemoveEntityOperation
{
	private static final PersonIdentityService PERSON_IDENTITY_SERVICE = new PersonIdentityService();
	private PersonOldIdentity personOldIdentity;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		//если -1 то это запись текущих данных, её не трогаем
		if (id != CURRENT_IDENTITY_MOCK_ID)
			personOldIdentity = PERSON_IDENTITY_SERVICE.getIdentityById(id);
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		if (personOldIdentity != null)
			PERSON_IDENTITY_SERVICE.markDelete(personOldIdentity);
	}

	public Object getEntity()
	{
		return personOldIdentity;
	}
}
