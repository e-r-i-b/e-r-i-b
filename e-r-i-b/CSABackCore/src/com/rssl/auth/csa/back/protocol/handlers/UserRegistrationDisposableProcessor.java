package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Login;
import com.rssl.auth.csa.back.servises.connectors.DisposableConnector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationDisposableOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * Обработка запроса на получение одноразового логина и пароля через УС
 * @author Jatsky
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */

public class UserRegistrationDisposableProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "userRegistrationDisposableRq";
	public static final String RESPONCE_TYPE = "userRegistrationDisposableRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		trace("проверяем входные данные");
		String cardNum = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CARD_NUMBER_TAG);
		String sendSMS = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.SEND_SMS_TAG);
		return generatePassword(cardNum, sendSMS);
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	private ResponseInfo generatePassword(String cardNum, String sendSMS) throws Exception
	{
		try
		{
			IdentificationContext identificationContext = IdentificationContext.createByCardNumber(cardNum);
			UserRegistrationDisposableOperation operation = new UserRegistrationDisposableOperation(identificationContext);
			operation.initialize(cardNum);
			DisposableConnector connector = operation.execute(sendSMS);

			String login = connector.getLogin();
			if(login == null)
			{
				error("Ошибка генерации одноразового логина и пароля");
				return getFailureResponseBuilder().buildLoginNotFoundResponse();
			}

			return getSuccessResponseBuilder()
					.addParameter(Constants.LOGIN_TAG, login)
					.addParameter(Constants.PASSWORD_TAG, connector.getDisposablePass())
					.end().getResponceInfo();
		}
		catch (ConnectorNotFoundException e)
		{
			error("Ошибка генерации одноразового логина и пароля", e);
			return getFailureResponseBuilder().buildLoginNotFoundResponse();
		}
	}
}

