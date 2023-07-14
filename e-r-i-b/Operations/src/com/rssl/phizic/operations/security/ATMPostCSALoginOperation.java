package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.operations.OperationBase;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция завершения аутентификации пользователя АТМ
 */
public class ATMPostCSALoginOperation extends OperationBase
{
	private AuthData authData;

	/**
	 * ctor
	 * @param token токен аутентификации
	 */
	public ATMPostCSALoginOperation(String token, String codeATM) throws BusinessException, BusinessLogicException
	{
		try
		{
			Document response = CSABackRequestHelper.sendFinishCreateATMSessionRq(token);
			authData = new AuthData();
			authData.setBrowserInfo(codeATM);
			AuthenticationHelper.fillFromATMCSAData(authData, response);

			if (LoginType.ATM != authData.getLoginType())
			{
				throw new BusinessException("Вход в мобильной версии возможен только через mAPI коннектор, используется тип " + authData.getLoginType().name());
			}
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
	}

	/**
	 * @return данные аутентификации
	 */
	public AuthData getAuthData()
	{
		return authData;
	}
}
