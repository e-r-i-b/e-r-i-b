package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.ValidateLoginOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Element;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на проверку логина
 *
 * Параметры запроса:
 * SID		            Идентфикатор сессии, в контексте которой происходит проверка логина. 	[1]
 * login		        логин	                [1]
 * sameLogin            исключить из поиска логины этого пользователя [0-1]
 *
 * Параметры ответа:
 */
public class ValidateLoginRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "validateLoginRq";
	public static final String RESPONCE_TYPE = "validateLoginRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element body = requestInfo.getBody().getDocumentElement();

		String sid = XmlHelper.getSimpleElementValue(body, Constants.SID_TAG);
		String ouid = XmlHelper.getSimpleElementValue(body, Constants.OUID_TAG);

		String login = XmlHelper.getSimpleElementValue(body, Constants.LOGIN_TAG);
		boolean sameLogin = BooleanUtils.toBoolean(XmlHelper.getSimpleElementValue(body, Constants.SAME_LOGIN_TAG));
		boolean checkPassword = BooleanUtils.toBoolean(XmlHelper.getSimpleElementValue(body, Constants.CHECK_PASSWORD_PARAM_NAME));

		ValidateLoginOperation operation = null;
		if(StringHelper.isNotEmpty(ouid))
		{
			operation = new ValidateLoginOperation(IdentificationContext.createByOperationUID(ouid));
			operation.initializeByOUID(ouid);
		}
		else if(StringHelper.isNotEmpty(sid))
		{
			operation = new ValidateLoginOperation(IdentificationContext.createBySessionId(sid));
			operation.initializeBySID(sid);
		}
		else
		{
			throw new IdentificationFailedException("Нет данных для идентифкации клиента.");
		}

		return processRequest(operation, login, sameLogin, checkPassword) ;
	}

	protected ResponseInfo processRequest(ValidateLoginOperation operation, String login, boolean sameLogin, boolean checkPassword) throws Exception
	{
		trace("исполняем заяку на проверку логина");
		operation.execute(login, sameLogin, checkPassword);
		trace("заяка на проверку логина успешно исполнена");
		return buildSuccessResponse();
	}
}