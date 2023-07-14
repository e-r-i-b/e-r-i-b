package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.guest.StartCreateGuestLoginSessionRequestProcessor;
import com.rssl.auth.csa.back.servises.Login;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Session;
import org.w3c.dom.Document;

/**
 * Процессор, маршрутизирующий запросы на аутентификацию гостя и полноценного клиента
 * @author niculichev
 * @ created 01.02.15
 * @ $Author$
 * @ $Revision$
 */
public class RouterCreateSessionRequestProcessor extends RequestProcessorBase
{
	private static final StartCreateSessionRequestProcessor clientLoginSessionRequestProcessor = new StartCreateSessionRequestProcessor();
	private static final StartCreateGuestLoginSessionRequestProcessor guestLoginSessionRequestProcessor = new StartCreateGuestLoginSessionRequestProcessor();

	public static final String REQUEST_TYPE = "startCreateSessionRq";
	public static final String RESPONCE_TYPE = "startCreateSessionRs";

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

		// если логин не найден, обрабатываем как для обычного клиента
		if(login == null)
		{
			return clientLoginSessionRequestProcessor.process(requestInfo);
		}

		switch (login.getType())
		{
			case CLIENT:
				return clientLoginSessionRequestProcessor.process(requestInfo);

			case GUEST:
				return guestLoginSessionRequestProcessor.process(requestInfo);

			default:
				throw new IllegalStateException("Неизвестный тип логика " + loginReq);
		}
	}

	public boolean isAccessStandIn()
	{
		return true;
	}
}
