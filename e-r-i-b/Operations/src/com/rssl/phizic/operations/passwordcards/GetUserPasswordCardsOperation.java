package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;

import java.util.List;

/**
 * @author Roshka
 * @ created 14.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class GetUserPasswordCardsOperation extends PersonOperationBase implements ListEntitiesOperation<UserRestriction>
{
	private static PasswordCardService passwordCardService = new PasswordCardService();

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		super.setPersonId(personId);
		Login login = getPerson().getLogin();
	}

	@Transactional
	public List<PasswordCard> getCards() throws BusinessException
	{
		try
		{
			Login login = getPerson().getLogin();
			return passwordCardService.getAll(login);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}