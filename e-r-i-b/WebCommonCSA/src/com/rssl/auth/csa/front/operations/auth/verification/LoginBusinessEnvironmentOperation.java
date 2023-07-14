package com.rssl.auth.csa.front.operations.auth.verification;

import com.rssl.auth.csa.front.operations.auth.GlobalLoginRestriction;
import com.rssl.auth.csa.front.operations.auth.InterchangeCSABackOperationBase;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import org.w3c.dom.Document;

/**
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * Операция входа для верификации данных в деловой среде
 */

public class LoginBusinessEnvironmentOperation extends InterchangeCSABackOperationBase
{
	private static final String GLOBAL_LOGIN_RESTRICTION_MESSAGE = "По техническим причинам вход в систему временно ограничен. Попробуйте войти позднее";
	private static final GlobalLoginRestriction globalLoginRestriction = new GlobalLoginRestriction();

	private BusinessEnvironmentOperationInfo operationInfo;

	/**
	 * Инициализация операции
	 * @param operationInfo контекст операции
	 * @param login логин
	 * @param password пароль
	 */
	public void initialize(BusinessEnvironmentOperationInfo operationInfo, String login, String password)
	{
		this.operationInfo = operationInfo;
		operationInfo.setLogin(login);
		operationInfo.setPassword(password);
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		return CSABackRequestHelper.sendAuthenticationRq(operationInfo.getLogin(), operationInfo.getPassword());
	}

	protected void processResponce(Document responce) throws FrontException, FrontLogicException
	{
		operationInfo.setAuthToken(CSABackResponseSerializer.getAuthToken(responce));
	}

	protected void checkRestrict() throws FrontLogicException, FrontException
	{
		// проверяем глобальное ограничения входа
		if(!globalLoginRestriction.check())
			throw new FrontLogicException(GLOBAL_LOGIN_RESTRICTION_MESSAGE);
	}


}
