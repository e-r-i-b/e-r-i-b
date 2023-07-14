package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.login.exceptions.CanNotLoginException;
import com.rssl.phizic.business.login.exceptions.LoginOrPasswordWrongLoginExeption;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.operations.OperationBase;

import java.util.Map;

/**
 * Операция аутентификации через мобильный банк
 *
 * @author khudyakov
 * @ created 29.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class GetAuthDataFromMobileBankOperation extends OperationBase implements GetAuthDataOperation
{
	private AuthData authData = new AuthData();

	public GetAuthDataFromMobileBankOperation(Map<String, Object> data) throws CanNotLoginException, LoginOrPasswordWrongLoginExeption
	{
		String pan = (String) data.get("pan");
		String codeATM = (String) data.get("codeATM");

		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			UserInfo userInfo = mobileBankService.getClientByCardNumber(pan);
			if (userInfo == null)
				throw new LoginOrPasswordWrongLoginExeption();

			// Аутентифицировать можем только по активной и основной карте. Иначе не пропускаем.
			if (!(userInfo.isActiveCard() && userInfo.isMainCard()))
				throw new CanNotLoginException();

			//при входе в АТМ присваивается низкий уровень безопасности.
			authData.setSecurityType(SecurityType.LOW);
			authData.setBrowserInfo(codeATM);
			AuthenticationHelper.fillFromFromMobileBankData(authData, userInfo);
		}
		catch (GateException e)
		{
			throw new CanNotLoginException(e);
		}
		catch (GateLogicException e)
		{
			throw new CanNotLoginException(e);
		}
	}

	public AuthData getAuthData()
	{
		return authData;
	}
}
