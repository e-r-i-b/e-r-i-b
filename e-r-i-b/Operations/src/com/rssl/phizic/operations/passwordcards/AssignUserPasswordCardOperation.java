package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Evgrafov
 * @ created 23.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 32866 $
 */

public class AssignUserPasswordCardOperation extends PersonOperationBase
{
	private static PasswordCardService passwordCardService = new PasswordCardService();

	private PasswordCard activeCard;
	private PasswordCard cardToAssign;

	public void initialize(Long personId, String cardNumber) throws BusinessException, BusinessLogicException
	{
		super.setPersonId(personId);
		Login login = getPerson().getLogin();

		try
		{
			activeCard = passwordCardService.getActiveCard(login);

			cardToAssign = passwordCardService.findByNumber(cardNumber);
			if (cardToAssign == null)
				throw new BusinessException("Карта паролей с номером [" + cardNumber + "] не найдена");
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Назначить карту пользователю.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public void assign() throws BusinessException, BusinessLogicException
	{
		try
		{
			passwordCardService.assign(getPerson().getLogin(), cardToAssign);
			if(activeCard == null)
			{
				passwordCardService.activate(cardToAssign);
				activeCard = cardToAssign;
			}
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public PasswordCard getCardToAssign()
	{
		return cardToAssign;
	}
}
