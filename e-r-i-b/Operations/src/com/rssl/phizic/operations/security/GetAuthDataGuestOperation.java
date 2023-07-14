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
 * @author niculichev
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */
public class GetAuthDataGuestOperation extends OperationBase implements GetAuthDataOperation
{
	private AuthData authData = new AuthData();

	public GetAuthDataGuestOperation(String authToken, String browserInfo) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document document = CSABackRequestHelper.sendFinishCreateGuestSessionRq(authToken);
			AuthenticationHelper.fillGuestAuthData(authData, document, browserInfo);

			if (LoginType.GUEST != authData.getLoginType())
			{
				throw new BusinessException("Точка входа для гостя. Пришел " + authData.getLoginType());
			}
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	public AuthData getAuthData()
	{
		return authData;
	}
}
