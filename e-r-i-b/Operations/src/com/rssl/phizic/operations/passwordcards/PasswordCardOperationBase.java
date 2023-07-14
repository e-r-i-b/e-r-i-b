package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.Login;

/**
 * @author Roshka
 * @ created 14.04.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class PasswordCardOperationBase extends OperationBase<UserRestriction>
{
	protected static final PasswordCardService passwordCardService = new PasswordCardService();
	private static final PersonService personService = new PersonService();
	private PasswordCard card;

	public void initialize(Long cardId) throws BusinessException
	{
		try
		{
			PasswordCard temp = passwordCardService.findById(cardId);

			if (temp == null)
				throw new BusinessException("Карта паролей с id [" + cardId + "] не найдена");

			Login login = temp.getLogin();

			ActivePerson person = personService.findByLogin(login);
			PersonOperationBase.checkRestriction(getRestriction(), person);

			card = temp;

		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	public PasswordCard getCard()
	{
		return card;
	}
}