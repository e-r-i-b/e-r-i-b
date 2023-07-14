package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.config.SecurityConfig;

/**
 * @author Roshka
 * @ created 23.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class AddPasswordCardsOperation extends OperationBase
{
    private static final PasswordCardService service = new PasswordCardService();

    private int cardsCount;
    private int keysCount;

    public void initialize() throws BusinessException, BusinessLogicException
    {
	    SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
	    cardsCount = securityConfig.getCardsCount();
	    keysCount = securityConfig.getCardPasswords();
    }

    public int getCardsCount()
    {
        return cardsCount;
    }

    public void setCardsCount(int cardsCount)
    {
        this.cardsCount = cardsCount;
    }

    public int getKeysCount()
    {
        return keysCount;
    }

    public void setKeysCount(int keysCount)
    {
        this.keysCount = keysCount;
    }

    /**
     * Создать новую карту(ы)
     * @throws Exception
     */
    @Transactional
    public void add() throws Exception
    {
       service.create(getCardsCount(), getKeysCount());
    }
}

