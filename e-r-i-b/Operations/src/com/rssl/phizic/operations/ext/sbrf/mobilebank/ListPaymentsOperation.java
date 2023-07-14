package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;

/**
 * @author Erkin
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция получения списка платежей Мобильного Банка
 */
public class ListPaymentsOperation extends MobileBankOperationBase
{
	public void initialize() throws BusinessException
	{
		super.initialize();
	}

	/**
	 * Возвращает профиль подключения по коду номера телефона и коду номера карты
	 * @param phoneCode - код номера телефона
	 * @param cardCode - код карты
	 * @return профиль подключения или null, если не найден
	 */
	public RegistrationProfile getRegistrationProfile(String phoneCode, String cardCode) throws BusinessException, BusinessLogicException
	{
		return mobileBankService.getRegistrationProfile(getLogin(), phoneCode, cardCode);
	}
}
