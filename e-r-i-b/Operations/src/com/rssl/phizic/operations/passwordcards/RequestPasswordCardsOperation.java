package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.config.SecurityConfig;

import java.io.OutputStream;

/**
 * @author Roshka
 * @ created 23.12.2005
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
public class RequestPasswordCardsOperation extends OperationBase
{
    private static final PasswordCardService service = new PasswordCardService();

    private int cardsCount;
    private int keysCount;

	/**
	 * инициализация
	 */
    public void initialize() throws BusinessException, BusinessLogicException
    {
	    SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
	    cardsCount = securityConfig.getCardsCount();
	    keysCount = securityConfig.getCardPasswords();
    }

	/**
	 * @return сколько карт создавать
	 */
    public int getCardsCount()
    {
        return cardsCount;
    }

	/**
	 * @param cardsCount сколько карт создавать
	 */
    public void setCardsCount(int cardsCount)
    {
        this.cardsCount = cardsCount;
    }

	/**
 	 * @return количество ключей в карте
	 */
    public int getKeysCount()
    {
        return keysCount;
    }

	/**
	 * @param keysCount количество ключей в карте
	 */
    public void setKeysCount(int keysCount)
    {
        this.keysCount = keysCount;
    }

    /**
     * Создать новую карту(ы)
     * @param os поток в который записать результаты
     * @return запрос на карты ключей
     * @throws Exception
     */
    @Transactional
    public void request(OutputStream os) throws Exception
    {
	    RequestBuilder requestBuilder = new RequestBuilder(os);
	    PasswordCard[] cards = service.create(getCardsCount(), getKeysCount());
	    requestBuilder.addCards(cards);
	    requestBuilder.build();
    }
}