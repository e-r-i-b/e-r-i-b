package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.operations.GeneratePasswordOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на генерацию пароля.
 *
 * Пармерты запроса:
 * login		        логин пользователя. 	[1]
 *
 * Параметры ответа:
 *

 */
public class GeneratePasswordRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "generatePasswordRq";
	public static final String RESPONCE_TYPE = "generatePasswordRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		trace("проверяем входные данные");
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		boolean ignoreImsiCheck = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.IGNORE_IMSI_CHEK));
		return generatePassword(login, context, ignoreImsiCheck);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		return LogIdentificationContext.createByLogin(login, false);
	}

	private LogableResponseInfo generatePassword(String login, final IdentificationContext identificationContext, boolean ignoreImsiCheck) throws Exception
	{
		try
		{
			trace("создаем заявку на генерацию пароля");
			GeneratePasswordOperation operation = createGeneratePasswordOperation(identificationContext, login, ignoreImsiCheck);
			trace("генерируем пароль");
			operation.execute();
			trace("пароль сгененирован. Возвращаем удачный ответ");
			return new LogableResponseInfo(buildSuccessResponse());
		}
		catch (ConnectorNotFoundException e)
		{
			error("ошибка генерации пароля", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildLoginNotFoundResponse(), e);
		}
	}

	private GeneratePasswordOperation createGeneratePasswordOperation(IdentificationContext identificationContext, String login, boolean ignoreImsiCheck) throws Exception
	{
		GeneratePasswordOperation operation = new GeneratePasswordOperation(identificationContext);
		operation.initialize(login, ignoreImsiCheck);
		return operation;
	}
}
