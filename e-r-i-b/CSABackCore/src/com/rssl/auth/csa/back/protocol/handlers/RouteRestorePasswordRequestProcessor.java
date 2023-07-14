package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.guest.StartGuestRestorePasswordRequestProcessor;
import com.rssl.auth.csa.back.servises.Login;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author niculichev
 * @ created 15.02.15
 * @ $Author$
 * @ $Revision$
 */
public class RouteRestorePasswordRequestProcessor extends RequestProcessorBase
{
	private static final StartRestorePasswordRequestProcessor clientProcessor = new StartRestorePasswordRequestProcessor();
	private static final StartGuestRestorePasswordRequestProcessor guestProcessor = new StartGuestRestorePasswordRequestProcessor();

	public static final String REQUEST_TYPE = "startRestorePasswordRq";
	public static final String RESPONCE_TYPE = "startRestorePasswordRs";

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String loginReq = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);

		final Login login = Login.findBylogin(loginReq);

		// если логин не найден, обрабатываем как дл€ обычного клиента
		if(login == null)
		{
			return clientProcessor.process(requestInfo);
		}

		switch (login.getType())
		{
			case CLIENT:
				return clientProcessor.process(requestInfo);

			case GUEST:
				return guestProcessor.process(requestInfo);

			default:
				throw new IllegalStateException("Ќеизвестный тип логина " + loginReq);
		}
	}
}
