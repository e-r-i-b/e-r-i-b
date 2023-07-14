package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.TooManyConnectorsException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginOperation extends LoginOperationBase
{
	private static final String GLOBAL_LOGIN_RESTRICTION_MESSAGE = "ѕо техническим причинам вход в систему временно ограничен. ѕопробуйте войти позднее";
	private static final GlobalLoginRestriction globalLoginRestriction = new GlobalLoginRestriction();

	/**
	 * »нициализаци€ операции
	 * @param info контекст операции
	 * @param login логин
	 * @param password пароль
	 */
	public void initialize(OperationInfo info, String login, String password)
	{
		super.initialize(info);
		operationInfo.setLogin(login);
		operationInfo.setPassword(password);
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		try
		{
			return CSABackRequestHelper.sendStartCreateSessionRq(operationInfo.getLogin(), operationInfo.getPassword());
		}
		catch (TooManyConnectorsException e)
		{
			// обновл€ем контекст операции коннекторами и идентификатором операции
			operationInfo.setConnectorInfos(e.getConnectorInfos());
			operationInfo.setOUID(e.getOuid());
			return null;
		}
	}

	protected void checkRestrict() throws FrontLogicException, FrontException
	{
		// провер€ем глобальное ограничени€ входа
		if(!globalLoginRestriction.check())
			throw new FrontLogicException(GLOBAL_LOGIN_RESTRICTION_MESSAGE);
	}
}
