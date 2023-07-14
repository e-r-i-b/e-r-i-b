package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csa.wsclient.exceptions.InvalidCodeConfirmException;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.business.login.exceptions.WrongCodeConfirmException;
import com.rssl.phizic.business.operations.restrictions.MobileDataCompositeRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileMGUIDDataRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileVersionDataRestriction;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DocumentConfig;

import java.util.Map;

/**
 * Операция подтверждения регистрации мобильного приложения
 *
 * @author Jatsky
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmMobileRegistrationOperation extends OperationBase
{
	private AuthParamsContainer container = new AuthParamsContainer();

	/**
	 * Операция подтверждения регистрации мобильного приложения
	 * @param data данные формы
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public ConfirmMobileRegistrationOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
	{
		//данная проверка здесь, а не в форме, только из-за перехода и текстовки
		new MobileDataCompositeRestriction(new MobileMGUIDDataRestriction(), new MobileVersionDataRestriction()).accept(data);

		String mGUID = (String) data.get("mGUID");

		// Подтверждаем операцию создания mGUID.
		try
		{
			CSABackRequestHelper.sendConfirmOperationRq(mGUID, (String) data.get("smsPassword"));
		}
		catch (InvalidCodeConfirmException e)
		{
			throw new WrongCodeConfirmException(ConfigFactory.getConfig(DocumentConfig.class).getInvalidConfirmCodeRequest(), e.getTime(), e.getAttempts(), e);
		}
		catch (FailureIdentificationException e)
		{
			throw new RegistrationErrorException(e.getMessage(), e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить первоначальные данные для подтверждения регистрации
	 * @return AuthParamsContainer
	 */
	public AuthParamsContainer getAuthParams()
	{
		return container;
	}
}
