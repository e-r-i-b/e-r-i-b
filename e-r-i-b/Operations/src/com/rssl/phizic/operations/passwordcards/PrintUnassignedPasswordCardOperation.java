package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.passwordcards.CardPassword;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author Roshka
 * @ created 05.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class PrintUnassignedPasswordCardOperation extends OperationBase
{
	private static PasswordCardService passwordCardService = new PasswordCardService();

	private PasswordCard card;

    public PasswordCard initialize(Long cardId) throws BusinessException
    {
        try
        {
            card = passwordCardService.findById( cardId );
            return card;
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	public List<CardPassword> getPaswords() throws BusinessException
    {
	    try
	    {
	       return passwordCardService.getAllCardPaswords(card);
	    }
	    catch (Exception e)
	    {
	        throw new BusinessException(e);
	    }
    }

}
