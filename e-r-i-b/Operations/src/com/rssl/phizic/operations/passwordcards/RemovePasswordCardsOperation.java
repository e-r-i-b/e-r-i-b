package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.auth.passwordcards.InvalidPasswordCardStateException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.security.SecurityDbException;

/**
 * @author Evgrafov
 * @ created 02.05.2006
 * @ $Author: khudyakov $
 * @ $Revision: 40124 $
 */

public class RemovePasswordCardsOperation extends OperationBase implements RemoveEntityOperation
{
	private static final PasswordCardService service = new PasswordCardService();

	private PasswordCard card;

	public void initialize(Long id) throws BusinessException
	{
		try
		{
			card = service.findById(id);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		if (card == null)
			throw new BusinessException("карта с id " + id + " не найдена");
	}

	public PasswordCard getEntity()
	{
		return card;
	}

	/**
	 * Удалить карту
	 *
	 * @throws Exception
	 */
	@Transactional
	public void remove() throws BusinessException
	{
		try
		{
			service.remove(card);
		}
		catch (InvalidPasswordCardStateException e)
		{
	   	    throw new BusinessException(e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

}